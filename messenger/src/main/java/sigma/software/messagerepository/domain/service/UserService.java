package sigma.software.messagerepository.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import sigma.software.messagerepository.domain.Message;
import sigma.software.messagerepository.domain.User;
import sigma.software.messagerepository.domain.command.*;
import sigma.software.messagerepository.domain.event.api.DomainEvent;
import sigma.software.messagerepository.domain.query.*;
import sigma.software.messagerepository.domain.query.api.QueryResponse;
import sigma.software.messagerepository.domain.service.gateway.CommandGateway;
import sigma.software.messagerepository.domain.service.gateway.QueryGateway;
import sigma.software.messagerepository.domain.service.gateway.repository.UserRepository;
import sigma.software.messagerepository.domain.service.gateway.repository.eventstore.EventStore;
import sigma.software.messagerepository.domain.service.gateway.repository.eventstore.config.EventStoreConfig;
import sigma.software.messagerepository.domain.service.gateway.repository.eventstore.config.JacksonConfig;
import sigma.software.messagerepository.domain.service.insftastructure.Process;
import sigma.software.messagerepository.domain.service.insftastructure.Result;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.joining;

public class UserService {

    // private static final Logger log = LogManager.getLogger();

    private JacksonConfig jacksonConfig = new JacksonConfig();
    private EventStoreConfig evenStoreConfig = new EventStoreConfig();
    private EventStore eventStore = new EventStore(jacksonConfig, evenStoreConfig);
    private UserRepository repository = new UserRepository(eventStore);
    QueryGateway queryGateway = new QueryGateway(repository);
    private CommandGateway commandGateway = new CommandGateway(repository);
    private ObjectMapper objectMapper = jacksonConfig.createObjectMapper();

    private String homePath = System.getProperty("user.home", "/tmp");
    private String userConfigJson = "user-config.json";
    private Path path = Paths.get(homePath, ".mr", userConfigJson);

    public Result signup(String... args) {
        if (args.length < 1) return Result.BAD_REQUEST;

        String username = args[1];
        Try<UUID> aTry = args.length > 2 ? Try.of(() -> UUID.fromString(args[2])) : Try.of(UUID::randomUUID);
        if (aTry.isFailure()) return Result.BAD_REQUEST;

        UUID uuid = aTry.get();
        commandGateway.apply(new CreateUserCommand(uuid, username));
        createConfigFile(uuid);
        System.out.printf("%s user created. user this id to signin: %s%n", username, uuid);

        return Result.OK;
    }

    public Result signin(String... args) {
        Try<UUID> aTry = Try.of(() -> UUID.fromString(args[1]));
        if (aTry.isFailure()) return Result.BAD_REQUEST;

        UUID uuid = aTry.get();
        String username = repository.load(uuid).getUsername();
        if (Objects.isNull(username)) return Result.USER_NOT_FOUND;

        createConfigFile(uuid);
        System.out.println("Your username: " + username);
        return Result.OK;
    }

    public Result invite(String... args) {
        String desiredFriendId = args[1];
        Try<UUID> aTry = Try.of(() -> UUID.fromString(desiredFriendId));
        if (aTry.isFailure()) return Result.BAD_REQUEST;

        UUID friendId = aTry.get();
        if (Objects.isNull(friendId)) return Result.USER_NOT_FOUND;

        User friend = repository.load(friendId);
        Optional<UUID> maybeCurrentId = getCurrentUserId();
        if (Objects.nonNull(friend) && maybeCurrentId.isPresent()) {
            commandGateway.apply(new SendFriendRequestCommand(maybeCurrentId.get(), friend.getAggregateId()));
            commandGateway.apply(new ReceiveFriendRequestCommand(friend.getAggregateId(), maybeCurrentId.get()));
            System.out.println("Friend invited.");
        }
        return Result.OK;
    }

    public Result invites(String... args) {
        Optional<UUID> maybeCurrentId = getCurrentUserId();
        if (!maybeCurrentId.isPresent()) return Result.USER_NOT_FOUND;

        String response = queryGateway.apply(new UserFriendRequestsRequest(maybeCurrentId.get())).toString();
        if (response.isEmpty()) return Result.EMPTY_LIST;
        System.out.println(response);
        return Result.OK;
    }

    public Result accept(String... args) {
        String maybeUserId = args[1];
        Try<UUID> aTry = Try.of(() -> UUID.fromString(maybeUserId));
        if (aTry.isFailure()) return Result.BAD_REQUEST;

        UUID acceptedFriendId = aTry.get();
        if (Objects.isNull(acceptedFriendId)) return Result.USER_NOT_FOUND;
        UUID currentUserId = getCurrentUserId().get();

        commandGateway.apply(new AcceptFriendRequestCommand(currentUserId, acceptedFriendId));
        commandGateway.apply(new AcceptFriendRequestCommand(currentUserId, currentUserId));

        return Result.OK;
    }

    public Result decline(String... args) {
        String maybeUserId = args[1];
        Try<UUID> aTry = Try.of(() -> UUID.fromString(maybeUserId));
        if (aTry.isFailure()) return Result.BAD_REQUEST;

        UUID declinedFriendId = aTry.get();
        if (Objects.isNull(declinedFriendId)) return Result.USER_NOT_FOUND;

        UUID currentUserId = getCurrentUserId().get();
        commandGateway.apply(new DeclineFriendRequestCommand(currentUserId, UUID.fromString(maybeUserId)));

        return Result.OK;
    }

    public Result friends(String... arg) {
        Optional<UUID> maybeCurrentId = getCurrentUserId();
        if (!maybeCurrentId.isPresent()) return Result.USER_NOT_FOUND;
        QueryResponse response = queryGateway.apply(new UserFriendsRequest(maybeCurrentId.get()));
        System.out.println(response.toString());
        return Result.OK;
    }

    public Result messages(String... args) {
        Optional<UUID> maybeCurrentId = getCurrentUserId();
        if (!maybeCurrentId.isPresent()) return Result.USER_NOT_FOUND;
        UUID uuid = maybeCurrentId.get();
        QueryResponse queryResponse = queryGateway.apply(new UserMessagesUserRequest(uuid));
        UserMessagesResponse response = UserMessagesResponse.class.cast(queryResponse);
        String result = response.getAllUserMessages().stream()
                                .map(Message::getBody)
                                .collect(joining(", "));
        System.out.println(result);
        return Result.OK;
    }

    private void createConfigFile(UUID id) {
        User user = repository.load(id);
        Try.of(() -> Files.deleteIfExists(path))
           .map(aBoolean -> path.getParent())
           .filter(Objects::nonNull)
           .andThenTry(p -> Files.createDirectories(p))
           .andThenTry(() -> Files.createFile(path))
           .andFinallyTry(() -> {
               String json = objectMapper.writeValueAsString(user);
               Files.write(path, singletonList(json), UTF_8, TRUNCATE_EXISTING);
           });
    }

    private File findConfigFile() {
        return Files.exists(path) ? path.toFile() : null;
    }

    private Optional<UUID> getCurrentUserId() {
        Optional<File> maybeFile = Optional.ofNullable(findConfigFile());
        if (!maybeFile.isPresent()) return Optional.empty();
        File file = maybeFile.get();
        return Try.of(() -> objectMapper.readValue(file, User.class).getAggregateId())
                  .map(Optional::of)
                  .getOrElseGet(throwable -> Optional.empty());
    }

    public Optional<User> getCurrentUser() {
        Optional<UUID> maybeCurrentUserId = getCurrentUserId();
        if (!maybeCurrentUserId.isPresent()) {
            return Optional.empty();
        }
        Collection<DomainEvent> readed = eventStore.read(maybeCurrentUserId.get());
        return Try.of(() -> new User(maybeCurrentUserId.get(), readed))
                  .map(Optional::of)
                  .getOrElseGet(throwable -> Optional.empty());
    }

    public void process(Process actionQuery, String... args) {
        actionQuery.getProcessors().entrySet().stream()
                   .filter(entry -> entry.getKey().test(args))
                   .map(Map.Entry::getValue)
                   .findFirst()
                   .map(action -> action.apply(this, args))
                   .ifPresent(Result::printUserOutputAndExit);
    }

    public Result usage(String... args) {
        return Result.USAGE;
    }

}

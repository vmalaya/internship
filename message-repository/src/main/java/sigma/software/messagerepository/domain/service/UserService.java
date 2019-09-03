package sigma.software.messagerepository.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import sigma.software.messagerepository.domain.User;
import sigma.software.messagerepository.domain.command.CreateUserCommand;
import sigma.software.messagerepository.domain.command.ReceiveFriendRequestCommand;
import sigma.software.messagerepository.domain.command.SendFriendRequestCommand;
import sigma.software.messagerepository.domain.service.gateway.CommandGateway;
import sigma.software.messagerepository.domain.service.gateway.repository.UserRepository;
import sigma.software.messagerepository.domain.service.gateway.repository.eventstore.EventStore;
import sigma.software.messagerepository.domain.service.gateway.repository.eventstore.config.EventStoreConfig;
import sigma.software.messagerepository.domain.service.gateway.repository.eventstore.config.JacksonConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
    // TODO: FIXME: // QueryGateway queryGateway = new QueryGateway(repository);
    private CommandGateway commandGateway = new CommandGateway(repository);
    private ObjectMapper objectMapper = jacksonConfig.createObjectMapper();

    private String homePath = System.getProperty("user.home", "/tmp");
    private String userConfigJson = "user-config.json";
    private Path path = Paths.get(homePath, ".mr", userConfigJson);

    public UUID signup(String username, UUID uuid) {
        commandGateway.handle(new CreateUserCommand(uuid, username));
        createConfigFile(uuid);
        return uuid;
    }

    public String signin(UUID id) {
        User loaded = repository.load(id);
        createConfigFile(id);
        return loaded.getUsername();
    }

    public UUID invite(UUID id) {
        User friend = repository.load(id);
        Optional<UUID> maybeCurrentId = getCurrentUserId();
        if (Objects.nonNull(friend) && maybeCurrentId.isPresent()) {
            commandGateway.handle(new SendFriendRequestCommand(maybeCurrentId.get(), friend.getAggregateId()));
            // TODO: FIXME: // commandGateway.handle(new ReceiveFriendRequestCommand(maybeCurrentId.get(), friend.getAggregateId()));
        }
        return id;
    }

    public String invites() {
        Optional<UUID> maybeCurrentId = getCurrentUserId();
        if (!maybeCurrentId.isPresent()) return "";
        User me = repository.load(maybeCurrentId.get());
        return me.getFriendRequest()
                 .stream()
                 .map(UUID::toString)
                 .collect(Collectors.joining(", "));
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
}

package sigma.software.messagerepository.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import sigma.software.messagerepository.domain.User;
import sigma.software.messagerepository.domain.command.CreateUserCommand;
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
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.util.Collections.singletonList;

public class UserService {

    // private static final Logger log = LogManager.getLogger();

    JacksonConfig jacksonConfig = new JacksonConfig();
    EventStoreConfig evenStoreConfig = new EventStoreConfig();
    EventStore eventStore = new EventStore(jacksonConfig, evenStoreConfig);
    UserRepository repository = new UserRepository(eventStore);
    // TODO: FIXME: // QueryGateway queryGateway = new QueryGateway(repository);
    CommandGateway commandGateway = new CommandGateway(repository);
    ObjectMapper objectMapper = jacksonConfig.createObjectMapper();

    public UUID signup(String username) {
        UUID id = UUID.randomUUID();
        commandGateway.handle(new CreateUserCommand(id, username));
        createConfigFile(id);
        return id;
    }

    public String signin(String id) throws IOException {
        User loaded = repository.load(UUID.fromString(id));
        return loaded.getUsername();
    }

    public String invite(String id) throws IOException {
        User friend = repository.load(UUID.fromString(id));
        if (Objects.nonNull(friend)) {
            commandGateway.handle(new SendFriendRequestCommand(getCurrentUserId(), friend.getAggregateId()));
        }
        return id;
    }

    private void createConfigFile(UUID id) {
        User user = repository.load(id);
        String homePath = System.getProperty("user.home", "/tmp");
        Path path = Paths.get(homePath, ".mr", "user-config.txt");
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
        String homePath = System.getProperty("user.home");
        Path path = Paths.get(homePath, ".mr", "user-config.txt");
        return Files.exists(path) ? path.toFile() : null;
    }

    private UUID getCurrentUserId() throws IOException {
        File file = findConfigFile();
        return Objects.nonNull(file) ? objectMapper.readValue(file, User.class).getAggregateId() : null;
    }
}

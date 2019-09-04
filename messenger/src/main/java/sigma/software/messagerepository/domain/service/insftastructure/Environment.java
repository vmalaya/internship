package sigma.software.messagerepository.domain.service.insftastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sigma.software.messagerepository.domain.User;
import sigma.software.messagerepository.domain.query.UserResponse;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.util.Collections.singletonList;

public class Environment {

    private static final String homePath = System.getProperty("user.home", "/tmp");
    private static final Path path = Paths.get(homePath, ".mr", "config.json");
    private static final Logger log = LogManager.getLogger();

    private final ObjectMapper objectMapper;

    public Environment(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        if (Files.notExists(path)) Try.run(() -> Files.createDirectories(path));
    }

    public void createHomeConfig(UserResponse user) {
        Try.of(() -> Files.deleteIfExists(path))
           .map(aBoolean -> path.getParent())
           .filter(Objects::nonNull)
           .andThenTry(parent -> Files.createDirectories(parent))
           .andThenTry(parent -> Files.createFile(path))
           .andFinallyTry(() -> {
               log.debug("user serialization: {}", user);
               String json = objectMapper.writeValueAsString(user);
               log.debug("replace local home store with: {}", json);
               Files.write(path, singletonList(json), UTF_8, TRUNCATE_EXISTING);
           })
           .onFailure(e -> log.info(e.getLocalizedMessage()));
    }

    public Optional<UUID> getAuthentication() {
        return getCurrentUser().map(User::getAggregateId);
    }

    public Optional<User> getCurrentUser() {
        Optional<File> maybeAuthFile = Files.exists(path) ? Optional.of(path.toFile()) : Optional.empty();
        if (!maybeAuthFile.isPresent()) return Optional.empty();
        return maybeAuthFile.map(file -> Try.of(() -> objectMapper.readValue(file, User.class))
                                            .onFailure(e -> log.info(e.getLocalizedMessage())))
                            .filter(Try::isSuccess)
                            .map(Try::get);
    }
}

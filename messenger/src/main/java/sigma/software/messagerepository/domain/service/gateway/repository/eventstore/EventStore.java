package sigma.software.messagerepository.domain.service.gateway.repository.eventstore;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.Predicates;
import io.vavr.control.Try;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sigma.software.messagerepository.domain.User;
import sigma.software.messagerepository.domain.event.api.DomainEvent;

import java.nio.file.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.util.Collections.singletonList;

public class EventStore {

    private static final Logger log = LogManager.getLogger();

    private final ObjectMapper objectMapper;
    private final Path dbBasePath;

    public EventStore(ObjectMapper objectMapper, Path dbBasePath) {
        this.objectMapper = objectMapper;
        this.dbBasePath = dbBasePath;
        postConstruct();
    }

    private void postConstruct() {
        Path dbDir = dbBasePath.toAbsolutePath();
        if (Files.notExists(dbDir, LinkOption.NOFOLLOW_LINKS)) {
            Try.run(() -> Files.createDirectories(dbDir))
               .getOrElseThrow(this::reThrow);
        }
        log.debug("EventStore constructed.");
    }

    /* Public API */

    // step 1: get or create file by filename "${aggregateId}.past.json.log"
    // stream it's content...
    // step 1: get or create file by filename "${aggregateId}.json.log"
    // stream it's content too...
    // step 2: read line by line json strings from "${aggregateId}.json.log" file
    // step 3: using Stream API map them into DomainEvents
    // step 4: collect and return new CopyOnWriteArrayList with events found.
    public Collection<DomainEvent> read(UUID aggregateId) {
        log.debug(aggregateId);
        Path pastEventLog = createAndGetDbFilePath(aggregateId, ".past.json.log");
        try (Stream<String> streamPastEventLog = Try.of(() -> Files.lines(pastEventLog))
                                                    .getOrElseThrow(this::reThrow)
                                                    .filter(Objects::nonNull)
                                                    .map(String::trim)
                                                    .filter(Predicates.not(String::isEmpty))
                                                    .peek(log::info)) {
            Path eventLog = createAndGetDbFilePath(aggregateId, ".json.log");
            try (Stream<String> streamEventLog = Try.of(() -> Files.lines(eventLog))
                                                    .getOrElseThrow(this::reThrow)
                                                    .filter(Objects::nonNull)
                                                    .map(String::trim)
                                                    .filter(Predicates.not(String::isEmpty))
                                                    .peek(log::info)) {
                return Stream.concat(streamPastEventLog, streamEventLog)
                             .map(json -> Try.of(() -> objectMapper.readValue(json, DomainEvent.class))
                                             .getOrElseThrow(this::reThrow))
                             .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
            }
        }
    }

    // step 1: find file by filename "${domainEvent.getAggregateId()}.json.log"
    //   - if file doesn't exists -> create new one
    // step 2: convert domainEvent object into JSON string
    // step 3: if json doesn't contains type field, throw an exception
    // step 4: append JSON string into end of the "${domainEvent.getAggregateId()}.json.log" file
    public void appendAll(DomainEvent... domainEvents) {
        for (DomainEvent domainEvent : domainEvents) {
            append(domainEvent);
        }
    }

    public void append(DomainEvent domainEvent) {
        log.debug(domainEvent);
        Path eventLog = createAndGetDbFilePath(domainEvent.getAggregateId(), ".json.log");
        String json = Try.of(() -> objectMapper.writeValueAsString(domainEvent))
                         .getOrElseThrow(this::reThrow);
        JsonNode jsonNode = Try.of(() -> objectMapper.readTree(json))
                               .getOrElseThrow(this::reThrow);
        if (Objects.nonNull(jsonNode.get("type").asText())) {
            Try.run(() -> Files.write(eventLog, singletonList(json), APPEND));
        }
    }

    // - stream all absolute db paths in db folder
    // - remove if any exists
    public void cleanupAll() {
        log.debug("clearing all *.json* files...");
        cleanupBy(entry -> entry.toString().contains(".json"));
    }

    public void cleanupBy(DirectoryStream.Filter<Path> pathFilter) {
        Try.run(() -> {
            try (DirectoryStream<Path> paths = Files.newDirectoryStream(dbBasePath.toAbsolutePath(), pathFilter)) {
                for (Path path : paths) {
                    log.debug("trying remove {} file from event store", path);
                    Files.deleteIfExists(path);
                }
            }
        }).onFailure(e -> log.error(e.getLocalizedMessage(), e));
    }

    // - find all files in db folder with suffixed filter
    // - take all it's filenames
    // - remove suffix part from each filename
    // - map it into UUID
    // - collect result into list
    public Collection<UUID> findAll() {
        return findAllBy(filename -> filename.endsWith(".past.json.log"));
    }

    public Collection<UUID> findAllBy(Predicate<String> filenamePredicate) {
        String[] files = dbBasePath.toAbsolutePath().toFile().list(
                (dir, filename) -> filenamePredicate.test(filename));
        return Optional.ofNullable(files)
                       .map(Arrays::stream)
                       .orElse(Stream.empty())
                       .peek(file -> log.info("found: {}", file))
                       .map(filename -> filename.replace(".past.json.log", ""))
                       .map(UUID::fromString)
                       .collect(Collectors.toList());
    }

    /* Snapshot API */

    public void snapshot(User aggregate) {
        log.debug("snapshotting: {}", aggregate);
        // place all domain events out from aggregate into fresh new clean ${aggregateId}.json.log file
        appendAll(aggregate.getDomainEvents().toArray(new DomainEvent[0]));
        // clean aggregate.eventStream
        aggregate.getDomainEvents().clear();
        // serialize aggregate into json and replace with it content of ${aggregateId}.snapshot.json file
        saveSnapshot(aggregate);
        // crete ${aggregateId}.past.json.log
        Path pastEventLog = createAndGetDbFilePath(aggregate.getAggregateId(), ".past.json.log");
        // read ${aggregateId}.json.log file and append everything from it, line-by-line into ${aggregateId}.past.json.log
        Path eventLog = createAndGetDbFilePath(aggregate.getAggregateId(), ".json.log");
        List<String> jsons = Try.of(() -> Files.lines(eventLog))
                                .getOrElseThrow(this::reThrow)
                                .filter(Objects::nonNull)
                                .map(String::trim)
                                .filter(Predicates.not(String::isEmpty))
                                .collect(Collectors.toList());
        Try.run(() -> Files.write(pastEventLog, jsons, APPEND));
        // // that just doesn't worked (truncate ${aggregateId}.json.log file) see deletion workaround...
        // Try.run(() -> Files.write(eventLog, new byte[0], StandardOpenOption.TRUNCATE_EXISTING));
        // remove ${aggregateId}.json.log file
        cleanupBy(entry -> entry.toAbsolutePath()
                                .toString()
                                .endsWith(String.format("%s.json.log", aggregate.getAggregateId())));
    }

    /* Private API */

    private void saveSnapshot(User aggregate) {
        Objects.requireNonNull(aggregate);
        Path snapshotFile = createAndGetDbFilePath(aggregate.getAggregateId(), ".snapshot.json");
        Try.of(() -> objectMapper.writeValueAsString(aggregate))
           .andThenTry(json -> Files.write(snapshotFile, singletonList(json)))
           .getOrElseThrow(this::reThrow);
    }

    private Path createAndGetDbFilePath(UUID aggregateId, String suffix) {
        Path dbFilePath = getDbFilePath(aggregateId, suffix);
        if (Files.notExists(dbFilePath, LinkOption.NOFOLLOW_LINKS)) {
            Try.run(() -> Files.createFile(dbFilePath))
               .getOrElseThrow(this::reThrow);
        }
        return dbFilePath;
    }

    private Path getDbFilePath(UUID aggregateId, String suffix) {
        Objects.requireNonNull(aggregateId);
        String logFilename = String.format("%s%s", aggregateId.toString(), suffix);
        String absoluteParentPath = dbBasePath.toAbsolutePath().toString();
        return Paths.get(absoluteParentPath, logFilename);
    }

    private RuntimeException reThrow(Throwable throwable) {
        log.warn(throwable.getLocalizedMessage(), throwable);
        return new RuntimeException(throwable.getClass() + ": " + throwable.getLocalizedMessage());
    }
}

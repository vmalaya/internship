package sigma.software.messagerepository.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.API;
import org.jetbrains.annotations.NotNull;
import org.mapdb.*;
import sigma.software.messagerepository.User;
import sigma.software.messagerepository.event.*;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.Predicates.instanceOf;

/**
 * Pattern Factory
 */
public class EventStore {

    public static final DomainEventSerializer SERIALIZER = new DomainEventSerializer();

    public static EventStore create() {
        return new EventStore();
    }

    private EventStore() {
    }

    public void appendUserEvent(User user) {
        Objects.requireNonNull(user, "user may not be null");

        UUID aggregateId = user.getAggregateId();
        DB db = db(aggregateId);
        Atomic.Var<Collection<DomainEvent>> eventStore = db.atomicVar(aggregateId.toString(), SERIALIZER)
                                                           .createOrOpen();
        Collection<DomainEvent> past = Optional.ofNullable(eventStore.get())
                                               .orElse(new CopyOnWriteArrayList<>());
        Collection<DomainEvent> present = new CopyOnWriteArrayList<>(user.getDomainEvents());
        user.flushEvents();
        Collection<DomainEvent> history = Stream.concat(past.stream(), present.stream())
                                                .collect(Collectors.toList());
        eventStore.set(new CopyOnWriteArrayList<>(history));
        db.commit();
        db.close();
    }

    public User load(User snapshot, UUID aggregateId) {
        Objects.requireNonNull(aggregateId, "aggregate id may not be null");

        DB db = db(aggregateId);
        Atomic.Var<Collection<DomainEvent>> eventStore = db.atomicVar(aggregateId.toString(), SERIALIZER)
                                                           .createOrOpen();
        Collection<DomainEvent> history = Optional.ofNullable(eventStore.get())
                                                  .orElse(new CopyOnWriteArrayList<>());

        return history.isEmpty() ? snapshot : User.recreate(snapshot, history);
    }

    /* Private API. */
    private DB db(UUID aggregateId) {
        return DBMaker.fileDB(String.format("target/user-%s.db", aggregateId))
                      .fileMmapEnableIfSupported()
                      .transactionEnable()
                      .closeOnJvmShutdown()
                      .closeOnJvmShutdownWeakReference()
                      .executorEnable()
                      .concurrencyScale(2)
                      .make();
    }

    public static final class DomainEventsJsonSerializer implements Serializer<Collection<DomainEvent>> {

        private static final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public void serialize(@NotNull DataOutput2 out, @NotNull Collection<DomainEvent> value) throws IOException {
            String jsonString = objectMapper.writeValueAsString(value);
            out.writeUTF(jsonString);
        }

        @Override
        public Collection<DomainEvent> deserialize(@NotNull DataInput2 input, int available) throws IOException {
            String jsonString = input.readUTF();
            return (Collection<DomainEvent>) objectMapper.readValue(jsonString, Collection.class);
        }
    }

    public static final class DomainEventSerializer implements Serializer<Collection<DomainEvent>> {

        private static final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public void serialize(@NotNull DataOutput2 out, @NotNull Collection<DomainEvent> value) throws IOException {
            String jsonString = objectMapper.writeValueAsString(value);
            out.writeUTF(jsonString);
        }

        @Override
        public Collection<DomainEvent> deserialize(@NotNull DataInput2 input, int available) throws IOException {
            String jsonString = input.readUTF();
            Collection<DomainEvent> list = new CopyOnWriteArrayList<>((Collection<DomainEvent>) objectMapper.readValue(jsonString, Collection.class));
            io.vavr.collection.List.ofAll(list).forEach(event -> API.Match(event).of(
                    Case($(instanceOf(UserCreatedEvent.class)), e -> ),
                    Case($(instanceOf(FriendRequestSentEvent.class)), this::on),
                    Case($(instanceOf(FriendRequestAcceptedEvent.class)), this::on),
                    Case($(instanceOf(FriendRequestDeclinedEvent.class)), this::on),
                    Case($(instanceOf(MessageSentEvent.class)), this::on),
                    Case($(instanceOf(MessageReceivedEvent.class)), this::on)
            );
            return new CopyOnWriteArrayList<>((Collection<DomainEvent>) objectMapper.readValue(jsonString,
                                                                                               Collection.class));
        }
    }
}

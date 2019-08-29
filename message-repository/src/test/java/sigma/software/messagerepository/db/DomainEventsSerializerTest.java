package sigma.software.messagerepository.db;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.mapdb.Atomic;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import sigma.software.messagerepository.User;
import sigma.software.messagerepository.event.DomainEvent;
import sigma.software.messagerepository.event.FriendRequestAcceptedEvent;
import sigma.software.messagerepository.event.UserCreatedEvent;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DomainEventsSerializerTest {

    @Test
    void test() {
        UUID aggregateId = UUID.randomUUID();
        DB db = db(aggregateId);
        Atomic.Var<Collection<DomainEvent>> eventStore = db.atomicVar(aggregateId.toString(), EventStore.SERIALIZER)
                                                           .createOrOpen();
        Collection<DomainEvent> past = Optional.ofNullable(eventStore.get())
                                               .orElse(new CopyOnWriteArrayList<>());
        Collection<DomainEvent> present = Arrays.asList(new UserCreatedEvent(UUID.randomUUID(), "TEST"),
                                                        new FriendRequestAcceptedEvent(UUID.randomUUID(),UUID.randomUUID()));
        Collection<DomainEvent> history = Stream.concat(past.stream(), present.stream())
                                                .collect(Collectors.toList());
        eventStore.set(new CopyOnWriteArrayList<>(history));

        db.commit();
        db.close();

        DB db2 = db(aggregateId);
        Atomic.Var<Collection<DomainEvent>> eventStoreFromDb = db2.atomicVar(aggregateId.toString(), EventStore.SERIALIZER)
                                                           .createOrOpen();
        Collection<DomainEvent> historyFromDb = Optional.ofNullable(eventStoreFromDb.get())
                                                  .orElse(new CopyOnWriteArrayList<>());
        User snapshot = new User();

        User recreated = historyFromDb.isEmpty() ? snapshot : User.recreate(snapshot, historyFromDb);

        assertThat(recreated).isNotNull();
        assertThat(recreated.getFriends()).isNotEmpty();
    }

    @NotNull
    private DB db(UUID aggregateId) {
        return DBMaker.fileDB(String.format("target/test-%s.db", aggregateId))
                      .fileMmapEnableIfSupported()
                      .transactionEnable()
                      .closeOnJvmShutdown()
                      .closeOnJvmShutdownWeakReference()
                      .executorEnable()
                      .concurrencyScale(2)
                      .make();
    }
}
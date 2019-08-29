package sigma.software.messagerepository.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mapdb.Atomic;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;
import sigma.software.messagerepository.event.DomainEvent;
import sigma.software.messagerepository.event.MessageSentEvent;
import sigma.software.messagerepository.event.UserCreatedEvent;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class MapDbTest {

    // @Test
    // void should_serialize() throws JsonProcessingException {
    //
    //     // given:
    //     DB db = DBMaker.fileDB("target/should_create_atomic_variable.db")
    //                    .fileMmapEnableIfSupported()
    //                    .transactionEnable()
    //                    .closeOnJvmShutdown()
    //                    .make();
    //     Serializer<Collection<? extends DomainEvent>> serializer = new Serializer<Collection<? extends DomainEvent>>() {
    //         private final ObjectMapper objectMapper = new ObjectMapper();
    //
    //         @Override
    //         public void serialize(@NotNull DataOutput2 out, @NotNull Collection<? extends DomainEvent> value) throws IOException {
    //             String jsonString = objectMapper.writeValueAsString(value);
    //             out.writeUTF(jsonString);
    //         }
    //
    //         @Override
    //         public Collection<? extends DomainEvent> deserialize(@NotNull DataInput2 input, int available) throws IOException {
    //             String jsonString = input.readUTF();
    //             return objectMapper.readValue(jsonString, new TypeReference<Collection<? extends DomainEvent>>() {
    //             });
    //         }
    //     };
    //     Collection<DomainEvent> events = Arrays.asList(
    //             new UserCreatedEvent(UUID.randomUUID(), "TEST_USERNAME"),
    //             new UserCreatedEvent(UUID.randomUUID(), "TEST_USERNAME_2")
    //     );
    //
    //     // when:
    //     db.atomicVar("test", serializer, events)
    //       .createOrOpen();
    //     // and
    //     db.commit();
    //
    //     // then:
    //     Collection<? extends DomainEvent> collection = db.atomicVar("test", serializer).open().get();
    //     assertThat(collection).containsExactly(events.toArray(new DomainEvent[0]));
    //     // clean up:
    //     db.close();
    // }

    @Test
    void should_create_atomic_variable() throws JsonProcessingException {

        // given:
        DB db = DBMaker.fileDB("target/should_create_atomic_variable.db")
                       .fileMmapEnableIfSupported()
                       .transactionEnable()
                       .closeOnJvmShutdown()
                       .make();
        ObjectMapper objectMapper = new ObjectMapper();
        List<UserCreatedEvent> events = Arrays.asList(
                new UserCreatedEvent(UUID.randomUUID(), "TEST_USERNAME"),
                new UserCreatedEvent(UUID.randomUUID(), "TEST_USERNAME_2")
        );
        String value = objectMapper.writeValueAsString(events);

        // when:
        db.atomicVar("test", Serializer.STRING, value)
          .createOrOpen();
        // and
        db.commit();

        // then:
        DB.AtomicVarMaker<String> fromDb = db.atomicVar("test", Serializer.STRING);
        // and
        assertThat(fromDb.createOrOpen().get()).contains("TEST_USERNAME");
        // clean up:
        db.close();
    }

    @Test
    void should_update_atomic_variable() throws JsonProcessingException {

        // given:
        DB db = DBMaker.fileDB("target/should_update_atomic_variable.db")
                       .fileMmapEnableIfSupported()
                       .transactionEnable()
                       .closeOnJvmShutdown()
                       .make();
        ObjectMapper objectMapper = new ObjectMapper();
        Collection<DomainEvent> events = Arrays.asList(
                new UserCreatedEvent(UUID.randomUUID(), "TEST_USERNAME")
        );
        String value = objectMapper.writeValueAsString(events);
        Object o = objectMapper.readValue(value, Object.class);
        System.out.println(o);
        Collection collection = objectMapper.readValue(value, Collection.class);
        System.out.println(collection);
        System.out.println((Collection<DomainEvent>) collection);
        // // when:
        // db.atomicVar("test", Serializer.STRING, value)
        //   .createOrOpen();
        // // and
        // db.commit();
        //
        // // then:
        // DB.AtomicVarMaker<String> fromDb = db.atomicVar("test", Serializer.STRING);
        // // and
        // assertThat(fromDb.createOrOpen().get()).contains("TEST_USERNAME");
        // clean up:
        db.close();
    }

    @Test
    void should_get_non_existed_variable() throws JsonProcessingException {

        // given:
        DB db = DBMaker.fileDB("target/should_get_non_existed_variable_" + UUID.randomUUID())
                       .fileMmapEnableIfSupported()
                       .transactionEnable()
                       .closeOnJvmShutdown()
                       .closeOnJvmShutdownWeakReference()
                       .executorEnable()
                       .concurrencyScale(2)
                       .make();

        Atomic.Var<String> json = db.atomicVar(UUID.randomUUID().toString(), Serializer.STRING).createOrOpen();
        String jsonString = json.get();
        assertThat(jsonString).isNull();

        Optional<String> maybeString = Optional.ofNullable(jsonString);
        if (!maybeString.isPresent()) {
            json.set("[]");
            db.commit();
        }
        Collection<DomainEvent> collection;
        // Collection<DomainEvent> past = eventStore.getOrDefault(aggregateId, new CopyOnWriteArrayList<>());
        db.close();
    }
}

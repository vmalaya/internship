package sigma.software.messagerepository.domain.service.gateway.repository.eventstore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sigma.software.messagerepository.config.EventStoreConfig;
import sigma.software.messagerepository.config.JacksonConfig;
import sigma.software.messagerepository.domain.event.FriendRequestSentEvent;
import sigma.software.messagerepository.domain.event.UserCreatedEvent;
import sigma.software.messagerepository.domain.event.api.DomainEvent;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class EventStoreTest {

    private EventStore eventStore = new EventStore(JacksonConfig.objectMapper, EventStoreConfig.dbBasePath);

    @BeforeEach
    void tearDown() {
        eventStore.cleanupAll();
    }

    @Test
    void should_store_events() {
        // given:
        DomainEvent[] domainEvents = Arrays.asList(
                new UserCreatedEvent(UUID.fromString("00000000-0000-0000-0000-000000000000"), "maksim"),
                new UserCreatedEvent(UUID.fromString("11111111-1111-1111-1111-111111111111"), "valentina"),
                new FriendRequestSentEvent(UUID.fromString("00000000-0000-0000-0000-000000000000"),
                                           UUID.fromString("11111111-1111-1111-1111-111111111111")))
                                           .toArray(new DomainEvent[0]);
        // when
        eventStore.appendAll(domainEvents);

        // then:
        Collection<DomainEvent> events = eventStore.read(UUID.fromString("00000000-0000-0000-0000-000000000000"));
        assertThat(events).contains(new UserCreatedEvent(UUID.fromString("00000000-0000-0000-0000-000000000000"),
                                                         "maksim"),
                                    new FriendRequestSentEvent(UUID.fromString("00000000-0000-0000-0000-000000000000"),
                                                               UUID.fromString("11111111-1111-1111-1111-111111111111")));
    }
}

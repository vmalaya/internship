package sigma.software.messagerepository.domain.service.gateway.repository.eventstore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sigma.software.messagerepository.domain.event.FriendRequestSentEvent;
import sigma.software.messagerepository.domain.event.UserCreatedEvent;
import sigma.software.messagerepository.domain.event.api.DomainEvent;
import sigma.software.messagerepository.domain.service.gateway.repository.eventstore.config.EventStoreConfig;
import sigma.software.messagerepository.domain.service.gateway.repository.eventstore.config.JacksonConfig;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class EventStoreTest {

    EventStore eventStore = new EventStore(new JacksonConfig(), new EventStoreConfig());

    @BeforeEach
    void tearDown() {
        eventStore.cleanup();
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
        eventStore.append(domainEvents);

        // then:
        Collection<DomainEvent> events = eventStore.read(UUID.fromString("00000000-0000-0000-0000-000000000000"));
        assertThat(events).contains(new UserCreatedEvent(UUID.fromString("00000000-0000-0000-0000-000000000000"), "maksim"),
                                    new FriendRequestSentEvent(UUID.fromString("00000000-0000-0000-0000-000000000000"),
                                                               UUID.fromString("11111111-1111-1111-1111-111111111111")));
    }
}

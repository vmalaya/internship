package sigma.software.messagerepository.db;

import org.junit.jupiter.api.Test;
import sigma.software.messagerepository.User;
import sigma.software.messagerepository.command.AcceptFriendRequestCommand;
import sigma.software.messagerepository.command.CreateUserCommand;
import sigma.software.messagerepository.command.SendFriendRequestCommand;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EventStoreTest {

    @Test
    void should_create_event_store() {

        // when:
        EventStore eventStore = EventStore.create();

        // then:
        assertThat(eventStore).isNotNull();
    }

    @Test
    void should_append_events() {

        // given:
        EventStore eventStore = EventStore.create();
        // and
        User user = new User();
        UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000000");
        user.handle(new CreateUserCommand(userId, "valentyna.mala"));
        user.handle(new SendFriendRequestCommand(UUID.fromString("11111111-1111-1111-1111-111111111111")));

        // when:
        eventStore.appendUserEvent(user);
    }
}
package sigma.software.messagerepository.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import sigma.software.messagerepository.domain.command.*;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UserTest {

    @Test
    void should_create_user() {

        // given:
        User user = new User();
        // and
        UUID id = UUID.randomUUID();

        // when:
        user.handle(new CreateUserCommand(id, "valentyna.mala"));

        // then:
        assertThat(user.getAggregateId()).isNotNull()
                                         .isEqualTo(id);
        // and:
        assertThat(user.getUsername()).isEqualTo("valentyna.mala");
    }

    @Test
    void should_not_store_outgoing_friend_requests() {

        // given:
        User user = new User();
        // and
        UUID id = UUID.randomUUID();

        // when:
        user.handle(new CreateUserCommand(id, "valentyna.mala"));
        // and
        user.handle(new SendFriendRequestCommand(id, UUID.fromString("00000000-0000-0000-0000-000000000000")));

        // then:
        assertThat(user.getFriendRequest()).hasSize(0);
    }

    @Test
    void should_accept_friend_request() {

        // given:
        User user = new User();
        UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000000");
        user.handle(new CreateUserCommand(userId, "valentyna.mala"));
        // and
        User friend = new User();
        UUID friendId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        friend.handle(new CreateUserCommand(friendId, "valentyna.mala"));

        // when:
        user.handle(new SendFriendRequestCommand(userId, friendId));
        // and
        friend.handle(new AcceptFriendRequestCommand(friendId, userId));

        // then:
        assertThat(friend.getFriends()).hasSize(1);
    }

    @Test
    void should_decline_friend_request() {

        // given:
        User user = new User();
        UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000000");
        user.handle(new CreateUserCommand(userId, "valentyna.mala"));
        // and
        User friend = new User();
        UUID friendId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        friend.handle(new CreateUserCommand(friendId, "valentyna.mala"));

        // when:
        user.handle(new SendFriendRequestCommand(userId, friendId));
        // and
        friend.handle(new DeclineFriendRequestCommand(friendId, userId));

        // then:
        assertThat(friend.getFriends()).hasSize(0);
    }

    @Test
    public void should_send_message() {

        // given:
        User user = new User();
        user.handle(new CreateUserCommand(UUID.randomUUID(), "valentyna.mala"));
        // and
        User friend = new User();
        friend.handle(new CreateUserCommand(UUID.randomUUID(), "friend"));
        // and
        user.handle(new SendFriendRequestCommand(user.getAggregateId(), friend.getAggregateId()));
        // and
        friend.handle(new AcceptFriendRequestCommand(friend.getAggregateId(), user.getAggregateId()));
        user.handle(new AcceptFriendRequestCommand(user.getAggregateId(), friend.getAggregateId()));

        // when:
        user.handle(new SendMessageCommand(user.getAggregateId(), friend.getAggregateId(), "Hi"));

        // then:
        assertThat(user.getMessages()).hasSize(1);
    }

    @Test
    public void should_receive_message() {

        // given:
        User user = new User();
        user.handle(new CreateUserCommand(UUID.randomUUID(), "valentyna.mala"));
        // and
        User friend = new User();
        friend.handle(new CreateUserCommand(UUID.randomUUID(), "friend"));
        // and
        user.handle(new SendFriendRequestCommand(user.getAggregateId(), friend.getAggregateId()));
        // and
        friend.handle(new AcceptFriendRequestCommand(friend.getAggregateId(), user.getAggregateId()));
        user.handle(new AcceptFriendRequestCommand(user.getAggregateId(), friend.getAggregateId()));

        // when:
        user.handle(new ReceiveMessageCommand(user.getAggregateId(), friend.getAggregateId(), "Hi"));

        // then:
        assertThat(user.getMessages()).hasSize(1);
    }

    @Test
    public void test() {
        // given:
        User user = new User();
    }
}

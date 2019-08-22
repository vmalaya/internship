package sigma.software.messagerepository;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import sigma.software.messagerepository.command.AcceptFriendRequestCommand;
import sigma.software.messagerepository.command.CreateUserCommand;
import sigma.software.messagerepository.command.DeclineFriendRequestCommand;
import sigma.software.messagerepository.command.SendFriendRequestCommand;

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
        assertThat(user.getId()).isNotNull()
                                .isEqualTo(id);
        // and:
        assertThat(user.getUsername()).isEqualTo("valentyna.mala");
    }

    @Test
    void should_send_friend_request() {

        // given:
        User user = new User();
        // and
        UUID id = UUID.randomUUID();

        // when:
        user.handle(new CreateUserCommand(id, "valentyna.mala"));
        // and
        user.handle(new SendFriendRequestCommand(UUID.fromString("00000000-0000-0000-0000-000000000000")));

        // then:
        assertThat(user.getFriendRequest()).hasSize(1);
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
        user.handle(new SendFriendRequestCommand(friendId));
        // and
        friend.handle(new AcceptFriendRequestCommand(userId));

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
        user.handle(new SendFriendRequestCommand(friendId));
        // and
        friend.handle(new DeclineFriendRequestCommand(userId));

        // then:
        assertThat(friend.getFriends()).hasSize(0);
    }
}
package sigma.software.messagerepository.domain.service;

import org.junit.jupiter.api.Test;
import sigma.software.messagerepository.domain.User;

import java.io.IOException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest {
    UserService userService = new UserService();

    @Test
    void should_sign_up() {
        // given:
        String username = "valentina.mala";

        // when:
        UUID userId = userService.signup(username, UUID.randomUUID());

        // then:
        assertThat(userId).isNotNull();
    }

    @Test
    void should_sign_in() throws IOException {
        // given:
        UUID userId = userService.signup("valentina.mala", UUID.randomUUID());

        // when:
        String username = userService.signin(UUID.fromString(userId.toString()));

        // then:
        assertThat(username).isEqualTo("valentina.mala");
    }

    @Test
    void should_invite_friend() {
        // given:
        UUID friendId = userService.signup("friend", UUID.randomUUID());
        userService.signup("valentina.mala", UUID.randomUUID());

        // when:
        UUID invitedFriendId = userService.invite(friendId);
        // and
        userService.signin(friendId);

        // then:
        assertThat(invitedFriendId).isEqualTo(friendId);
        // and:
        User currentUser = userService.getCurrentUser().get();
        assertThat(currentUser.getFriendRequest()).hasSize(1);
    }
}

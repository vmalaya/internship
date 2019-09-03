package sigma.software.messagerepository.domain.service;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest {
    UserService userService = new UserService();

    @Test
    void should_sign_up() {
        // given:
        String username = "valentina.mala";

        // when:
        UUID userId = userService.signup(username);

        // then:
        assertThat(userService.repository.load(userId)).isNotNull();
    }

    @Test
    void should_sign_in() throws IOException {
        // given:
        UUID userId = userService.signup("valentina.mala");

        // when:
        String username = userService.signin(userId.toString());

        // then:
        assertThat(username).isEqualTo("valentina.mala");
    }

    @Test
    void should_invite_friend() throws IOException {
        // given:
        UUID friendId = userService.signup("friend");
        UUID userId = userService.signup("valentina.mala");

        // when:
        String invitedFriendId = userService.invite(friendId.toString());

        //
        assertThat(UUID.fromString(invitedFriendId)).isEqualTo(friendId);
    }
}

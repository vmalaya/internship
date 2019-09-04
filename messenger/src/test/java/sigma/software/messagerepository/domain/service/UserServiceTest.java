package sigma.software.messagerepository.domain.service;

import org.junit.jupiter.api.Test;
import sigma.software.messagerepository.domain.User;
import sigma.software.messagerepository.domain.service.insftastructure.Result;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest {
    UserService userService = new UserService();

    @Test
    void should_sign_up() {
        // when:
        Result signup = userService.signup("signup", "valentina.mala");

        // then:
        assertThat(signup).isEqualTo(Result.OK);
    }

    @Test
    void should_sign_in() {
        // given:
        userService.signup("signup", "valentina.mala");
        Optional<User> currentUser = userService.getCurrentUser();

        // when:
        Result signin = userService.signin("signin", currentUser.get().getAggregateId().toString());

        // then:
        assertThat(signin).isEqualTo(Result.OK);
    }

    @Test
    void should_invite_friend() {
        // given:
        userService.signup("signup", "friend");
        UUID friendId = userService.getCurrentUser().get().getAggregateId();
        userService.signup("signup", "valentina.mala");

        // when:
        Result invited = userService.invite("invite", friendId.toString());

        // then:
        assertThat(invited).isEqualTo(Result.OK);
        // and
        userService.signin("signin", friendId.toString());
        Optional<User> currentUser = userService.getCurrentUser();
        assertThat(currentUser.get().getFriendRequest()).hasSize(1);
    }

    @Test
    void should_get_invites() {
        // given
        userService.signup("signup", "user");
        String userId = userService.getCurrentUser().get().getAggregateId().toString();
        // and
        userService.signup("signup", "friend1");
        userService.invite("invite", userId);
        // and
        userService.signup("signup", "friend2");
        userService.invite("invite", userId);
        // and
        userService.signin("signin", userId);

        // when:
        Result invites = userService.invites("invites");

        // then:
        assertThat(invites).isEqualTo(Result.OK);
    }

    @Test
    void should_accept_friend_request() {
        // given:
        userService.signup("signup", "friend");
        UUID friendId = userService.getCurrentUser().get().getAggregateId();
        userService.signup("signup", "valentina.mala");
        // and
        userService.invite("invite", friendId.toString());

        // when:
        userService.signin("signin", friendId.toString());
        UUID me = userService.getCurrentUser().get().getAggregateId();
        Result accept = userService.accept("accept", me.toString());

        // then:
        assertThat(accept).isEqualTo(Result.OK);
    }

    @Test
    void should_decline_request() {
        // given:
        userService.signup("signup", "friend");
        UUID friendId = userService.getCurrentUser().get().getAggregateId();
        userService.signup("signup", "valentina.mala");
        // and
        userService.invite("invite", friendId.toString());

        // when:
        userService.signin("signin", friendId.toString());
        UUID me = userService.getCurrentUser().get().getAggregateId();
        Result accept = userService.accept("decline", me.toString());

        // then:
        assertThat(accept).isEqualTo(Result.OK);
    }

    @Test
    void should_get_friends() {
        // given:
        userService.signup("signup", "friend");
        UUID friendId = userService.getCurrentUser()
                                   .orElseThrow(RuntimeException::new)
                                   .getAggregateId();
        userService.signup("signup", "valentina.mala");
        // and
        userService.invite("invite", friendId.toString());
        // and
        userService.signin("signin", friendId.toString());
        UUID me = userService.getCurrentUser().get().getAggregateId();
        Result accept = userService.accept("accept", me.toString());

        // when:
        userService.friends("friends");

        // then:
        assertThat(accept).isEqualTo(Result.OK);
    }
}

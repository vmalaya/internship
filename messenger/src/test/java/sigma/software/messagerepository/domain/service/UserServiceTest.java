package sigma.software.messagerepository.domain.service;

import org.junit.jupiter.api.Test;
import sigma.software.messagerepository.config.EventStoreConfig;
import sigma.software.messagerepository.config.JacksonConfig;
import sigma.software.messagerepository.domain.User;
import sigma.software.messagerepository.domain.service.gateway.CommandGateway;
import sigma.software.messagerepository.domain.service.gateway.QueryGateway;
import sigma.software.messagerepository.domain.service.gateway.repository.UserRepository;
import sigma.software.messagerepository.domain.service.gateway.repository.eventstore.EventStore;
import sigma.software.messagerepository.domain.service.insftastructure.Environment;
import sigma.software.messagerepository.domain.service.insftastructure.Result;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest {

    private Environment environment = new Environment(JacksonConfig.objectMapper);
    private EventStore eventStore = new EventStore(JacksonConfig.objectMapper, EventStoreConfig.dbBasePath);
    private UserRepository userRepository = new UserRepository(eventStore);
    private QueryGateway queryGateway = new QueryGateway(userRepository);
    private CommandGateway commandGateway = new CommandGateway(userRepository);
    private UserService userService = new UserService(environment, queryGateway, commandGateway);

    @Test
    void should_sign_up() {
        // when:
        Result signup = userService.signUp("signup", "valentina.mala");

        // then:
        assertThat(signup).isEqualTo(Result.OK);
    }

    @Test
    void should_sign_in() {
        // given:
        userService.signUp("signup", "valentina.mala");
        Optional<User> currentUser = environment.getCurrentUser();
        assertThat(currentUser).isPresent();

        // when:
        Result signin = userService.signIn("signin", currentUser.get().getAggregateId().toString());

        // then:
        assertThat(signin).isEqualTo(Result.OK);
    }

    @Test
    void should_invite_friend() {
        // given:
        userService.signUp("signup", "friend");
        UUID friendId = environment.getAuthentication().get();
        userService.signUp("signup", "valentina.mala");

        // when:
        Result invited = userService.invite("invite", friendId.toString());

        // then:
        assertThat(invited).isEqualTo(Result.OK);
        userService.signIn("signin", friendId.toString());
        Optional<User> currentUser = environment.getCurrentUser();
        assertThat(currentUser).isPresent();
        currentUser.ifPresent(u -> assertThat(u.getFriendRequest()).hasSize(1));
    }

    @Test
    void should_get_invites() {
        // given
        userService.signUp("signup", "user");
        Optional<User> currentUser = environment.getCurrentUser();
        assertThat(currentUser).isPresent();
        String userId = currentUser.get().getAggregateId().toString();
        // and
        userService.signUp("signup", "friend1");
        userService.invite("invite", userId);
        // and
        userService.signUp("signup", "friend2");
        userService.invite("invite", userId);
        // and
        userService.signIn("signin", userId);

        // when:
        Result invites = userService.invites("invites");

        // then:
        assertThat(invites).isEqualTo(Result.OK);
    }

    @Test
    void should_accept_friend_request() {
        // given:
        userService.signUp("signup", "friend");
        Optional<User> currentUser = environment.getCurrentUser();
        assertThat(currentUser).isPresent();
        //
        UUID friendId = currentUser.get().getAggregateId();
        userService.signUp("signup", "valentina.mala");
        // and
        userService.invite("invite", friendId.toString());

        // when:
        userService.signIn("signin", friendId.toString());
        UUID me = currentUser.get().getAggregateId();
        Result accept = userService.accept("accept", me.toString());

        // then:
        assertThat(accept).isEqualTo(Result.OK);
    }

    @Test
    void should_decline_request() {
        // given:
        userService.signUp("signup", "friend");
        Optional<User> currentUser = environment.getCurrentUser();
        assertThat(currentUser).isPresent();
        //
        UUID friendId = currentUser.get().getAggregateId();
        userService.signUp("signup", "valentina.mala");
        // and
        userService.invite("invite", friendId.toString());

        // when:
        userService.signIn("signin", friendId.toString());
        UUID me = currentUser.get().getAggregateId();
        Result accept = userService.accept("decline", me.toString());

        // then:
        assertThat(accept).isEqualTo(Result.OK);
    }

    @Test
    void should_get_friends() {
        // given:
        userService.signUp("signup", "friend");
        Optional<User> currentUser = environment.getCurrentUser();
        assertThat(currentUser).isPresent();
        //
        UUID friendId = currentUser.get().getAggregateId();
        userService.signUp("signup", "valentina.mala");
        // and
        userService.invite("invite", friendId.toString());
        // and
        userService.signIn("signin", friendId.toString());
        UUID me = currentUser.get().getAggregateId();
        Result accept = userService.accept("accept", me.toString());

        // when:
        userService.friends("friends");

        // then:
        assertThat(accept).isEqualTo(Result.OK);
    }
}

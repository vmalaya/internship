package sigma.software.messagerepository.domain.service.gateway.repository;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import sigma.software.messagerepository.domain.User;
import sigma.software.messagerepository.domain.command.CreateUserCommand;
import sigma.software.messagerepository.domain.command.SendFriendRequestCommand;
import sigma.software.messagerepository.domain.service.gateway.repository.UserRepository;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UserRepositoryTest {

    UserRepository userRepository = new UserRepository();

    @Test
    void should_save_user() {

        // given:
        User user = new User();
        // and
        UUID id = UUID.randomUUID();
        // and
        user.handle(new CreateUserCommand(id, "valentyna.mala"));

        // when:
        userRepository.save(user);

        // then:
        assertThat(user.getUsername()).isEqualTo("valentyna.mala");
        // and:
        assertThat(user.getDomainEvents()).isEmpty();
    }

    @Test
    void should_load_user() {

        // given:
        User user = new User();
        // and
        UUID id = UUID.randomUUID();
        // and
        user.handle(new CreateUserCommand(id, "valentyna.mala"));

        // when:
        userRepository.save(user);

        // then:
        User loaded = userRepository.load(id);
        // and:
        assertThat(loaded.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    void should_load_user_from_snapshot() {

        // given:
        User user = new User();
        user.handle(new CreateUserCommand(UUID.randomUUID(), "valentyna.mala"));
        userRepository.save(user);
        // and:
        User snapshot = userRepository.load(user.getAggregateId());
        assertThat(snapshot).isEqualTo(user);

        // when:
        UUID friendId = UUID.randomUUID();
        SendFriendRequestCommand intention = new SendFriendRequestCommand(user.getAggregateId(), friendId);
        user.handle(intention);
        userRepository.save(user);
        assertThat(user.getFriendRequest()).hasSize(1);

        // then:
        User latest = userRepository.load(snapshot, user.getAggregateId());
        // and:
        assertThat(latest == snapshot).isTrue();
        // and:
        assertThat(snapshot.getFriendRequest()).hasSize(1)
                                               .containsExactly(friendId);
    }
}

package sigma.software.messagerepository;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import sigma.software.messagerepository.command.ChangeUsernameCommand;
import sigma.software.messagerepository.command.CreateUserCommand;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UserRepositoryTest {

    UserRepository userRepository = new UserRepository();

    @Test
    void Should_save_user() {

        // given:
        User user = new User();
        // and
        UUID id = UUID.randomUUID();
        // and
        user.handle(new CreateUserCommand(id, "valentyna.mala"));

        // when:
        userRepository.save(user);

        // then:
        assertThat(user.getDomainEvents()).isEmpty();
        // and:
        User foundUser = userRepository.find(id);
        // and:
        assertThat(foundUser).isEqualTo(user);
        // or:
        // assertThat(foundUser.getId()).isEqualTo(user.getId());
        // assertThat(foundUser.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    void Should_change_username() {

        // given:
        User user = new User();
        // and
        UUID id = UUID.randomUUID();
        // and
        user.handle(new CreateUserCommand(id, "bob"));
        // and
        user.handle(new ChangeUsernameCommand(id, "bob", "jack"));

        // when:
        userRepository.save(user);

        // then:
        User foundUser = userRepository.find(id);
        // and:
        assertThat(foundUser.getUsername()).isEqualTo("jack");
    }
}
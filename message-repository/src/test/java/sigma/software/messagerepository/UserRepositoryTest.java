package sigma.software.messagerepository;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import sigma.software.messagerepository.command.CreateUserCommand;

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
        assertThat(user.getDomainEvents()).isEmpty();
        // and:
        User foundUser = userRepository.find(id);
        // and:
        assertThat(foundUser).isEqualTo(user);
        // or:
        // assertThat(foundUser.getId()).isEqualTo(user.getId());
        // assertThat(foundUser.getUsername()).isEqualTo(user.getUsername());
    }

}
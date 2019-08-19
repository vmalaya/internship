package sigma.software.messagerepository;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import sigma.software.messagerepository.User;
import sigma.software.messagerepository.command.CreateUserCommand;
import sigma.software.messagerepository.UserRepository;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UserTest {

    UserRepository userRepository = new UserRepository();

    @Test
    void User_should_be_create() {

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
        // and:
        // User user = userRepository.find(id);
    }

    @Test
    void As_a_user_I_should_not_be_able_to_save_null_message() {

        // given:

        // when:

        // then:
    }

    @Test
    void As_a_user_I_want_to_be_able_to_save_new_message() {

        // given:

        // when:

        // then:
    }
}
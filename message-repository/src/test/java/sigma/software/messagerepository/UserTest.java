package sigma.software.messagerepository;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import sigma.software.messagerepository.command.CreateUserCommand;
import sigma.software.messagerepository.command.SaveMessageCommand;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UserTest {

    UserRepository userRepository = new UserRepository();

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
        // and:
        // User user = userRepository.find(id);
    }

    @Test
    void should_not_be_able_to_save_null_message_as_a_user() {

        // given:
        User user = new User();
        // and
        UUID userId = UUID.randomUUID();
        // and
        UUID messageId = UUID.randomUUID();
        // and
        user.handle(new CreateUserCommand(userId, "valentyna.mala"));

        // then:
        assertThatThrownBy(() -> {
            user.handle(new SaveMessageCommand(messageId, null, "mr.receiver"));
        }).isInstanceOf(IllegalArgumentException.class).hasMessage("message may not be null");
    }

    @Test
    void should_save_new_message() {

        // given:
        User user = new User();
        // and
        UUID userId = UUID.randomUUID();
        // and
        UUID messageId = UUID.randomUUID();
        // and
        user.handle(new CreateUserCommand(userId, "valentyna.mala"));

        // when:
        user.handle(new SaveMessageCommand(messageId, "valentyna.mala", "mr.receiver"));

        // then:
        assertThat(user.getMessageRepository().find(user, messageId)).isTrue();
    }
}
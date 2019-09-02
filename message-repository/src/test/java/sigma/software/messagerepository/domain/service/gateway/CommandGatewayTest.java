package sigma.software.messagerepository.domain.service.gateway;

import org.junit.jupiter.api.Test;
import sigma.software.messagerepository.domain.User;
import sigma.software.messagerepository.domain.command.CreateUserCommand;
import sigma.software.messagerepository.domain.service.gateway.CommandGateway;
import sigma.software.messagerepository.domain.service.gateway.repository.UserRepository;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CommandGatewayTest {

    private final UserRepository repository = new UserRepository();
    private final CommandGateway commandGateway = new CommandGateway(repository);

    @Test
    void should_create_user() {
        // given:
        CreateUserCommand createUserCommand = new CreateUserCommand(UUID.randomUUID(), "maksim.kostromin");

        // when:
        commandGateway.handle(createUserCommand);

        // then:
        User user = repository.load(createUserCommand.getId());
        assertThat(user.getUsername()).containsIgnoringCase("maksim");
    }
}

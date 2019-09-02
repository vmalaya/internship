package sigma.software.messagerepository.domain.service.gateway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sigma.software.messagerepository.domain.User;
import sigma.software.messagerepository.domain.command.CreateUserCommand;
import sigma.software.messagerepository.domain.service.gateway.repository.UserRepository;
import sigma.software.messagerepository.domain.service.gateway.repository.eventstore.EventStore;
import sigma.software.messagerepository.domain.service.gateway.repository.eventstore.config.EventStoreConfig;
import sigma.software.messagerepository.domain.service.gateway.repository.eventstore.config.JacksonConfig;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CommandGatewayTest {

    EventStore eventStore = new EventStore(new JacksonConfig(), new EventStoreConfig());
    UserRepository repository = new UserRepository(eventStore);
    CommandGateway commandGateway = new CommandGateway(repository);

    @BeforeEach
    void setUp() {
        eventStore.cleanup();
    }

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

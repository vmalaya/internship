package sigma.software.messagerepository.domain.service.gateway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sigma.software.messagerepository.domain.User;
import sigma.software.messagerepository.domain.command.CreateUserCommand;
import sigma.software.messagerepository.domain.query.UserRequest;
import sigma.software.messagerepository.domain.query.UserResponse;
import sigma.software.messagerepository.domain.query.api.QueryResponse;
import sigma.software.messagerepository.domain.service.gateway.repository.UserRepository;
import sigma.software.messagerepository.domain.service.gateway.repository.eventstore.EventStore;
import sigma.software.messagerepository.domain.service.gateway.repository.eventstore.config.EventStoreConfig;
import sigma.software.messagerepository.domain.service.gateway.repository.eventstore.config.JacksonConfig;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class QueryGatewayTest {

    EventStore eventStore = new EventStore(new JacksonConfig(), new EventStoreConfig());
    UserRepository repository = new UserRepository(eventStore);
    QueryGateway queryGateway = new QueryGateway(repository);

    @BeforeEach
    void setUp() {
        eventStore.cleanup();
    }

    @Test
    void should_query_user() {
        // given:
        User user = new User();
        UUID id = UUID.randomUUID();

        user.handle(new CreateUserCommand(id, "valentyna.mala"));
        repository.save(user);

        // when:
        QueryResponse response = queryGateway.apply(new UserRequest(id));

        // then:
        assertThat(response).isNotNull();
        assertThat(response).isInstanceOf(UserResponse.class);

        UserResponse userResponse = UserResponse.class.cast(response);
        assertThat(userResponse.getUsername()).isEqualTo("valentyna.mala");
    }
}

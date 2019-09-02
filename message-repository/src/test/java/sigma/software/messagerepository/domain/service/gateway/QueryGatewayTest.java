package sigma.software.messagerepository.domain.service.gateway;

import org.junit.jupiter.api.Test;
import sigma.software.messagerepository.domain.User;
import sigma.software.messagerepository.domain.command.CreateUserCommand;
import sigma.software.messagerepository.domain.query.UserRequest;
import sigma.software.messagerepository.domain.query.UserResponse;
import sigma.software.messagerepository.domain.query.api.QueryResponse;
import sigma.software.messagerepository.domain.service.gateway.QueryGateway;
import sigma.software.messagerepository.domain.service.gateway.repository.UserRepository;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class QueryGatewayTest {

    private final UserRepository repository = new UserRepository();
    private final QueryGateway queryGateway = new QueryGateway(repository);

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

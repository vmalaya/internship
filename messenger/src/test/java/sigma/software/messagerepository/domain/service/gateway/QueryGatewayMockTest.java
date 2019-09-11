package sigma.software.messagerepository.domain.service.gateway;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sigma.software.messagerepository.domain.User;
import sigma.software.messagerepository.domain.command.CreateUserCommand;
import sigma.software.messagerepository.domain.query.*;
import sigma.software.messagerepository.domain.query.api.QueryResponse;
import sigma.software.messagerepository.domain.service.gateway.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QueryGatewayMockTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private QueryGateway queryGateway;

    @Test
    void query_mocked_user_should_behave_correctly() {
        // given:
        String expectedUsername = "valentyna.mala";
        // and
        User user = mock(User.class);
        when(user.getUsername()).thenReturn(expectedUsername);

        // when:
        when(repository.load(any(UUID.class))).thenReturn(user);
        // and
        QueryResponse response = queryGateway.apply(new UserRequest(UUID.randomUUID()));

        // then:
        assertThat(response).isNotNull();
        assertThat(response).isInstanceOf(UserResponse.class);

        UserResponse userResponse = UserResponse.class.cast(response);
        assertThat(userResponse.getUsername()).isEqualTo(expectedUsername);
    }

    @Test
    void query_user_should_behave_correctly() {
        // given:
        User user = new User();
        UUID id = UUID.randomUUID();
        user.handle(new CreateUserCommand(id, "valentyna.mala"));
        //
        when(repository.load(any(UUID.class))).thenReturn(user);

        // when:
        QueryResponse response = queryGateway.apply(new UserRequest(id));

        // then:
        assertThat(response).isNotNull();
        assertThat(response).isInstanceOf(UserResponse.class);

        UserResponse userResponse = UserResponse.class.cast(response);
        assertThat(userResponse.getUsername()).isEqualTo("valentyna.mala");
    }

    @Test
    void query_user_friend_requests_should_behave_correctly() {
        // given:
        List<UUID> expectedFriendRequestsList = Collections.singletonList(UUID.randomUUID());
        // and
        User user = mock(User.class);
        when(user.getFriendRequest()).thenReturn(expectedFriendRequestsList);

        // when:
        when(repository.load(any(UUID.class))).thenReturn(user);
        // and
        QueryResponse response = queryGateway.apply(new UserFriendRequestsRequest(UUID.randomUUID()));

        // then:
        assertThat(response).isNotNull();
        assertThat(response).isInstanceOf(QueryResponse.class);
        // and
        UserFriendRequestsResponse requestsResponse = UserFriendRequestsResponse.class.cast(response);
        assertThat(requestsResponse.getAllRequests()).isEqualTo(expectedFriendRequestsList.toString()
                                                                                          .replace("[", "")
                                                                                          .replace("]", ""));
    }

    @Test
    void query_user_friends_should_behave_correctly() {
        // given:
        List<UUID> expectedFriendsList = Collections.singletonList(UUID.randomUUID());
        // and
        User user = mock(User.class);
        when(user.getFriends()).thenReturn(expectedFriendsList);

        // when:
        when(repository.load(any(UUID.class))).thenReturn(user);
        // and
        QueryResponse response = queryGateway.apply(new UserFriendsRequest(UUID.randomUUID()));

        // then:
        assertThat(response).isNotNull();
        assertThat(response).isInstanceOf(QueryResponse.class);

        UserFriendsResponse friendsResponse = UserFriendsResponse.class.cast(response);
        assertThat(friendsResponse.getFriends()).isEqualTo(expectedFriendsList.toString()
                                                                              .replace("[", "")
                                                                              .replace("]", ""));
    }
}

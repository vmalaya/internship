package sigma.software.messagerepository.domain.service.gateway.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sigma.software.messagerepository.domain.User;
import sigma.software.messagerepository.domain.event.UserCreatedEvent;
import sigma.software.messagerepository.domain.event.api.DomainEvent;
import sigma.software.messagerepository.domain.service.gateway.repository.eventstore.EventStore;

import java.util.List;
import java.util.UUID;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryMockTest {

    @Mock
    private EventStore eventStore;

    @InjectMocks
    private UserRepository userRepository;

    /*
    @Test
    void test_mock_annotation() {
        assertThat(eventStore).isNotNull();
    }
    */

    @Test
    void should_not_behave_correctly_on_null_user_save_operation() {
        // when
        assertThatThrownBy(() -> userRepository.save(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("user may not be null");
        // then
        verify(eventStore, times(0)).snapshot(null);
    }

    @Test
    void should_behave_correctly_on_user_save_operation() {
        // given
        User user = mock(User.class);
        // when
        userRepository.save(user);
        // then
        verify(eventStore, times(1)).snapshot(user);
    }

    @Test
    void mock_should_behave_correctly_on_user_load_operation() {
        // given
        String expectedUsername = "test";
        // and
        List<DomainEvent> history = singletonList(new UserCreatedEvent(UUID.randomUUID(), expectedUsername));
        // and
        given(eventStore.read(any(UUID.class))).willReturn(history);
        // when
        User user = userRepository.load(UUID.randomUUID());
        // then
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isNotNull()
                                      .isEqualTo(expectedUsername);
    }

    @Test
    void test_inject_mock_annotation() {
        assertThat(userRepository).isNotNull();
    }
}

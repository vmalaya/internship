package sigma.software.messagerepository.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import sigma.software.messagerepository.domain.command.CreateUserCommand;
import sigma.software.messagerepository.domain.command.SendFriendRequestCommand;

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
        assertThat(user.getUsername()).isEqualTo("valentyna.mala");
        // and:
        assertThat(user.getDomainEvents()).isEmpty();
    }

    @Test
    void should_load_user() {

        // given:
        User user = new User();
        // and
        UUID id = UUID.randomUUID();
        // and
        user.handle(new CreateUserCommand(id, "valentyna.mala"));

        // when:
        userRepository.save(user);

        // then:
        User loaded = userRepository.load(id);
        // and:
        assertThat(loaded.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    void should_load_user_from_snapshot() {

        // given:
        User user = new User();
        user.handle(new CreateUserCommand(UUID.randomUUID(), "valentyna.mala"));
        userRepository.save(user);
        // and:
        User snapshot = userRepository.load(user.getAggregateId());
        assertThat(snapshot).isEqualTo(user);

        // when:
        UUID friendId = UUID.randomUUID();
        SendFriendRequestCommand intention = new SendFriendRequestCommand(user.getAggregateId(), friendId);
        user.handle(intention);
        userRepository.save(user);
        assertThat(user.getFriendRequest()).hasSize(1);

        // then:
        User latest = userRepository.load(snapshot, user.getAggregateId());
        // and:
        assertThat(latest == snapshot).isTrue();
        // and:
        assertThat(snapshot.getFriendRequest()).hasSize(1)
                                               .containsExactly(friendId);
    }

    // @Test
    // void should_receive_latest_messages() throws InterruptedException {
    //
    //     // given:
    //     User user = new User();
    //     user.handle(new CreateUserCommand(UUID.randomUUID(), "valentyna.mala"));
    //     // and
    //     UUID friendId = UUID.randomUUID();
    //     SendFriendRequestCommand intention = new SendFriendRequestCommand(user.getAggregateId(), friendId);
    //     user.handle(intention);
    //     // and
    //     user.handle(new AcceptFriendRequestCommand(user.getAggregateId(), friendId));
    //     // and
    //     user.handle(new ReceiveMessageCommand(user.getAggregateId(), friendId, "Hi! It's first."));
    //     user.handle(new SendMessageCommand(user.getAggregateId(), friendId, "Hello, it's second."));
    //     Thread.sleep(100);
    //     user.handle(new ReceiveMessageCommand(user.getAggregateId(), friendId, "From friend, third one."));
    //     userRepository.save(user);
    //
    //     // when:
    //     Collection<Message> allMessages = userRepository.getAllMessagesInDescOrder(user.getAggregateId());
    //
    //     // then:
    //     Iterator<Message> iterator = allMessages.iterator();
    //     assertThat(allMessages).hasSize(3);
    //     assertThat(iterator.next().getAt()).isAfter(iterator.next().getAt()).isAfter(iterator.next().getAt());
    // }
    //
    // @Test
    // void should_receive_number_of_latest_messages() throws InterruptedException {
    //
    //     // given:
    //     User user = new User();
    //     user.handle(new CreateUserCommand(UUID.randomUUID(), "valentyna.mala"));
    //     // and
    //     UUID friendId = UUID.randomUUID();
    //     SendFriendRequestCommand intention = new SendFriendRequestCommand(user.getAggregateId(), friendId);
    //     user.handle(intention);
    //     // and
    //     user.handle(new AcceptFriendRequestCommand(user.getAggregateId(), friendId));
    //     // and
    //     user.handle(new ReceiveMessageCommand(user.getAggregateId(), friendId, "first."));
    //     user.handle(new SendMessageCommand(user.getAggregateId(), friendId, "second."));
    //     Thread.sleep(100);
    //     user.handle(new ReceiveMessageCommand(user.getAggregateId(), friendId, "third."));
    //     Thread.sleep(100);
    //     user.handle(new ReceiveMessageCommand(user.getAggregateId(), friendId, "fourth."));
    //     userRepository.save(user);
    //
    //     // when:
    //     Collection<Message> lastMessages = userRepository.getLastNumberOfMessagesInDescOrder(user.getAggregateId(),
    //                                                                                                             3);
    //     // then:
    //     Iterator<Message> iterator = lastMessages.iterator();
    //     assertThat(iterator.next().getAt()).isAfter(iterator.next().getAt()).isAfter(iterator.next().getAt());
    // }
}

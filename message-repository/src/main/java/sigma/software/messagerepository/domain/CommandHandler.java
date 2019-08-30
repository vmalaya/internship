package sigma.software.messagerepository.domain;

import io.vavr.API;
import sigma.software.messagerepository.domain.command.*;

import java.util.UUID;
import java.util.function.Consumer;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.Predicates.instanceOf;

public class CommandHandler implements Consumer<Command> {

    private final UserRepository repository;

    public CommandHandler(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void accept(Command command) {
        API.Match(command).of(
                Case($(instanceOf(CreateUserCommand.class)), this::handle),
                Case($(instanceOf(SendFriendRequestCommand.class)), this::handle),
                Case($(instanceOf(AcceptFriendRequestCommand.class)), this::handle),
                Case($(instanceOf(DeclineFriendRequestCommand.class)), this::handle),
                Case($(instanceOf(SendMessageCommand.class)), this::handle),
                Case($(instanceOf(ReceiveMessageCommand.class)), this::handle),
                Case($(), this::handleUnexpected)
        );
    }

    public Void handle(CreateUserCommand command) {
        return update(command.getId(), user -> user.handle(command));
    }

    public Void handle(SendFriendRequestCommand command) {
        return update(command.getAggregateId(), user -> user.handle(command));
    }

    public Void handle(AcceptFriendRequestCommand command) {
        return update(command.getAggregateId(), user -> user.handle(command));
    }

    public Void handle(DeclineFriendRequestCommand command) {
        return update(command.getAggregateId(), user -> user.handle(command));
    }

    public Void handle(SendMessageCommand command) {
        return update(command.getAggregateId(), user -> user.handle(command));return update(); // TODO
    }

    public Void handle(ReceiveMessageCommand command) {
        return update(command.getAggregateId(), user -> user.handle(command));
    }

    public <Cmd extends Command> Void handleUnexpected(Cmd cmd) {
        System.out.println(String.format("accepting unknown command: %s", cmd));
        return null;
    }

    private Void update(UUID aggregateId, Consumer<User> action) {
        User user = repository.load(aggregateId);
        action.accept(user);
        repository.save(user);
        return null;
    }
}

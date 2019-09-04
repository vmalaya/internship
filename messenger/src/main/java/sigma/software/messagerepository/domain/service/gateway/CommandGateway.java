package sigma.software.messagerepository.domain.service.gateway;

import io.vavr.API;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sigma.software.messagerepository.domain.User;
import sigma.software.messagerepository.domain.command.*;
import sigma.software.messagerepository.domain.command.api.Command;
import sigma.software.messagerepository.domain.service.gateway.repository.UserRepository;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.Predicates.instanceOf;

public class CommandGateway implements Function<Command, String> {

    private static final Logger log = LogManager.getLogger();

    private final UserRepository repository;

    public CommandGateway(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public String apply(Command command) {
        return API.Match(command).of(
                Case($(instanceOf(CreateUserCommand.class)), this::handle),
                Case($(instanceOf(SendFriendRequestCommand.class)), this::handle),
                Case($(instanceOf(ReceiveFriendRequestCommand.class)), this::handle),
                Case($(instanceOf(AcceptFriendRequestCommand.class)), this::handle),
                Case($(instanceOf(DeclineFriendRequestCommand.class)), this::handle),
                Case($(instanceOf(SendMessageCommand.class)), this::handle),
                Case($(instanceOf(ReceiveMessageCommand.class)), this::handle),
                Case($(), this::handleUnexpected)
        );
    }

    private String handle(CreateUserCommand command) {
        return update(command.getId(), user -> user.handle(command));
    }

    private String handle(SendFriendRequestCommand command) {
        return update(command.getAggregateId(), user -> user.handle(command));
    }

    private String handle(ReceiveFriendRequestCommand command) {
        return update(command.getAggregateId(), user -> user.handle(command));
    }

    private String handle(AcceptFriendRequestCommand command) {
        return update(command.getAggregateId(), user -> user.handle(command));
    }

    private String handle(DeclineFriendRequestCommand command) {
        return update(command.getAggregateId(), user -> user.handle(command));
    }

    private String handle(SendMessageCommand command) {
        return update(command.getAggregateId(), user -> user.handle(command));
    }

    private String handle(ReceiveMessageCommand command) {
        return update(command.getAggregateId(), user -> user.handle(command));
    }

    private String handleUnexpected(Command command) {
        log.warn(String.format("accepting unknown command: %s", command));
        return "Done.";
    }

    private String update(UUID aggregateId, Consumer<User> action) {
        User user = repository.load(aggregateId);
        action.accept(user);
        repository.save(user);
        return "Done.";
    }

}

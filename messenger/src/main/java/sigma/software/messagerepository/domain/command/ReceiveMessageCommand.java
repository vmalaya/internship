package sigma.software.messagerepository.domain.command;

import sigma.software.messagerepository.domain.command.api.Command;

import java.util.UUID;

public class ReceiveMessageCommand implements Command {

    private final UUID aggregateId;
    private final UUID sender;
    private final String message;

    public ReceiveMessageCommand(UUID aggregateId, UUID sender, String message) {
        this.aggregateId = aggregateId;
        this.sender = sender;
        this.message = message;
    }

    public UUID getAggregateId() {
        return aggregateId;
    }

    public UUID getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }
}

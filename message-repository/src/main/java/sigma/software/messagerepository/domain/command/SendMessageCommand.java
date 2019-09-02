package sigma.software.messagerepository.domain.command;

import sigma.software.messagerepository.domain.command.api.Command;

import java.util.UUID;

/**
 * Example of ValueObject (DDD term)
 */
public class SendMessageCommand implements Command {

    private final UUID aggregateId;
    private final UUID recipient;
    private final String message;

    public SendMessageCommand(UUID aggregateId, UUID recipient, String message) {
        this.aggregateId = aggregateId;
        this.recipient = recipient;
        this.message = message;
    }

    public UUID getAggregateId() {
        return aggregateId;
    }

    public UUID getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }
}

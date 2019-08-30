package sigma.software.messagerepository.domain.command;

import java.util.UUID;

/**
 * Example of ValueObject (DDD term)
 */
public class SendMessageCommand {

    private final UUID recipient;
    private final String message;

    public SendMessageCommand(UUID recipient, String message) {
        this.recipient = recipient;
        this.message = message;
    }

    public UUID getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }
}

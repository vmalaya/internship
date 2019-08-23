package sigma.software.messagerepository.command;

import java.util.UUID;

public class ReceiveMessageCommand {

    private final UUID sender;
    private final UUID recipient;
    private final String message;

    public ReceiveMessageCommand(UUID sender, UUID recipient, String message) {
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
    }

    public UUID getSender() {
        return sender;
    }

    public UUID getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }
}

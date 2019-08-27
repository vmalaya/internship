package sigma.software.messagerepository.command;

import java.util.UUID;

public class ReceiveMessageCommand {

    private final UUID sender;
    private final String message;

    public ReceiveMessageCommand(UUID sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public UUID getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }
}

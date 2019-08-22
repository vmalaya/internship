package sigma.software.messagerepository.command;

import java.util.UUID;

public class SaveMessageCommand {

    private final UUID id;
    private final String message;
    private final String receiverUsername;

    public SaveMessageCommand(UUID id, String message, String receiverUsername) {
        this.id = id;
        this.message = message;
        this.receiverUsername = receiverUsername;
    }

    public UUID getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }
}

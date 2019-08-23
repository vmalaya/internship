package sigma.software.messagerepository.command;

import java.util.UUID;

public class CreateMessageCommand {

    private final UUID id;
    private final String text;

    public CreateMessageCommand(UUID id, String text) {
        this.id = id;
        this.text = text;
    }

    public UUID getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}

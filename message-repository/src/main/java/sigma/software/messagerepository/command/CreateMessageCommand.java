package sigma.software.messagerepository.command;

import java.util.UUID;

public class CreateMessageCommand {

    private final UUID id;
    private final UUID idFriend;
    private final String text;

    public CreateMessageCommand(UUID id, UUID idFriend, String text) {
        this.id = id;
        this.idFriend = idFriend;
        this.text = text;
    }

    public UUID getId() {
        return id;
    }

    public UUID getIdFriend() {
        return idFriend;
    }

    public String getText() {
        return text;
    }
}

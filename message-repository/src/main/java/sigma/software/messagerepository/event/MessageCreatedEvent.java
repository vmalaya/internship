package sigma.software.messagerepository.event;

import java.util.UUID;

public class MessageCreatedEvent {
    private final UUID id;
    private final UUID idFriend;
    private final String text;

    public MessageCreatedEvent(UUID id, UUID idFriend, String text) {
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

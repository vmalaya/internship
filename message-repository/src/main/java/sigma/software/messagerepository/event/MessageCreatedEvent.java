package sigma.software.messagerepository.event;

import java.util.UUID;

public class MessageCreatedEvent {
    private final UUID id;
    private final String text;

    public MessageCreatedEvent(UUID id, String text) {
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

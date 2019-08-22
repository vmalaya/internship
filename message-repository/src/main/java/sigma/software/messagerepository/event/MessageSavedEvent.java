package sigma.software.messagerepository.event;

import java.util.Objects;
import java.util.UUID;

public class MessageSavedEvent implements DomainEvent {

    private UUID id;
    private String message;
    private String receiverUsername;

    public MessageSavedEvent(UUID id, String message, String receiverUsername) {
        this.id = id;
        this.message = message;
        this.receiverUsername = receiverUsername;
    }

    public MessageSavedEvent() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageSavedEvent)) return false;
        MessageSavedEvent that = (MessageSavedEvent) o;
        return id.equals(that.id) &&
                message.equals(that.message) &&
                receiverUsername.equals(that.receiverUsername);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message, receiverUsername);
    }
}

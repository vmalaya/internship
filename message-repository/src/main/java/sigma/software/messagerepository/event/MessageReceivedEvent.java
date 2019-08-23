package sigma.software.messagerepository.event;

import sigma.software.messagerepository.Message;

import java.time.ZonedDateTime;
import java.util.UUID;

public class MessageReceivedEvent implements DomainEvent {

    private final UUID sender;
    private final UUID recipient;
    private final String message;
    private final ZonedDateTime at;

    public MessageReceivedEvent(UUID sender, UUID recipient, String message, ZonedDateTime at) {
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
        this.at = at;
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

    public ZonedDateTime getAt() {
        return at;
    }
}

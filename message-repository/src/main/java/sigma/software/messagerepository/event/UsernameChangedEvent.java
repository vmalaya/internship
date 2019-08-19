package sigma.software.messagerepository.event;

import java.util.UUID;

public class UsernameChangedEvent implements DomainEvent {

    private final UUID id;
    private final String from;
    private final String to;

    public UsernameChangedEvent(UUID id, String from, String to) {
        this.id = id;
        this.from = from;
        this.to = to;
    }

    public UUID getId() {
        return id;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}

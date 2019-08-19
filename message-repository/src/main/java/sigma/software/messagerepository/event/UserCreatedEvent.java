package sigma.software.messagerepository.event;

import java.util.Objects;
import java.util.UUID;

public class UserCreatedEvent implements DomainEvent {

    private final UUID id;
    private final String username;

    public UserCreatedEvent(UUID id, String username) {
        this.id = id;
        this.username = username;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}

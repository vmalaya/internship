package sigma.software.messagerepository.domain.command;

import java.util.UUID;

public class AcceptFriendRequestCommand {

    private final UUID id;

    public AcceptFriendRequestCommand(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}

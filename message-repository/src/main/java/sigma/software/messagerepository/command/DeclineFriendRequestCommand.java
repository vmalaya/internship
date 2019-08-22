package sigma.software.messagerepository.command;

import java.util.UUID;

public class DeclineFriendRequestCommand {

    private final UUID userId;

    public DeclineFriendRequestCommand(UUID userId) {
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }
}

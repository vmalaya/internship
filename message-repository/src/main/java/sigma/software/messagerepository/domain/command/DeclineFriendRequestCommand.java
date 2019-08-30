package sigma.software.messagerepository.domain.command;

import java.util.UUID;

public class DeclineFriendRequestCommand implements Command {

    private final UUID userId;

    public DeclineFriendRequestCommand(UUID userId) {
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }
}

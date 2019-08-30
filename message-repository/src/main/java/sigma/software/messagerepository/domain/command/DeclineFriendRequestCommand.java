package sigma.software.messagerepository.domain.command;

import java.util.UUID;

public class DeclineFriendRequestCommand implements Command {

    private final UUID aggregateId;
    private final UUID userId;

    public DeclineFriendRequestCommand(UUID aggregateId, UUID userId) {
        this.aggregateId = aggregateId;
        this.userId = userId;
    }

    public UUID getAggregateId() {
        return aggregateId;
    }

    public UUID getUserId() {
        return userId;
    }
}

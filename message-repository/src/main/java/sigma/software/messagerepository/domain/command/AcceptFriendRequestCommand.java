package sigma.software.messagerepository.domain.command;

import java.util.UUID;

public class AcceptFriendRequestCommand implements Command {

    private final UUID aggregateId;
    private final UUID id;

    public AcceptFriendRequestCommand(UUID aggregateId, UUID id) {
        this.aggregateId = aggregateId;
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public UUID getAggregateId() {
        return aggregateId;
    }
}

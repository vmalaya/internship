package sigma.software.messagerepository.domain.command;

import sigma.software.messagerepository.domain.command.api.Command;

import java.util.UUID;

public class ReceiveFriendRequestCommand implements Command {

    private final UUID aggregateId;
    private final UUID senderId;

    public ReceiveFriendRequestCommand(UUID aggregateId, UUID senderId) {
        this.aggregateId = aggregateId;
        this.senderId = senderId;
    }

    public UUID getAggregateId() {
        return aggregateId;
    }

    public UUID getSenderId() {
        return senderId;
    }
}

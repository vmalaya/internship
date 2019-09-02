package sigma.software.messagerepository.domain.command;

import sigma.software.messagerepository.domain.command.api.Command;

import java.util.UUID;

public class SendFriendRequestCommand implements Command {

    private final UUID aggregateId;
    private final UUID friendId;

    public SendFriendRequestCommand(UUID aggregateId, UUID friendId) {
        this.aggregateId = aggregateId;
        this.friendId = friendId;
    }

    public UUID getFriendId() {
        return friendId;
    }

    public UUID getAggregateId() {
        return aggregateId;
    }
}

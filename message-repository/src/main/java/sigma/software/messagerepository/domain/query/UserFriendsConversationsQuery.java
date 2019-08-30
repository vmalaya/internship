package sigma.software.messagerepository.domain.query;

import java.util.UUID;

public class UserFriendsConversationsQuery {

    private final UUID aggregateId;
    private final UUID friendId;

    public UserFriendsConversationsQuery(UUID aggregateId, UUID friendId) {
        this.aggregateId = aggregateId;
        this.friendId = friendId;
    }

    public UUID getAggregateId() {
        return aggregateId;
    }

    public UUID getFriendId() {
        return friendId;
    }
}

package sigma.software.messagerepository.domain.query;

import java.util.UUID;

public class UserFriendsConversationsRequestUser implements UserQueryRequest {

    private final UUID aggregateId;
    private final UUID friendId;

    public UserFriendsConversationsRequestUser(UUID aggregateId, UUID friendId) {
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

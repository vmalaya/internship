package sigma.software.messagerepository.domain.query;

import sigma.software.messagerepository.domain.query.api.UserQueryRequest;

import java.util.UUID;

public class UserFriendsConversationsRequest implements UserQueryRequest {

    private final UUID aggregateId;
    private final UUID friendId;

    public UserFriendsConversationsRequest(UUID aggregateId, UUID friendId) {
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

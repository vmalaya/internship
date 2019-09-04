package sigma.software.messagerepository.domain.query;

import sigma.software.messagerepository.domain.query.api.UserQueryRequest;

import java.util.UUID;

public class UserFriendsRequest implements UserQueryRequest {

    private final UUID aggregateId;

    public UserFriendsRequest(UUID aggregateId) {
        this.aggregateId = aggregateId;
    }

    @Override
    public UUID getAggregateId() {
        return aggregateId;
    }
}

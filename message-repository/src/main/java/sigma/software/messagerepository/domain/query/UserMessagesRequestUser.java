package sigma.software.messagerepository.domain.query;

import sigma.software.messagerepository.domain.query.api.UserQueryRequest;

import java.util.UUID;

public class UserMessagesRequestUser implements UserQueryRequest {

    private final UUID aggregateId;

    public UserMessagesRequestUser(UUID aggregateId) {
        this.aggregateId = aggregateId;
    }

    public UUID getAggregateId() {
        return aggregateId;
    }
}

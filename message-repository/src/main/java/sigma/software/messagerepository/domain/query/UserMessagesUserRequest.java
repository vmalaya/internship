package sigma.software.messagerepository.domain.query;

import sigma.software.messagerepository.domain.query.api.UserQueryRequest;

import java.util.UUID;

public class UserMessagesUserRequest implements UserQueryRequest {

    private final UUID aggregateId;

    public UserMessagesUserRequest(UUID aggregateId) {
        this.aggregateId = aggregateId;
    }

    public UUID getAggregateId() {
        return aggregateId;
    }
}

package sigma.software.messagerepository.domain.query;

import sigma.software.messagerepository.domain.query.api.UserQueryRequest;

import java.util.UUID;

public class UserRequest implements UserQueryRequest {

    private final UUID aggregateId;

    public UserRequest(UUID aggregateId) {
        this.aggregateId = aggregateId;
    }

    @Override
    public UUID getAggregateId() {
        return aggregateId;
    }
}

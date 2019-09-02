package sigma.software.messagerepository.domain.query;

import java.util.UUID;

public class UserLimitedMessagesRequestUser {

    private final UUID aggregateId;
    private final long limit;

    public UserLimitedMessagesRequestUser(UUID aggregateId, long limit) {
        this.aggregateId = aggregateId;
        this.limit = limit;
    }

    public UUID getAggregateId() {
        return aggregateId;
    }

    public long getLimit() {
        return limit;
    }
}

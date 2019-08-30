package sigma.software.messagerepository.domain.query;

import java.util.UUID;

public class UserMessagesQuery {

    private final UUID aggregateId;

    public UserMessagesQuery(UUID aggregateId) {
        this.aggregateId = aggregateId;
    }

    public UUID getAggregateId() {
        return aggregateId;
    }
}

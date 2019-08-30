package sigma.software.messagerepository.domain.query;

import java.util.UUID;

public interface UserQueryRequest extends QueryRequest {
    UUID getAggregateId();
}

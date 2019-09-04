package sigma.software.messagerepository.domain.query.api;

import java.util.UUID;

public interface UserQueryRequest extends QueryRequest {
    UUID getAggregateId();
}

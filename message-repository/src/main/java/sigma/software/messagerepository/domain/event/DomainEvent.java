package sigma.software.messagerepository.domain.event;

import java.util.UUID;

public interface DomainEvent {
    UUID getAggregateId();
}

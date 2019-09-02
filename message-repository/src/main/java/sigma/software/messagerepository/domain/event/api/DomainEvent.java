package sigma.software.messagerepository.domain.event.api;

import java.util.UUID;

public interface DomainEvent {
    UUID getAggregateId();
}

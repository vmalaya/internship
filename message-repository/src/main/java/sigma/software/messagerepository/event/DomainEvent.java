package sigma.software.messagerepository.event;

import java.util.UUID;

public interface DomainEvent {
    UUID getAggregateId();
}

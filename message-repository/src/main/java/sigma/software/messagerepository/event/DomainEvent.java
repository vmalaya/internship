package sigma.software.messagerepository.event;

import java.io.Serializable;
import java.util.UUID;

public interface DomainEvent extends Serializable {
    UUID getAggregateId();
}

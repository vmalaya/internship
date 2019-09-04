package sigma.software.messagerepository.domain.service.gateway.repository;

import sigma.software.messagerepository.domain.User;
import sigma.software.messagerepository.domain.event.api.DomainEvent;
import sigma.software.messagerepository.domain.service.gateway.repository.eventstore.EventStore;

import java.util.Objects;
import java.util.UUID;

public class UserRepository {

    private final EventStore eventStore;

    public UserRepository(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    public void save(User user) {
        eventStore.append(user.getDomainEvents().toArray(new DomainEvent[0]));
        user.getDomainEvents().clear();
    }

    public User load(UUID id) {
        return load(new User(), id);
    }

    public User load(User snapshot, UUID id) {
        Objects.requireNonNull(id, "id may not be null");
        return User.recreate(snapshot, eventStore.read(id));
    }
}

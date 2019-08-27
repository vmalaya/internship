package sigma.software.messagerepository;

import sigma.software.messagerepository.event.DomainEvent;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserRepository {

    private final Map<UUID, Collection<DomainEvent>> eventStore = new ConcurrentHashMap<>();

    public void save(User user) {
        UUID aggregateId = user.getAggregateId();
        Collection<DomainEvent> past = eventStore.getOrDefault(aggregateId, new CopyOnWriteArrayList<>());
        Collection<DomainEvent> present = new CopyOnWriteArrayList<>(user.getDomainEvents());
        user.flushEvents();
        List<DomainEvent> history = Stream.concat(past.stream(), present.stream())
                                          .collect(Collectors.toList());
        eventStore.put(aggregateId, new CopyOnWriteArrayList<>(history));
    }

    public User load(UUID id) {
        return load(new User(), id);
    }

    public User load(User snapshot, UUID id) {
        Objects.requireNonNull(id, "id may not be null");
        return eventStore.containsKey(id)
                ? User.recreate(snapshot, eventStore.get(id))
                : snapshot;
    }
}

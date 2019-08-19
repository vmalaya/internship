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
        Collection<DomainEvent> tail = eventStore.getOrDefault(user.getId(), new CopyOnWriteArrayList<>());
        Collection<DomainEvent> head = user.getDomainEvents();
        List<DomainEvent> userEvents = Stream.concat(tail.stream(), head.stream())
                                             .collect(Collectors.toList());
        eventStore.put(user.getId(), new CopyOnWriteArrayList<>(userEvents));
        user.flushEvents();
    }

    public User find(UUID id) {
        User snapshot = new User();
        return eventStore.containsKey(id)
                ? User.recreate(snapshot, eventStore.get(id))
                : snapshot;
    }
}

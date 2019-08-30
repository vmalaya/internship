package sigma.software.messagerepository.domain;

import sigma.software.messagerepository.domain.event.DomainEvent;
import sigma.software.messagerepository.domain.query.DescendingComparator;

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

    public Collection<Message> getAllMessages(UUID userId) {
        User user = load(userId);
        return user.getMessages();
    }

    public Collection<Message> getAllMessagesInDescOrder(UUID userId) {
        User user = load(userId);
        Collection<Message> messages = user.getMessages();
        List<Message> list = new CopyOnWriteArrayList<>(messages);
        Collections.sort(list, new DescendingComparator());
        return list;
    }

    public Collection<Message> getLastNumberOfMessagesInDescOrder(UUID userId, int limit) {
        User user = load(userId);
        List<Message> list = new CopyOnWriteArrayList<>(user.getMessages());
        ListIterator<Message> iterator = list.listIterator(list.size() - limit);
        List<Message> sorted = new CopyOnWriteArrayList<>();
        while (iterator.hasNext()) {
            sorted.add(iterator.next());
        }
        Collections.sort(sorted, new DescendingComparator());
        return sorted;
    }
}

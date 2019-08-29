package sigma.software.messagerepository;

import sigma.software.messagerepository.db.EventStore;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class UserRepository {
    private final EventStore eventStore = EventStore.create();

    public void save(User user) {
        eventStore.appendUserEvent(user);
    }

    public User load(UUID id) {
        return load(new User(), id);
    }

    public User load(User snapshot, UUID id) {
        return eventStore.load(snapshot, id);
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

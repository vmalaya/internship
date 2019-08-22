package sigma.software.messagerepository;

import sigma.software.messagerepository.event.DomainEvent;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class MessageRepository {

    private final Map<UUID, Collection<DomainEvent>> messageStore = new ConcurrentHashMap<>();

    public void save(User user, DomainEvent event) {
        Collection<DomainEvent> messagesList = messageStore.getOrDefault(user.getId(), new CopyOnWriteArrayList<>());
        messagesList.add(event);
        if (messageStore.containsKey(user.getId())) {
            messageStore.replace(user.getId(), messagesList);
        } else {
            messageStore.put(user.getId(), messagesList);
        }
    }

    public boolean find(User user, UUID s) {
        if (messageStore.containsKey(user.getId())) {
            Collection<DomainEvent> collection = messageStore.get(user.getId());
            for (DomainEvent d : collection) {
                // TODO: // FIXME: if (d.getId().equals(s)) return true;
            }
        }
        return false;
    }
}

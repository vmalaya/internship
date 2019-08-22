package sigma.software.messagerepository;

import sigma.software.messagerepository.event.DomainEvent;
import sigma.software.messagerepository.event.MessageSavedEvent;

import java.util.*;
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
                if (d.getId().equals(s)) return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return messageStore.isEmpty();
    }

    public Collection<String> getAllMessages(UUID id) {
        Collection<DomainEvent> collection = messageStore.get(id);
        Iterator<DomainEvent> iterator = collection.iterator();
        List<String> list = new ArrayList<>(collection.size() - 1);

        return list;
    }
}

package sigma.software.messagerepository.event;

public class LastMessagesInDescOrderRevealedEvent implements DomainEvent {

    private final int limit;

    public LastMessagesInDescOrderRevealedEvent(int limit) {
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }
}

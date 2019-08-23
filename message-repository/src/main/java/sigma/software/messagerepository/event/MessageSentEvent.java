package sigma.software.messagerepository.event;

import sigma.software.messagerepository.Message;

public class MessageSentEvent implements DomainEvent {

    private final Message message;

    public MessageSentEvent(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }
}

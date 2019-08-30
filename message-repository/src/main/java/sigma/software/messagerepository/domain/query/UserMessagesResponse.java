package sigma.software.messagerepository.domain.query;

import sigma.software.messagerepository.domain.Message;

import java.util.Collection;

public class UserMessagesResponse implements QueryResponse {

    private final Collection<Message> allUserMessages;

    public UserMessagesResponse(Collection<Message> allUserMessages) {
        this.allUserMessages = allUserMessages;
    }

    public Collection<Message> getAllUserMessages() {
        return allUserMessages;
    }
}

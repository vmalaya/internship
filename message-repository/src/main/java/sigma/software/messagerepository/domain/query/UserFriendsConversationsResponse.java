package sigma.software.messagerepository.domain.query;

import sigma.software.messagerepository.domain.Message;

import java.util.Collection;

public class UserFriendsConversationsResponse implements QueryResponse {

    private final Collection<Message> messages;

    public UserFriendsConversationsResponse(Collection<Message> messages) {
        this.messages = messages;
    }

    public Collection<Message> getMessages() {
        return messages;
    }
}

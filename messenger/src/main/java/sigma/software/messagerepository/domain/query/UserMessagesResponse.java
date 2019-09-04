package sigma.software.messagerepository.domain.query;

import sigma.software.messagerepository.domain.Message;
import sigma.software.messagerepository.domain.query.api.QueryResponse;

import java.util.Collection;

import static java.util.stream.Collectors.joining;

public class UserMessagesResponse implements QueryResponse {

    private final String allUserMessages;

    public UserMessagesResponse(Collection<Message> messages) {
        this.allUserMessages = messages.stream()
                                       .map(Message::toString)
                                       .collect(joining("\n"));
    }

    public String getAllUserMessages() {
        return allUserMessages;
    }
}

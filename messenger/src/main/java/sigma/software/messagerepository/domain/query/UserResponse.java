package sigma.software.messagerepository.domain.query;

import sigma.software.messagerepository.domain.Message;
import sigma.software.messagerepository.domain.query.api.QueryResponse;

import java.util.Collection;
import java.util.UUID;

public class UserResponse implements QueryResponse {

    private final UUID aggregateId;
    private final String username;
    private final Collection<UUID> friendRequest;
    private final Collection<UUID> friends;
    private final Collection<Message> messages;

    public UserResponse(UUID aggregateId, String username, Collection<UUID> friendRequest, Collection<UUID> friends, Collection<Message> messages) {
        this.aggregateId = aggregateId;
        this.username = username;
        this.friendRequest = friendRequest;
        this.friends = friends;
        this.messages = messages;
    }

    public UUID getAggregateId() {
        return aggregateId;
    }

    public String getUsername() {
        return username;
    }

    public Collection<UUID> getFriendRequest() {
        return friendRequest;
    }

    public Collection<UUID> getFriends() {
        return friends;
    }

    public Collection<Message> getMessages() {
        return messages;
    }
}

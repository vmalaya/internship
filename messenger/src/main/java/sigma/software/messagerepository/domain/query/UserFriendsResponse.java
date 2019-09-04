package sigma.software.messagerepository.domain.query;

import sigma.software.messagerepository.domain.query.api.QueryResponse;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserFriendsResponse implements QueryResponse {

    private final String friends;

    public UserFriendsResponse(Collection<UUID> friends) {
        this.friends = friends.stream()
                              .map(UUID::toString)
                              .collect(Collectors.joining(", "));
    }

    public String getFriends() {
        return friends;
    }
}

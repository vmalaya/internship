package sigma.software.messagerepository.domain.query;

import sigma.software.messagerepository.domain.query.api.QueryResponse;

public class UserFriendsResponse implements QueryResponse {

    private final String friends;

    public UserFriendsResponse(String friends) {
        this.friends = friends;
    }

    public String getFriends() {
        return friends;
    }

    @Override
    public String toString() {
        return "All friends: " + friends;
    }
}

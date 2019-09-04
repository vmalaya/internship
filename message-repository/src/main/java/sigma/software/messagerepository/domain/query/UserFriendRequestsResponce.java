package sigma.software.messagerepository.domain.query;

import sigma.software.messagerepository.domain.query.api.QueryResponse;

import java.util.Collection;
import java.util.UUID;

public class UserFriendRequestsResponce implements QueryResponse {

    private final String allRequests;

    public UserFriendRequestsResponce(String allRequests) {
        this.allRequests = allRequests;
    }

    public String getAllRequests() {
        return allRequests;
    }

    @Override
    public String toString() {
        return "All requests: " + allRequests;
    }
}

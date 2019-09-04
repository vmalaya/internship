package sigma.software.messagerepository.domain.query;

import sigma.software.messagerepository.domain.query.api.QueryResponse;

public class UserFriendRequestsResponse implements QueryResponse {

    private final String allRequests;

    public UserFriendRequestsResponse(String allRequests) {
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

package sigma.software.messagerepository.domain.query.api;

public interface QueryRequest {
    // User Specific Queries must implement UserQueryRequest interface instead.

    // This one is not using yet, but could be used to query something more out of scope
    // concrete user perspective and point of view, for instance, like in skype: search
    // available user by it's username query... We could use such query for analytics,
    // like for example: find how many users are available at the moment in our event-store...
}

package sigma.software.messagerepository.domain;

import io.vavr.API;
import sigma.software.messagerepository.domain.query.*;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.Predicates.instanceOf;

public class QueryHandler implements Function<UserQueryRequest, QueryResponse> {

    private final UserRepository repository;

    public QueryHandler(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public QueryResponse apply(UserQueryRequest userQueryRequest) {
        return API.Match(userQueryRequest).of(
                Case($(instanceOf(UserFriendsConversationsRequestUser.class)), this::handle),
                Case($(instanceOf(UserMessagesRequestUser.class)), this::handle)
        );
    }

    private UserFriendsConversationsResponse handle(UserFriendsConversationsRequestUser request) {
        Predicate<Message> conversationPredicate = message ->
                (message.getRecipient().equals(request.getAggregateId())
                        && message.getSender().equals(request.getFriendId()))
             || (message.getSender().equals(request.getAggregateId())
                        && message.getRecipient().equals(request.getFriendId()));
        Stream<Message> allMessageStream = getAllMessageStream(request.getAggregateId());
        Stream<Message> friendMessageStream = allMessageStream.filter(conversationPredicate);
        Collection<Message> result = collectInDescendOrder(friendMessageStream);
        return new UserFriendsConversationsResponse(result);
    }

    private UserMessagesResponse handle(UserMessagesRequestUser request) {
        Stream<Message> allMessageStream = getAllMessageStream(request.getAggregateId());
        Collection<Message> result = collectInDescendOrder(allMessageStream);
        return new UserMessagesResponse(result);
    }

    private Stream<Message> getAllMessageStream(UUID aggregateId) {
        User user = repository.load(aggregateId);
        // TODO: read about Stream API + Java 8 Default methods.
        return user.getMessages().stream();
    }

    private Collection<Message> collectInDescendOrder(Stream<Message> messageStream) {
        // TODO: Java 8 Comparator replacement.
        return messageStream.sorted(new DescendingComparator())
                            // TODO: Read about Java 8 Collectors (Stream API)
                            .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
    }
}
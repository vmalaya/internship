package sigma.software.messagerepository.domain;

import sigma.software.messagerepository.domain.query.DescendingComparator;
import sigma.software.messagerepository.domain.query.UserFriendsConversationsQuery;
import sigma.software.messagerepository.domain.query.UserMessagesQuery;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QueryHandler {

    private final UserRepository repository;

    public QueryHandler(UserRepository repository) {
        this.repository = repository;
    }

    public Collection<Message> handle(UserFriendsConversationsQuery query) {
        User user = repository.load(query.getAggregateId());
        // TODO: read about Stream API + Java 8 Default methods.
        Stream<Message> allMessages = user.getMessages().stream();
        Stream<Message> messageStream = allMessages.filter(
                message -> (message.getRecipient().equals(query.getAggregateId())
                                && message.getSender().equals(query.getFriendId()))
                        || (message.getSender().equals(query.getAggregateId())
                                && message.getRecipient().equals(query.getFriendId())));
        return collectInDescendOrder(messageStream);
    }

    public Collection<Message> handle(UserMessagesQuery query) {
        User user = repository.load(query.getAggregateId());
        // TODO: read about Stream API + Java 8 Default methods.
        Stream<Message> messageStream = user.getMessages().stream();
        return collectInDescendOrder(messageStream);
    }

    private Collection<Message> collectInDescendOrder(Stream<Message> messageStream) {
        return messageStream.sorted(new DescendingComparator()) // TODO: Java 8 Comparator replacement.
                            .collect(Collectors.toCollection(CopyOnWriteArrayList::new)); // TODO: Read about Java 8 Collectors (Stream API)
    }
}

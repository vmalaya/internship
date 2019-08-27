package sigma.software.messagerepository;

import io.vavr.API;
import sigma.software.messagerepository.command.*;
import sigma.software.messagerepository.event.*;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Function;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.Predicates.instanceOf;

/**
 * Aggregate.
 */
public class User implements Function<DomainEvent, User> {

    // state:
    private UUID aggregateId;
    private String username;
    private Collection<UUID> friendRequest = new CopyOnWriteArraySet<>();
    private Collection<UUID> friends = new CopyOnWriteArraySet<>();
    private Collection<Message> messages = new CopyOnWriteArrayList<>();

    public User() {
    }

    // event sourcing
    public static User recreate(User snapshot, Collection<DomainEvent> history) {
        return io.vavr.collection.List.ofAll(history)
                                      .foldLeft(snapshot, User::apply);
    }

    @Override
    public User apply(DomainEvent domainEvent) {
        return API.Match(domainEvent).of(
                Case($(instanceOf(UserCreatedEvent.class)), this::on),
                Case($(instanceOf(FriendRequestSentEvent.class)), this::on),
                Case($(instanceOf(FriendRequestAcceptedEvent.class)), this::on),
                Case($(instanceOf(FriendRequestDeclinedEvent.class)), this::on),
                Case($(instanceOf(MessageSentEvent.class)), this::on),
                Case($(instanceOf(MessageReceivedEvent.class)), this::on),
                Case($(), event -> this)
        );
    }

    // event stream:
    private final List<DomainEvent> domainEvents = new CopyOnWriteArrayList<>();

    public Collection<DomainEvent> getDomainEvents() {
        return domainEvents;
    }

    public void flushEvents() {
        domainEvents.clear();
    }

    // CQRS

    // sign-in/sign-up
    public void handle(CreateUserCommand command) { // cmd
        if (Objects.isNull(command.getId())) throw new IllegalArgumentException("id may not be null."); // nack
        if (Objects.isNull(command.getUsername())) throw new IllegalArgumentException("username may not be null.");
        if (command.getUsername().isEmpty()) throw new IllegalArgumentException("username may not be empty.");
        on(new UserCreatedEvent(command.getId(), command.getUsername())); // ack
    }

    private User on(UserCreatedEvent event) { // evt
        domainEvents.add(event);
        aggregateId = event.getAggregateId();
        username = event.getUsername();
        return this;
    }

    // send friend request
    public void handle(SendFriendRequestCommand command) {
        if (Objects.isNull(command.getFriendId())) throw new IllegalArgumentException("id may not be null."); // nack
        on(new FriendRequestSentEvent(aggregateId, command.getFriendId()));
    }

    private User on(FriendRequestSentEvent event) {
        domainEvents.add(event);
        friendRequest.add(event.getFriendId());
        return this;
    }

    // accept friend request
    public void handle(AcceptFriendRequestCommand command) {
        if (Objects.isNull(command.getId())) throw new IllegalArgumentException("id may not be null."); // nack
        on(new FriendRequestAcceptedEvent(aggregateId, command.getId()));
    }

    private User on(FriendRequestAcceptedEvent event) {
        domainEvents.add(event);
        friends.add(event.getFriendId());
        return this;
    }

    // decline friend request
    public void handle(DeclineFriendRequestCommand command) {
        if (Objects.isNull(command.getUserId())) throw new IllegalArgumentException("id may not be null."); // nack
        on(new FriendRequestDeclinedEvent(aggregateId, command.getUserId()));
    }

    private User on(FriendRequestDeclinedEvent event) {
        domainEvents.add(event);
        return this;
    }

    // send message
    public void handle(SendMessageCommand command) {
        if (Objects.isNull(command.getMessage())) throw new IllegalArgumentException("message may not be null.");
        if (Objects.isNull(command.getRecipient())) throw new IllegalArgumentException("recipient may not be null."); // nack
        if (!friends.contains(command.getRecipient())) throw new IllegalArgumentException("recipient must be your friend.");
        on(new MessageSentEvent(aggregateId, command.getRecipient(), command.getMessage(), ZonedDateTime.now()));
    }

    private User on(MessageSentEvent event) {
        domainEvents.add(event);
        messages.add(new Message(event.getAggregateId(), event.getRecipient(), event.getMessage(), event.getAt()));
        return this;
    }

    // receive message
    public void handle(ReceiveMessageCommand command) {
        if (Objects.isNull(command.getMessage())) throw new IllegalArgumentException("message may not be null.");// nack
        if (Objects.isNull(command.getSender())) throw new IllegalArgumentException("sender may not be null.");
        on(new MessageReceivedEvent(aggregateId, command.getSender(), command.getMessage(), ZonedDateTime.now()));
    }

    private User on(MessageReceivedEvent event) {
        domainEvents.add(event);
        messages.add(new Message(event.getSender(), event.getAggregateId(), event.getMessage(), event.getAt()));
        return this;
    }

    // in java we trust
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(aggregateId, user.aggregateId) &&
                Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateId, username);
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

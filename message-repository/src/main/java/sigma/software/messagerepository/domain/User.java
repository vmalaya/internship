package sigma.software.messagerepository.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.vavr.API;
import sigma.software.messagerepository.domain.command.*;
import sigma.software.messagerepository.domain.event.*;
import sigma.software.messagerepository.domain.event.api.DomainEvent;

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
import static java.time.ZonedDateTime.now;
import static sigma.software.messagerepository.domain.Message.Type.INCOMING;
import static sigma.software.messagerepository.domain.Message.Type.OUTGOING;

/**
 * Aggregate.
 */
public class User implements Function<DomainEvent, User> {

    // event stream:
    private List<DomainEvent> domainEvents = new CopyOnWriteArrayList<>();

    public Collection<DomainEvent> getDomainEvents() {
        return domainEvents;
    }

    public void flushEvents() {
        domainEvents.clear();
    }

    // state:
    private UUID aggregateId;
    private String username;
    private Collection<UUID> friendRequest = new CopyOnWriteArraySet<>();
    private Collection<UUID> friends = new CopyOnWriteArraySet<>();
    private Collection<Message> messages = new CopyOnWriteArrayList<>();

    // creation

    public User() { // 1: main internal
        // TODO
    }

    @JsonCreator
    public User(@JsonProperty("aggregateId") UUID aggregateId,
                @JsonProperty("username") String username) {
        this.aggregateId = aggregateId;
        this.username = username;
    }

    // 2.1: helper to avoid list -> array conversion
    public User(UUID aggregateId, Collection<DomainEvent> domainEvents) {
        this(aggregateId,
             null,
             new CopyOnWriteArrayList<>(),
             new CopyOnWriteArrayList<>(),
             new CopyOnWriteArrayList<>(),
             domainEvents.toArray(new DomainEvent[0]));
    }

    public User(UUID aggregateId,
                String username,
                Collection<UUID> friendRequest,
                Collection<UUID> friends,
                Collection<Message> messages,
                Collection<DomainEvent> domainEvents) { // 2.2: helper to avoid list -> array conversion

        this(aggregateId,
             username,
             friendRequest,
             friends,
             messages,
             domainEvents.toArray(new DomainEvent[0]));
    }

    public User(UUID aggregateId,
                String username,
                Collection<UUID> friendRequest,
                Collection<UUID> friends,
                Collection<Message> messages,
                DomainEvent... domainEvents) { // 3: full, will be used in command handler...

        Objects.requireNonNull(aggregateId);
        // all other fields in between aggregateId and domainEvents are optional in case
        // if aggregate is recreating from beginning.
        Objects.requireNonNull(domainEvents);

        this.domainEvents = new CopyOnWriteArrayList<>();
        this.aggregateId = aggregateId;
        this.username = username;
        this.friendRequest = friendRequest;
        this.friends = friends;
        this.messages = messages;

        for (DomainEvent domainEvent : domainEvents) {
            apply(domainEvent);
        }
    }

    // event sourcing
    public static User recreate(User snapshot, Collection<DomainEvent> history) {
        User user = io.vavr.collection.List.ofAll(history)
                                           .foldLeft(snapshot, User::apply);
        user.domainEvents.clear();
        return user;
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
        if (Objects.isNull(command.getAggregateId()))
            throw new IllegalArgumentException("aggregate id may not be null."); // nack
        if (!command.getAggregateId().equals(aggregateId))
            throw new IllegalArgumentException("sender id must be same as aggregate id."); // nack
        if (Objects.isNull(command.getFriendId()))
            throw new IllegalArgumentException("friend id may not be null."); // nack
        on(new FriendRequestSentEvent(aggregateId, command.getFriendId()));
    }

    private User on(FriendRequestSentEvent event) {
        domainEvents.add(event);
        // friendRequest.add(event.getFriendId());
        return this;
    }

    // receive friend request
    public void handle(ReceiveFriendRequestCommand command) {}

    // accept friend request
    public void handle(AcceptFriendRequestCommand command) {
        if (Objects.isNull(command.getAggregateId()))
            throw new IllegalArgumentException("aggregate id may not be null."); // nack
        if (!command.getAggregateId().equals(aggregateId))
            throw new IllegalArgumentException("sender id must be same as aggregate id.");
        if (Objects.isNull(command.getId())) throw new IllegalArgumentException("id may not be null.");
        on(new FriendRequestAcceptedEvent(aggregateId, command.getId()));
    }

    private User on(FriendRequestAcceptedEvent event) {
        domainEvents.add(event);
        friends.add(event.getFriendId());
        return this;
    }

    // decline friend request
    public void handle(DeclineFriendRequestCommand command) {
        if (Objects.isNull(command.getAggregateId()))
            throw new IllegalArgumentException("aggregate id may not be null."); // nack
        if (!command.getAggregateId().equals(aggregateId))
            throw new IllegalArgumentException("decliner id must be same as aggregate id.");
        if (Objects.isNull(command.getUserId())) throw new IllegalArgumentException("id may not be null.");
        on(new FriendRequestDeclinedEvent(aggregateId, command.getUserId()));
    }

    private User on(FriendRequestDeclinedEvent event) {
        domainEvents.add(event);
        return this;
    }

    // send message
    public void handle(SendMessageCommand command) {
        if (Objects.isNull(command.getAggregateId()))
            throw new IllegalArgumentException("aggregate id may not be null."); // nack
        if (!command.getAggregateId().equals(aggregateId))
            throw new IllegalArgumentException("sender id must be same as aggregate id.");
        if (Objects.isNull(command.getMessage())) throw new IllegalArgumentException("message may not be null.");
        if (Objects.isNull(command.getRecipient()))
            throw new IllegalArgumentException("recipient may not be null.");
        if (!friends.contains(command.getRecipient()))
            throw new IllegalArgumentException("recipient must be your friend.");
        on(new MessageSentEvent(aggregateId, command.getRecipient(), command.getMessage(), OUTGOING, now()));
    }

    private User on(MessageSentEvent event) {
        domainEvents.add(event);
        messages.add(new Message(event.getAggregateId(),
                                 event.getRecipient(),
                                 event.getBody(),
                                 event.getType(),
                                 event.getAt()));
        return this;
    }

    // receive message
    public void handle(ReceiveMessageCommand command) {
        if (Objects.isNull(command.getAggregateId()))
            throw new IllegalArgumentException("aggregate id may not be null."); // nack
        if (!command.getAggregateId().equals(aggregateId))
            throw new IllegalArgumentException("recipient id must be same as aggregate id.");
        if (Objects.isNull(command.getMessage())) throw new IllegalArgumentException("message may not be null.");
        if (Objects.isNull(command.getSender())) throw new IllegalArgumentException("sender may not be null.");
        on(new MessageReceivedEvent(aggregateId, command.getSender(), command.getMessage(), INCOMING, now()));
    }

    private User on(MessageReceivedEvent event) {
        domainEvents.add(event);
        messages.add(new Message(event.getSender(),
                                 event.getAggregateId(),
                                 event.getMessage(),
                                 event.getType(),
                                 event.getAt()));
        return this;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(aggregateId, user.aggregateId) &&
                Objects.equals(username, user.username) &&
                Objects.equals(friendRequest, user.friendRequest) &&
                Objects.equals(friends, user.friends) &&
                Objects.equals(messages, user.messages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateId, username, friendRequest, friends, messages);
    }
}

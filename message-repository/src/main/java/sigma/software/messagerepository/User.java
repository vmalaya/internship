package sigma.software.messagerepository;

import io.vavr.API;
import sigma.software.messagerepository.command.AcceptFriendRequestCommand;
import sigma.software.messagerepository.command.CreateUserCommand;
import sigma.software.messagerepository.command.DeclineFriendRequestCommand;
import sigma.software.messagerepository.command.SendFriendRequestCommand;
import sigma.software.messagerepository.event.*;

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

public class User implements Function<DomainEvent, User> {

    // state:
    private UUID id;
    private String username;
    private Collection<UUID> friendRequest = new CopyOnWriteArraySet<>();
    private Collection<UUID> friends = new CopyOnWriteArraySet<>();

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
        id = event.getId();
        username = event.getUsername();
        return this;
    }

    // send friend request
    public void handle(SendFriendRequestCommand command) {
        if (Objects.isNull(command.getFriendId())) throw new IllegalArgumentException("id may not be null."); // nack
        on(new FriendRequestSentEvent(command.getFriendId()));
    }

    private User on(FriendRequestSentEvent event) {
        domainEvents.add(event);
        friendRequest.add(event.getFriendId());
        return this;
    }

    // accept friend request
    public void handle(AcceptFriendRequestCommand command) {
        if (Objects.isNull(command.getId())) throw new IllegalArgumentException("id may not be null."); // nack
        on(new FriendRequestAcceptedEvent(command.getId()));
    }

    private User on(FriendRequestAcceptedEvent event) {
        domainEvents.add(event);
        friends.add(event.getFriendId());
        return this;
    }

    // decline friend request
    public void handle(DeclineFriendRequestCommand command) {
        if (Objects.isNull(command.getUserId())) throw new IllegalArgumentException("id may not be null."); // nack
        on(new FriendRequestDeclinedEvent(command.getUserId()));
    }

    private User on(FriendRequestDeclinedEvent event) {
        domainEvents.add(event);
        return this;
    }

    // in java we trust
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

    public UUID getId() {
        return id;
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
}

package sigma.software.messagerepository;

import io.vavr.API;
import sigma.software.messagerepository.command.ChangeUsernameCommand;
import sigma.software.messagerepository.command.CreateUserCommand;
import sigma.software.messagerepository.event.DomainEvent;
import sigma.software.messagerepository.event.UserCreatedEvent;
import sigma.software.messagerepository.event.UsernameChangedEvent;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.stream.IntStream;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.Predicates.instanceOf;

public class User implements Function<DomainEvent, User> {

    private UUID id;
    private String username;

    // in java we trust
    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public User() {
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

    // commands
    public void handle(CreateUserCommand command) { // cmd
        if (Objects.isNull(command.getId())) throw new IllegalArgumentException("id may not be null."); // nack
        if (Objects.isNull(command.getUsername())) throw new IllegalArgumentException("username may not be null.");
        if (command.getUsername().isEmpty()) throw new IllegalArgumentException("username may not be empty.");
        on(new UserCreatedEvent(command.getId(), command.getUsername())); // ack
    }

    // events
    private User on(UserCreatedEvent event) { // evt
        domainEvents.add(event);
        id = event.getId();
        username = event.getUsername();
        return this;
    }

    // commands
    public void handle(ChangeUsernameCommand command) { // cmd
        if (Objects.isNull(command.getId())) throw new IllegalArgumentException("id may not be null."); // nack
        if (Objects.isNull(command.getFrom())) throw new IllegalArgumentException("from username may not be null.");
        if (command.getFrom().isEmpty()) throw new IllegalArgumentException("from username may not be empty.");
        if (Objects.isNull(command.getTo())) throw new IllegalArgumentException("to username may not be null.");
        if (command.getTo().isEmpty()) throw new IllegalArgumentException("to username may not be empty.");
        if (!command.getFrom().equals(username)) throw new IllegalArgumentException("wrong from username.");
        on(new UsernameChangedEvent(command.getId(), command.getFrom(), command.getTo())); // ack
    }

    // events
    private User on(UsernameChangedEvent event) { // evt
        domainEvents.add(event);
        username = event.getTo();
        return this;
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
                Case($(instanceOf(UsernameChangedEvent.class)), this::on),
                Case($(), event -> this)
        );
    }

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
}

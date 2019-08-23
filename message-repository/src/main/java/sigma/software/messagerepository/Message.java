package sigma.software.messagerepository;

import sigma.software.messagerepository.command.CreateMessageCommand;
import sigma.software.messagerepository.event.MessageCreatedEvent;

import java.util.Objects;
import java.util.UUID;

public class Message {
    private UUID id;
    private UUID idFriend;
    private String text;

    public Message() {
    }

    public UUID getId() {
        return id;
    }

    public UUID getIdFriend() {
        return idFriend;
    }

    public String getText() {
        return text;
    }

    public void handle(CreateMessageCommand command) {
        if (Objects.isNull(command.getId())) throw new IllegalArgumentException("id may not be null.");// nack
        if (Objects.isNull(command.getIdFriend())) throw new IllegalArgumentException("friend id may not be null");
        if (Objects.isNull(command.getText())) throw new IllegalArgumentException("text may not be null.");
        if (command.getText().isEmpty()) throw new IllegalArgumentException("message should not be empty.");
        on(new MessageCreatedEvent(command.getId(), command.getIdFriend(), command.getText()));
    }

    private Message on(MessageCreatedEvent event) {
        id = event.getId();
        idFriend = event.getIdFriend();
        text = event.getText();
        return this;
    }
}

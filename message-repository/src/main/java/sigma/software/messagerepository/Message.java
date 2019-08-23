package sigma.software.messagerepository;

import sigma.software.messagerepository.command.CreateMessageCommand;
import sigma.software.messagerepository.event.MessageCreatedEvent;

import java.util.Objects;
import java.util.UUID;

public class Message {
    private UUID id;
    private String text;

    public Message() {
    }

    // create message
    public void handle(CreateMessageCommand command) {
        if (Objects.isNull(command.getId())) throw new IllegalArgumentException("id may not be null.");// nack
        if (Objects.isNull(command.getText())) throw new IllegalArgumentException("text may not be null.");
        if (command.getText().isEmpty()) throw new IllegalArgumentException("message should not be empty.");
        on(new MessageCreatedEvent(command.getId(), command.getText()));
    }

    private Message on(MessageCreatedEvent event) {
        id = event.getId();
        text = event.getText();
        return this;
    }

    public UUID getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}

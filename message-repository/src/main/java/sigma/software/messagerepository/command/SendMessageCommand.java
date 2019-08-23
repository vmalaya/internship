package sigma.software.messagerepository.command;

import sigma.software.messagerepository.Message;

public class SendMessageCommand {

    private final Message message;

    public SendMessageCommand(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }
}

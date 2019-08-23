package sigma.software.messagerepository.command;

public class RevealLastMessagesInDescOrderCommand {

    private int limit;

    public RevealLastMessagesInDescOrderCommand(int limit) {
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }
}

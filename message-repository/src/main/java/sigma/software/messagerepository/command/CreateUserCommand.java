package sigma.software.messagerepository.command;

import java.util.UUID;

public class CreateUserCommand {
    private final UUID id;
    private final String username;

    public CreateUserCommand(UUID id, String username) {
        this.id = id;
        this.username = username;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}

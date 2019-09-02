package sigma.software.messagerepository;

import sigma.software.messagerepository.domain.command.CreateUserCommand;
import sigma.software.messagerepository.domain.service.UserService;
import sigma.software.messagerepository.domain.service.gateway.CommandGateway;
import sigma.software.messagerepository.domain.service.gateway.QueryGateway;
import sigma.software.messagerepository.domain.service.gateway.repository.UserRepository;
import sigma.software.messagerepository.domain.service.gateway.repository.eventstore.EventStore;
import sigma.software.messagerepository.domain.service.gateway.repository.eventstore.config.EventStoreConfig;
import sigma.software.messagerepository.domain.service.gateway.repository.eventstore.config.JacksonConfig;

import java.util.Objects;
import java.util.UUID;

public class Main {

    enum ExitCode {

        OK(0, "OK"),
        USAGE(1, "See command usage help..."),
        BAD_REQUEST(2, "Bad request"),
        ;

        public final int codeNumber;
        public final String codeName;

        ExitCode(int codeNumber, String codeName) {
            this.codeNumber = codeNumber;
            this.codeName = codeName;
        }
    }

    /*
     * mr signup valentina.malaya
     *  -> 00000000-0000-0000-0000-000000000000
     *
     * mr invite 11111111-1111-1111-1111-111111111111
     *  1) check if friend exists ...
     *     otherwise ...
     *
     * mr invites
     *  -> 00000000-0000-0000-0000-000000000000
     *     ...
     * mr accept 00000000-0000-0000-0000-000000000000
     *
     * mr friends
     *  -> username 0000000-...
     *
     * mr send 00000000-...
     *  1)check if friend exists ...
     *  2) mr text
     *
     * query
     * mr get-last-messages
     *
     * mr get-lest-messages limit
     *
     *
     */
    public static void main(String[] args) {

        JacksonConfig jacksonConfig = new JacksonConfig();
        EventStoreConfig evenStoreConfig = new EventStoreConfig();
        EventStore eventStore = new EventStore(jacksonConfig, evenStoreConfig);
        UserRepository repository = new UserRepository(eventStore);
        QueryGateway queryGateway = new QueryGateway(repository);
        CommandGateway commandGateway = new CommandGateway(repository);
        UserService userService = new UserService(queryGateway, commandGateway);
        UUID currentUserId;

        boolean hasNoArguments = Objects.isNull(args);
        if (hasNoArguments) {
            System.err.println(ExitCode.BAD_REQUEST.codeName);
            System.exit(ExitCode.BAD_REQUEST.codeNumber);
        }

        if (args.length == 0) {
            System.err.println(ExitCode.USAGE.codeName);
            System.exit(ExitCode.USAGE.codeNumber);
        }

        if (args.length == 2 && "signup".equalsIgnoreCase(args[0])) {
            System.out.println("registering a new user with username: " + args[1]);
            currentUserId = UUID.randomUUID();
            commandGateway.handle(new CreateUserCommand(currentUserId, args[1]));
            System.out.println(ExitCode.OK.codeNumber);
            System.exit(ExitCode.OK.codeNumber);
        }

        // if (args.length == 2 && "invite".equalsIgnoreCase(args[0])) {
        //     if (Objects.nonNull(repository.load(UUID.fromString(args[1])).getAggregateId())) {
        //         System.out.println("sending invitation to a user " + args[1] + " ...");
        //         commandGateway.handle(new SendFriendRequestCommand(currentUserId,
        //                                                            UUID.fromString(args[1])));
        //         System.out.println("Friend request was sent.");
        //     } else {
        //         System.out.println("There is no such user.");
        //     }
        // }
        //
        // if (args.length == 2 && "invites".equalsIgnoreCase(args[0])) {
        //     repository.
        //     if (currentUser)
        // }

        System.err.println(ExitCode.USAGE.codeName);
        System.exit(ExitCode.USAGE.codeNumber);
    }
}

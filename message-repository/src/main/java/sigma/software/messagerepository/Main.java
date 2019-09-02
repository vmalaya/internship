package sigma.software.messagerepository;

import sigma.software.messagerepository.domain.service.gateway.CommandGateway;
import sigma.software.messagerepository.domain.service.gateway.QueryGateway;
import sigma.software.messagerepository.domain.service.gateway.repository.UserRepository;
import sigma.software.messagerepository.domain.service.UserService;

import java.util.Objects;

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

    public static void main(String[] args) {

        UserRepository repository = new UserRepository();
        QueryGateway queryGateway = new QueryGateway(repository);
        CommandGateway commandGateway = new CommandGateway(repository);
        UserService userService = new UserService(queryGateway, commandGateway);

        boolean hasNoArguments = Objects.isNull(args);
        if (hasNoArguments) {
            System.err.println(ExitCode.BAD_REQUEST.codeName);
            System.exit(ExitCode.BAD_REQUEST.codeNumber);
        }

        if (args.length == 0) {
            System.err.println(ExitCode.USAGE.codeName);
            System.exit(ExitCode.USAGE.codeNumber);
        }

        if (args.length == 2 && "register".equalsIgnoreCase(args[0])) {
            System.out.println("registering a new user with username: " + args[1]);
            System.out.println(ExitCode.OK.codeNumber);
            System.exit(ExitCode.OK.codeNumber);
        }

        System.err.println(ExitCode.USAGE.codeName);
        System.exit(ExitCode.USAGE.codeNumber);
    }
}

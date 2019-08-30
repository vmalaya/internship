package sigma.software.messagerepository;

import sigma.software.messagerepository.domain.CommandHandler;
import sigma.software.messagerepository.domain.QueryHandler;
import sigma.software.messagerepository.domain.UserRepository;

import java.util.Objects;

public class Main {

    enum ExitCode {

        USAGE(400, "See command usage help..."),
        BARD_REQUEST(400, "Bad request"),
        OK(200, "OK");

        public final int codeNumber;
        public final String codeName;

        ExitCode(int codeNumber, String codeName) {
            this.codeNumber = codeNumber;
            this.codeName = codeName;
        }
    }

    public static void main(String[] args) {

        UserRepository repository = new UserRepository();
        CommandHandler commandGateway = new CommandHandler(repository);
        QueryHandler queryGateway = new QueryHandler(repository);

        boolean hasNoArguments = Objects.isNull(args);
        if (hasNoArguments) {
            System.err.println(ExitCode.BARD_REQUEST.codeName);
            System.exit(ExitCode.BARD_REQUEST.codeNumber);
        }

        if (args.length == 0) {
            System.err.println(ExitCode.USAGE.codeName);
            System.exit(ExitCode.USAGE.codeNumber);
        }

        if (args.length == 2 && "register".equalsIgnoreCase(args[0])) {
            System.out.println("registering a new user with username: " + args[1]);
            System.err.println(ExitCode.OK.codeNumber);
            System.exit(ExitCode.OK.codeNumber);
        }

        System.err.println(ExitCode.USAGE.codeName);
        System.exit(ExitCode.USAGE.codeNumber);
    }
}

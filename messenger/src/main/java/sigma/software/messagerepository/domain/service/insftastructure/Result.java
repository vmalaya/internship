package sigma.software.messagerepository.domain.service.insftastructure;

public enum Result {
    OK(0, "OK"),
    CANNOT_PARSE_ID(400, "Cannot parse identifier!"),
    UNAUTHORIZED(401, "Please sign in."),
    USER_NOT_FOUND(404, "User not found."),
    USAGE(400, "\n" +
            "Command Line Messenger usage:\n" +
            "\n\tSign Up (register) user with optional ID:\n" +
            "\n\t\tmr signUp 00000000-0000-0000-0000-000000000000\n" +
            "\n\tSign In (login) by user ID:\n" +
            "\n\t\tmr signIn 00000000-0000-0000-0000-000000000000\n" +
            "\n\tSend friend request to another user (desired future friend) in the system by his (ID):\n" +
            "\n\t\tmr invite 11111111-1111-1111-1111-111111111111\n" +
            "\n\tShow friendship requests from other people by theirs IDs:\n" +
            "\n\t\tmr invites\n" +
            "\n\tApprove friend request from user bu his ID:\n" +
            "\n\t\tmr accept 00000000-0000-0000-0000-000000000000\n" +
            "\n\tReject friendship by nis (not friend) ID:\n" +
            "\n\t\tmr decline 00000000-0000-0000-0000-000000000000\n" +
            "\n\tShow friends by theirs IDs:\n" +
            "\n\t\tmr friends\n" +
            "\n\tSend message to friend by his ID:\n" +
            "\n\t\tmr message 11111111-1111-1111-1111-111111111111 Hello.\n" +
            "\n\tShow conversations:\n" +
            "\n\t\tmr messages\n"),
    ;

    public final int codeNumber;
    public final String codeName;

    Result(int codeNumber, String codeName) {
        this.codeNumber = codeNumber;
        this.codeName = codeName;
    }

    public void printUserOutputAndExit() {
        if (codeNumber != 0) System.err.println(codeName);
        // else System.out.println(codeName);
        System.exit(codeNumber);
    }
}

package sigma.software.messagerepository.domain.service.insftastructure;

public enum Result {
    OK(0, "OK"),
    USAGE(1, "TODO: See command usage help (Implement me please...)"),
    USER_NOT_FOUND(404, "User not found."),
    EMPTY_LIST(3, "List is empty."),
    BAD_REQUEST(2, "Bad request"),
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

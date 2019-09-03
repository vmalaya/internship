package sigma.software.messagerepository;

import sigma.software.messagerepository.domain.service.UserService;

import java.io.IOException;
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

    /* MR functionality
     * - sign up
     * call signup(username) from service:
     * mr signup valentina.malaya
     *  -> 00000000-0000-0000-0000-000000000000
     * Was created directory .mr including config file in user.home.
     * Config file contains user id, username.
     *
     * - sing in
     * call signin(username) from service:
     * mr signin valentina.malaya
     * If config file exist and username equals input user get
     *  -> 00000000-0000-0000-0000-000000000000
     * If don't exist or file contains another username
     *  -> User not found. Please use signup.
     *
     * - invite friend
     * call invite(friendId) from service:
     * mr invite 11111111-1111-1111-1111-111111111111
     *  1) check if friend exists... (search if exist file with friendId events in directory events?)
     *      add friend request to friend
     *      -> Friend request sent.
     *     otherwise
     *       -> User not found.
     *
     * - get all existed friend request
     * call getFriendRequests(aggregateId) from service:
     * mr invites
     *  1) If current user have id equals aggregateId
     *      1.1) if user have friend requests
     *       -> 00000000-0000-0000-0000-000000000000
     *       -> 22222222-2222-2222-2222-222222222222
     *      1.2)
     *       -> You have no friend request
     *  2) else
     *      -> Please sign in as aggregateId.
     *
     * - accept friend request
     * call accept(friendId) from service:
     * mr accept 00000000-0000-0000-0000-000000000000
     * 1) If current user exist
     *      Add to friend list friendId
     *      -> 00000000-0000-0000-0000-000000000000
     *      friend.accept(currentUser.id)
     * 2) -> User not found.
     *
     * - get list of friend
     * mr friends
     * 1) if current user have friends
     *  -> 0000000-...
     * 2) Your friend list is empty.
     *
     * - send message
     * call checkIfFriend(friendId) from service:
     * mr send 00000000-...
     *  1)check if friendId is friend current user
     *      -> Please, input message:
     *      mr send message
     *      call send(friendId, message) from service
     *  2) You have no such friend
     *
     * query
     *
     * - get messages
     * call getAllMessages(friendId)
     * mr messages friendId
     *  1) check if friendId is friend current user
     *      -> msg1
     *      -> msg2
     *  2) You have no such friend
     *
     * - get last messages
     * call getLastMessages(friendId, limit)
     * mr last-messages friendId limit
     *      1) call checkIfFriend(friendId) from service:
     *
     *
     */

    static UserService userService = new UserService();

    public static void main(String[] args) throws IOException {

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
            userService.signup(args[1]);
            System.out.println(ExitCode.OK.codeNumber);
            System.exit(ExitCode.OK.codeNumber);
        }

        if (args.length == 2 && "signin".equalsIgnoreCase(args[0])) {
            String username = userService.signin(args[1]);
            if (Objects.nonNull(username)) {
                System.out.println(username);
            } else {
                System.out.println("User not found. Please use command signup.");
            }
        }

        if (args.length == 2 && "invite".equalsIgnoreCase(args[0])) {
            String invited = userService.invite(args[1]);
            if (Objects.nonNull(invited)) {
                System.out.println(invited);
            } else {
                System.err.println("User not found.");
            }
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

package sigma.software.messagerepository;

import io.vavr.control.Try;
import sigma.software.messagerepository.domain.service.UserService;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class Main {

    enum ExitCode {

        OK(0, "OK"),
        USAGE(1, "TODO: See command usage help (Implement me please...)"),
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

        if (args.length == 0) {
            System.err.println(ExitCode.USAGE.codeName);
            System.exit(ExitCode.USAGE.codeNumber);
        }

        String signUpCommand = args[0];
        if (args.length >= 2 && "signup".equalsIgnoreCase(signUpCommand)) {

            String username = args[1];
            Try<UUID> aTry = args.length > 2 ? Try.of(() -> UUID.fromString(args[2])) : Try.of(UUID::randomUUID);
            if (aTry.isFailure()) {
                System.err.println("Can not parse id.");
                System.exit(ExitCode.BAD_REQUEST.codeNumber);
            }

            UUID id = userService.signup(username, aTry.get());
            System.out.printf("%s user created. user this id to signin: %s\n", username, id);
            // System.out.println(ExitCode.OK.codeNumber);
            System.exit(ExitCode.OK.codeNumber);
        }

        String signInCommand = args[0];
        if (args.length == 2 && "signin".equalsIgnoreCase(signInCommand)) {

            Try<UUID> aTry = Try.of(() -> UUID.fromString(args[1]));
            if (aTry.isFailure()) {
                System.err.println("Can not parse id.");
                System.exit(ExitCode.BAD_REQUEST.codeNumber);
            }

            String username = userService.signin(aTry.get());
            if (Objects.isNull(username)) {
                System.out.println("User not found. Please use command signup.");
                System.exit(ExitCode.BAD_REQUEST.codeNumber);
            }

            System.out.println(username);
            System.exit(ExitCode.OK.codeNumber);
        }

        String inviteCommand = args[0];
        if (args.length == 2 && "invite".equalsIgnoreCase(inviteCommand)) {
            String desiredFriendId = args[1];
            Try<UUID> aTry = Try.of(() -> UUID.fromString(desiredFriendId));
            if (aTry.isFailure()) {
                System.err.println("Can not parse id.");
                System.exit(ExitCode.BAD_REQUEST.codeNumber);
            }

            UUID friendId = aTry.get();
            UUID desiredFriend = userService.invite(friendId);
            if (Objects.isNull(desiredFriend)) {
                System.err.println("Desired friend hasn't been found.");
                System.exit(ExitCode.BAD_REQUEST.codeNumber);
            }

            System.out.println("Friend invited.");
            System.exit(ExitCode.OK.codeNumber);
        }

        String invitesQuery = args[0];
        if (args.length == 1 && "invites".equalsIgnoreCase(invitesQuery)) {
            String friendRequests = userService.invites();
            System.out.printf("Friends requests: %s\n", friendRequests);
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

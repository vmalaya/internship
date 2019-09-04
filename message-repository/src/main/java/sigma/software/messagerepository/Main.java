package sigma.software.messagerepository;

import sigma.software.messagerepository.domain.service.UserService;
import sigma.software.messagerepository.domain.service.insftastructure.Process;
import sigma.software.messagerepository.domain.service.insftastructure.Result;

public class Main {

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

    public static void main(String[] args) {

        int len = args.length;
        Process actionQuery = Process
                .builder()
                .on(as -> len == 0, UserService::usage)
                .on(as -> len >= 2 && "signup".equalsIgnoreCase(args[0]), UserService::signup)
                .on(as -> len == 2 && "signin".equalsIgnoreCase(args[0]), UserService::signin)
                .on(as -> len == 2 && "invite".equalsIgnoreCase(args[0]), UserService::invite)
                .on(as -> len == 1 && "invites".equalsIgnoreCase(args[0]), UserService::invites)
                .on(as -> len == 2 && "accept".equalsIgnoreCase(args[0]), UserService::accept)
                .on(as -> len == 2 && "decline".equalsIgnoreCase(args[0]), UserService::decline)
                .on(as -> len == 1 && "friends".equalsIgnoreCase(args[0]), UserService::friends)
                .on(as -> len == 1 && "messages".equalsIgnoreCase(args[0]), UserService::messages)
                // .on(as -> args.length >= 2 && "signup".equalsIgnoreCase(args[0]),
                //            as -> Result.OK)
                // .on(as -> args.length == 2 && "signin".equalsIgnoreCase(args[0]),
                //            as -> Result.OK)
                // .otherwise(as -> Result.USAGE)
                .build();
        userService.process(actionQuery, args);

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

        System.err.println(Result.USAGE.codeName);
        System.exit(Result.USAGE.codeNumber);
    }
}

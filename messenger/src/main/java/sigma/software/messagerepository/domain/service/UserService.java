package sigma.software.messagerepository.domain.service;

import io.vavr.control.Try;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sigma.software.messagerepository.domain.command.*;
import sigma.software.messagerepository.domain.query.*;
import sigma.software.messagerepository.domain.query.api.QueryResponse;
import sigma.software.messagerepository.domain.service.gateway.CommandGateway;
import sigma.software.messagerepository.domain.service.gateway.QueryGateway;
import sigma.software.messagerepository.domain.service.insftastructure.Environment;
import sigma.software.messagerepository.domain.service.insftastructure.Process;
import sigma.software.messagerepository.domain.service.insftastructure.Result;

import java.util.*;
import java.util.stream.Collectors;

public class UserService {

    private static final Logger log = LogManager.getLogger();

    private final Environment environment;
    private final QueryGateway queryGateway;
    private final CommandGateway commandGateway;

    public UserService(Environment environment, QueryGateway queryGateway, CommandGateway commandGateway) {
        this.environment = environment;
        this.queryGateway = queryGateway;
        this.commandGateway = commandGateway;
    }

    /* Public API */

    public Result signUp(String... args) {
        // if (args.length < 2) return Result.USAGE;
        //
        String username = args[1];
        Try<UUID> aTry = args.length > 2 ? Try.of(() -> UUID.fromString(args[2])) : Try.of(UUID::randomUUID);
        if (aTry.isFailure()) return Result.CANNOT_PARSE_ID;

        UUID userId = aTry.get();
        commandGateway.apply(new CreateUserCommand(userId, username));
        QueryResponse queryResponse = queryGateway.apply(new UserRequest(userId));
        UserResponse user = (UserResponse) queryResponse;
        environment.createHomeConfig(user);
        System.out.printf("%s user created. use next id to sign in: %s%n", username, userId);
        return Result.OK;
    }

    public Result signIn(String... args) {
        // if (args.length < 2) return Result.USAGE;
        //
        Try<UUID> aTry = Try.of(() -> UUID.fromString(args[1]));
        if (aTry.isFailure()) return Result.CANNOT_PARSE_ID;

        UUID userId = aTry.get();
        QueryResponse queryResponse = queryGateway.apply(new UserRequest(userId));
        UserResponse user = (UserResponse) queryResponse;
        String username = user.getUsername();
        if (Objects.isNull(username)) return Result.USER_NOT_FOUND;

        environment.createHomeConfig(user);
        System.out.printf("%s user logged in.%n", username);
        return Result.OK;
    }

    public Result invite(String... args) {
        // if (args.length < 2) return Result.USAGE;
        //
        Optional<UUID> authentication = environment.getAuthentication();
        if (!authentication.isPresent()) return Result.UNAUTHORIZED;

        String maybeFriendId = args[1];
        Try<UUID> aTry = Try.of(() -> UUID.fromString(maybeFriendId));
        if (aTry.isFailure()) return Result.CANNOT_PARSE_ID;

        UUID friendId = aTry.get();
        QueryResponse queryResponse = queryGateway.apply(new UserRequest(friendId));
        UserResponse friend = (UserResponse) queryResponse;
        if (Objects.isNull(friend.getAggregateId())) return Result.USER_NOT_FOUND;

        UUID userId = authentication.get();
        commandGateway.apply(new SendFriendRequestCommand(userId, friendId));
        commandGateway.apply(new ReceiveFriendRequestCommand(friendId, userId));
        System.out.println("Friend request sent.");
        return Result.OK;
    }

    public Result invites(String... args) {
        // if (args.length < 1) return Result.USAGE;
        //
        Optional<UUID> authentication = environment.getAuthentication();
        if (!authentication.isPresent()) return Result.UNAUTHORIZED;

        UUID userId = authentication.get();
        QueryResponse queryResponse = queryGateway.apply(new UserFriendRequestsRequest(userId));
        UserFriendRequestsResponse response = (UserFriendRequestsResponse) queryResponse;
        System.out.printf("My friends invites: %s%n", response.getAllRequests());
        return Result.OK;
    }

    public Result accept(String... args) {
        // if (args.length < 2) return Result.USAGE;
        //
        Optional<UUID> authentication = environment.getAuthentication();
        if (!authentication.isPresent()) return Result.UNAUTHORIZED;

        String maybeFriendId = args[1];
        Try<UUID> aTry = Try.of(() -> UUID.fromString(maybeFriendId));
        if (aTry.isFailure()) return Result.CANNOT_PARSE_ID;

        UUID friendIdToAccept = aTry.get();
        UUID userId = authentication.get();

        commandGateway.apply(new AcceptFriendRequestCommand(userId, friendIdToAccept));
        commandGateway.apply(new AcceptFriendRequestCommand(friendIdToAccept, userId));

        return Result.OK;
    }

    public Result decline(String... args) {
        // if (args.length < 2) return Result.USAGE;
        //
        Optional<UUID> authentication = environment.getAuthentication();
        if (!authentication.isPresent()) return Result.UNAUTHORIZED;

        String maybeNotFriendId = args[1];
        Try<UUID> aTry = Try.of(() -> UUID.fromString(maybeNotFriendId));
        if (aTry.isFailure()) return Result.CANNOT_PARSE_ID;

        UUID notFriendId = aTry.get();
        UUID userId = authentication.get();
        commandGateway.apply(new DeclineFriendRequestCommand(userId, notFriendId));
        System.out.printf("Friend request from %s declined.%n", notFriendId);
        return Result.OK;
    }

    public Result friends(String... args) {
        // if (args.length < 1) return Result.USAGE;
        //
        Optional<UUID> authentication = environment.getAuthentication();
        if (!authentication.isPresent()) return Result.UNAUTHORIZED;

        UUID userId = authentication.get();
        QueryResponse queryResponse = queryGateway.apply(new UserFriendsRequest(userId));
        UserFriendsResponse response = (UserFriendsResponse) queryResponse;
        System.out.printf("My friend list: %s%n", response.getFriends());
        return Result.OK;
    }

    public Result message(String... args) {
        // if (args.length < 3) return Result.USAGE;
        //
        Optional<UUID> authentication = environment.getAuthentication();
        if (!authentication.isPresent()) return Result.UNAUTHORIZED;

        String maybeFriendId = args[1];
        Try<UUID> aTry = Try.of(() -> UUID.fromString(maybeFriendId));
        if (aTry.isFailure()) return Result.CANNOT_PARSE_ID;

        UUID receiver = aTry.get();
        UUID sender = authentication.get();
        String message = Arrays.stream(args)
                               .skip(2)
                               .collect(Collectors.joining(" "));

        commandGateway.apply(new SendMessageCommand(sender, receiver, message));
        commandGateway.apply(new ReceiveMessageCommand(receiver, sender, message));
        System.out.println("Message has been sent.");
        return Result.OK;
    }

    public Result messages(String... args) {
        // if (args.length < 1) return Result.USAGE;
        //
        Optional<UUID> authentication = environment.getAuthentication();
        log.info("auth: {}", authentication.isPresent());
        if (!authentication.isPresent()) return Result.UNAUTHORIZED;

        UUID userId = authentication.get();
        QueryResponse queryResponse = queryGateway.apply(new UserMessagesUserRequest(userId));
        UserMessagesResponse response = (UserMessagesResponse) queryResponse;
        System.out.printf("Found messages:%n%s%n", response.getAllUserMessages());
        return Result.OK;
    }

    public Result usage(String... args) {
        return Result.USAGE;
    }

    public void process(Process actionQuery, String... args) {
        actionQuery.getProcessors().entrySet().stream()
                   .filter(entry -> entry.getKey().test(args))
                   .map(Map.Entry::getValue)
                   .findFirst()
                   .map(action -> action.apply(this, args))
                   .ifPresent(Result::printUserOutputAndExit);
    }
}

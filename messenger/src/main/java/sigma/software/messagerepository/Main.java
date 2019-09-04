package sigma.software.messagerepository;

import sigma.software.messagerepository.config.EventStoreConfig;
import sigma.software.messagerepository.config.JacksonConfig;
import sigma.software.messagerepository.domain.service.UserService;
import sigma.software.messagerepository.domain.service.gateway.CommandGateway;
import sigma.software.messagerepository.domain.service.gateway.QueryGateway;
import sigma.software.messagerepository.domain.service.gateway.repository.UserRepository;
import sigma.software.messagerepository.domain.service.gateway.repository.eventstore.EventStore;
import sigma.software.messagerepository.domain.service.insftastructure.Environment;
import sigma.software.messagerepository.domain.service.insftastructure.Process;

import java.util.Objects;

public class Main {

    public static void main(String[] args) {

        int len = Objects.requireNonNull(args).length;
        Environment environment = new Environment(JacksonConfig.objectMapper);
        EventStore eventStore = new EventStore(JacksonConfig.objectMapper, EventStoreConfig.dbBasePath);
        UserRepository userRepository = new UserRepository(eventStore);
        QueryGateway queryGateway = new QueryGateway(userRepository);
        CommandGateway commandGateway = new CommandGateway(userRepository);
        UserService userService = new UserService(environment, queryGateway, commandGateway);

        userService.process(Process.builder()
                                   .on(in -> len >= 2 && "signUp".equalsIgnoreCase(in[0]), UserService::signUp)
                                   .on(in -> len == 2 && "signIn".equalsIgnoreCase(in[0]), UserService::signIn)
                                   .on(in -> len == 2 && "invite".equalsIgnoreCase(in[0]), UserService::invite)
                                   .on(in -> len == 1 && "invites".equalsIgnoreCase(in[0]), UserService::invites)
                                   .on(in -> len == 2 && "accept".equalsIgnoreCase(in[0]), UserService::accept)
                                   .on(in -> len == 2 && "decline".equalsIgnoreCase(in[0]), UserService::decline)
                                   .on(in -> len == 1 && "friends".equalsIgnoreCase(in[0]), UserService::friends)
                                   .on(in -> len >= 3 && "message".equalsIgnoreCase(in[0]), UserService::message)
                                   .on(in -> len == 1 && "messages".equalsIgnoreCase(in[0]), UserService::messages)
                                   .otherwise(UserService::usage)
                                   .build(), args);
    }
}

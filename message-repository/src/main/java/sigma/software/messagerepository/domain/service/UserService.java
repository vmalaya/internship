package sigma.software.messagerepository.domain.service;

import sigma.software.messagerepository.domain.service.gateway.CommandGateway;
import sigma.software.messagerepository.domain.service.gateway.QueryGateway;

public class UserService {

    private final QueryGateway queryGateway;
    private final CommandGateway commandGateway;

    public UserService(QueryGateway queryGateway, CommandGateway commandGateway) {
        this.queryGateway = queryGateway;
        this.commandGateway = commandGateway;
    }
}

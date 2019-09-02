package sigma.software.messagerepository.domain.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sigma.software.messagerepository.domain.service.gateway.CommandGateway;
import sigma.software.messagerepository.domain.service.gateway.QueryGateway;

public class UserService {

    private static final Logger log = LogManager.getLogger();

    private final QueryGateway queryGateway;
    private final CommandGateway commandGateway;

    public UserService(QueryGateway queryGateway, CommandGateway commandGateway) {
        this.queryGateway = queryGateway;
        this.commandGateway = commandGateway;
    }

    public void logMe() {
        log.info("injected {} {}", queryGateway.getClass(), commandGateway);
    }
}

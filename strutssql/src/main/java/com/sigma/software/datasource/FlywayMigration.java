package com.sigma.software.datasource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.sql.DataSource;

@ApplicationScoped
public class FlywayMigration {

    private static final Logger log = LogManager.getLogger();

    @Inject
    DataSource dataSource;

    /**
     * @see: https://flywaydb.org/documentation/maven/
     */
    @PostConstruct
    public void initFlyWay() {
        log.info("\n\n\n initializing.... \n\n");
        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.clean();
        // Needed if the database is not empty
        flyway.baseline();
        flyway.migrate();
    }
}

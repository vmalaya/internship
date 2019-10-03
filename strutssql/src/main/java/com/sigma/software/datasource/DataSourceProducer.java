package com.sigma.software.datasource;

import io.vavr.Lazy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;
import java.io.Serializable;

@ApplicationScoped
public class DataSourceProducer implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LogManager.getLogger();

    private final Lazy<DataSource> dataSource = Lazy.of(DataSourceFactory::create);

    @Produces
    public DataSource dataSource() {
        log.info("producing DS...");
        return dataSource.get();
    }
}

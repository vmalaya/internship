package com.sigma.software.datasource;

import io.vavr.control.Try;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.util.function.Function;

public class DataSourceFactory {

    private static final Function<Throwable, RuntimeException> reThrow = RuntimeException::new;
    private static final InitialContext initialContext = Try.of(InitialContext::new)
                                                            .getOrElseThrow(reThrow);

    private static final Function<String, Try<DataSource>> maybeDataSource = jndiName ->
            Try.of(() -> initialContext.lookup(jndiName)).map(o -> (DataSource) o);

    public static DataSource create() {
        String jndiName = "java:jboss/datasources/ExampleDS";
        // String jndiName = "java:jboss/datasources/MySqlDS";
        return maybeDataSource.apply(jndiName)
                              .getOrElseThrow(reThrow);
    }

    private DataSourceFactory() { }
}

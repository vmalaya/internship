package com.sigma.software.controller;

import com.opensymphony.xwork2.ActionSupport;
import com.sigma.software.datasource.DataSourceFactory;
import com.sigma.software.datasource.FlywayMigration;
import io.vavr.Lazy;
import org.apache.logging.log4j.LogManager;

import javax.inject.Inject;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SaveMessageAction extends ActionSupport {

    private static final long serialVersionUID = 1L;

    @Inject
    DataSource dataSource;

    @Inject
    FlywayMigration flywayMigration;

    private String message;

    // private DataSource datasource;

    public String save() throws SQLException, NamingException {
        LogManager.getLogger().info(flywayMigration);
        // Connection connection = dataSource.get().getConnection();
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT into messages(message) values(?)");
        preparedStatement.setString(1, message);
        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
        return SUCCESS;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

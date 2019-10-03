package com.sigma.software.controller;

import com.opensymphony.xwork2.ActionSupport;
import com.sigma.software.datasource.DataSourceFactory;
import com.sigma.software.datasource.FlywayMigration;
import io.vavr.Lazy;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewMessagesAction extends ActionSupport {

    private static final long serialVersionUID = 1L;
    private static final Lazy<DataSource> dataSource = Lazy.of(DataSourceFactory::create);

    private String messages = "";

    public String execute() throws SQLException {
        DataSource datasource = dataSource.get();
        Connection connection = datasource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT message FROM messages");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            String message = resultSet.getString("message");
            messages = messages + message + ";";
        }
        preparedStatement.close();
        connection.close();
        return SUCCESS;
    }

    public String getMessages() {
        return messages;
    }
}

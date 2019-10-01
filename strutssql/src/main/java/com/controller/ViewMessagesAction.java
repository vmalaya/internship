package com.controller;

import com.opensymphony.xwork2.ActionSupport;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewMessagesAction extends ActionSupport {
    private static final long serialVersionUID = 1L;

    private String messages = "";

    public String execute() throws NamingException, SQLException {
        Connection connection = getConnection("java:/MySqlDS");
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

    private Connection getConnection(String jndiName) throws NamingException, SQLException {
        InitialContext initialContext = new InitialContext();
        DataSource datasource = (DataSource) initialContext.lookup(jndiName);
        return datasource.getConnection();
    }

    public String getMessages() {
        return messages;
    }
}

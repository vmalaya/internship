package com.controller;

import com.opensymphony.xwork2.ActionSupport;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SaveMessageAction extends ActionSupport {
    private static final long serialVersionUID = 1L;

    private String message;

    public String save() throws SQLException, NamingException {
        Connection connection = getConnection("java:/MySqlDS");
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT into messages(message) values(?)");
        preparedStatement.setString(1, message);
        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
        return SUCCESS;
    }

    private Connection getConnection(String jndiName) throws NamingException, SQLException {
        InitialContext initialContext = new InitialContext();
        DataSource datasource = (DataSource) initialContext.lookup(jndiName);
        return datasource.getConnection();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

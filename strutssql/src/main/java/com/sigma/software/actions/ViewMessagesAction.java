package com.sigma.software.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.sigma.software.datasource.DataSourceFactory;
import io.vavr.Lazy;
import org.apache.struts2.convention.annotation.*;

import javax.enterprise.context.RequestScoped;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Results({
        @Result(name = "success", location = "index.jsp"),
})
@RequestScoped
@Namespace("/")
public class ViewMessagesAction extends ActionSupport {

    private static final long serialVersionUID = 1L;
    private static final Lazy<DataSource> dataSource = Lazy.of(DataSourceFactory::create);

    private String messages = "";

    @Action("/viewMessages")
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

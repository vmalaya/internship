package com.sigma.software.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.sigma.software.datasource.FlywayMigration;
import org.apache.logging.log4j.LogManager;
import org.apache.struts2.convention.annotation.*;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Results({
        @Result(name = "success", location = "viewMessages", type = "redirect"),
})
@RequestScoped
@Namespace("/")
public class SaveMessageAction extends ActionSupport {

    private static final long serialVersionUID = 1L;

    @Inject
    DataSource dataSource;

    @Inject
    private FlywayMigration flywayMigration;

    private String message;

    // private DataSource datasource;
    // private static final Lazy<DataSource> dataSource = Lazy.of(DataSourceFactory::create);

    @Action("/saveMessage")
    public String save() throws SQLException, NamingException {
        LogManager.getLogger().info(flywayMigration);
        Connection connection = dataSource.getConnection();
        // Connection connection = dataSource.getConnection();
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

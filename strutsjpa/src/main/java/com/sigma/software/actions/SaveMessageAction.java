package com.sigma.software.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.sigma.software.dao.MessageDao;
import com.sigma.software.datasource.FlywayMigration;
import com.sigma.software.model.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.naming.NamingException;

@Results({
        @Result(name = "success", location = "viewMessages", type = "redirect")
})
@RequestScoped
@Namespace("/")
public class SaveMessageAction extends ActionSupport {

    private static final long serialVersionUID = 1L;

    @Inject
    MessageDao messageDao;

    @Inject
    private FlywayMigration flywayMigration;

    private String message;

    @Action("/saveMessage")
    public String save() throws NamingException {
        LogManager.getLogger().info(flywayMigration);
        messageDao.save(new Message(message));
        return SUCCESS;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

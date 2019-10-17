package com.sigma.software.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.sigma.software.dao.MessageDao;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;

@Results({
        @Result(name = "success", location = "index.jsp")
})
@RequestScoped
@Namespace("/")
public class ViewMessagesAction extends ActionSupport {

    private static final long serialVersionUID = 1L;

    private List<String> messages;

    @Inject
    MessageDao messageDao;

    @Action("/viewMessages")
    public String execute() {
        messages = messageDao.findAllMessages();
        return SUCCESS;
    }

    public List<String> getMessages() {
        return messages;
    }
}

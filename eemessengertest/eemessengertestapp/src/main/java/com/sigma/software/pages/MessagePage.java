package com.sigma.software.pages;

import com.opensymphony.xwork2.ActionSupport;
import com.sigma.software.entities.Message;
import com.sigma.software.entities.User;
import com.sigma.software.repositories.MessageRepository;
import com.sigma.software.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import java.time.ZonedDateTime;
import java.util.List;

@Namespace("/send-message")
@Results({
        @Result(name = "input", location = "showMessages", type = "redirect"),
        @Result(name = "success", location = "send-message/messenger.jsp")
})
@RequestScoped
public class MessagePage extends ActionSupport {
    private static final long serialVersionUID = -6836296086197488826L;

    private String recipientUsername;
    private String body;
    private List<Message> messages;
    private User currentUser;

    @Inject
    private MessageRepository messageRepository;
    @Inject
    private UserRepository userRepository;

    @PostConstruct
    public void init(){
        currentUser = userRepository.getCurrentUser();
    }

    @Action("/page")
    public String open() {
        return SUCCESS;
    }

    @Action("/saveMessage")
    public String input() throws NamingException {
        LogManager.getLogger().info("\n\n\n ...saving message...\n\n\n");
        messageRepository.save(new Message(currentUser, userRepository.findUserByUsername(recipientUsername), body,
                                           ZonedDateTime.now()));
        return INPUT;
    }

    @Action("/showMessages")
    public String execute() {
        messages = messageRepository.findAllMessages();
        return SUCCESS;
    }

    @Action("/sign-out")
    public String signout() throws ServletException {
        ServletActionContext.getRequest().logout();
        return "index";
    }

    public String getRecipientUsername() {
        return recipientUsername;
    }

    public void setRecipientUsername(String recipientUsername) {
        this.recipientUsername = recipientUsername;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List getMessages() {
        return messages;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}

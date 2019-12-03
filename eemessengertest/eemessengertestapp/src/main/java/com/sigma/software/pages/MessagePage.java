package com.sigma.software.pages;

import com.opensymphony.xwork2.ActionSupport;
import com.sigma.software.entities.Message;
import com.sigma.software.entities.User;
import com.sigma.software.repositories.MessageRepository;
import com.sigma.software.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import javax.servlet.http.Cookie;
import java.time.ZonedDateTime;
import java.util.*;

@Namespace("/send-message")
@Results({
        @Result(name = "input", type = "redirect", location = "/send-message/page"),
        @Result(name = "view", type = "redirect", location = "/send-message/chat?username=${recipientUsername}"),
        @Result(name = "success", location = "send-message/messenger.jsp")
})
@RequestScoped
public class MessagePage extends ActionSupport {

    private static final Logger log = LogManager.getLogger();

    private static final long serialVersionUID = -6836296086197488826L;
    private static String errorMessage;

    private String recipientUsername;
    private String body;
    private List<Message> messages;
    private User currentUser;
    private List<User> contactsList;
    private List<Message> chat;

    @Inject
    private MessageRepository messageRepository;

    @Inject
    private UserRepository userRepository;

    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @PostConstruct
    public void init() {
        currentUser = userRepository.getCurrentUser();
        messages = messageRepository.findMessages(currentUser);
        contactsList = messageRepository.findContacts(currentUser.getUsername());
    }

    @Action("/page")
    public String open() {
        return SUCCESS;
    }

    @Action("/saveMessage")
    public String input() throws NamingException {
        if (recipientUsername.isEmpty()) errorMessage = "Please, input name of recipient";
        else {
            if (!userRepository.isUser(recipientUsername)) errorMessage = "There is no user with given username";
            else {
                errorMessage = null;
                LogManager.getLogger().info("\n\n\n ...saving message...\n\n\n");
                User recipient = userRepository.findUserByUsername(recipientUsername);
                messageRepository.save(new Message(currentUser, recipient, body,
                                                   ZonedDateTime.now()));
                ServletActionContext.getResponse().addCookie(new Cookie("recipient", recipientUsername));
            }
        }
        return "view";
    }

    @Action("/sign-out")
    public String signout() throws ServletException {
        ServletActionContext.getRequest().logout();
        errorMessage = null;
        return INPUT;
    }

    @Action("/chat")
    public String chat() {
        log.info("username: {}, current user: {}", username, currentUser);
        log.info("if: {}", Objects.isNull(username) || "".equals(username.trim()));
        chat = (Objects.isNull(username) || "".equals(username.trim()))
                ? new ArrayList<>() : messageRepository.findConversationBetween(currentUser.getUsername(), username);
        return SUCCESS;
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

    public List<User> getContactsList() {
        return contactsList;
    }

    public List<Message> getChat() {
        return chat;
    }

    public void setChat(List<Message> chat) {
        this.chat = chat;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

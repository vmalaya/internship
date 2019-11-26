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
import javax.servlet.http.Cookie;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Namespace("/send-message")
@Results({
        @Result(name = "input", type = "redirect", location = "/send-message/page"),
        @Result(name = "view", type = "redirect", location = "/send-message/chat"),
        @Result(name = "success", location = "send-message/messenger.jsp")
})
@RequestScoped
public class MessagePage extends ActionSupport {
    private static final long serialVersionUID = -6836296086197488826L;

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

    @PostConstruct
    public void init() {
        currentUser = userRepository.getCurrentUser();
        messages = messageRepository.findMessages(currentUser);
        contactsList = getContacts();
    }

    @Action("/page")
    public String open() {
        return SUCCESS;
    }

    @Action("/saveMessage")
    public String input() throws NamingException {
        LogManager.getLogger().info("\n\n\n ...saving message...\n\n\n");
        User recipient = userRepository.findUserByUsername(recipientUsername);
        messageRepository.save(new Message(currentUser, recipient, body,
                                           ZonedDateTime.now()));
        return "view";
    }

    @Action("/sign-out")
    public String signout() throws ServletException {
        ServletActionContext.getRequest().logout();
        return INPUT;
    }

    @Action("/chat")
    public String chat() {
        chat = new LinkedList<>();
        Cookie[] cookies = ServletActionContext.getRequest().getCookies();
        HashMap<String, String> hashMap = new HashMap<>();
        for (Cookie c : cookies) {
            hashMap.put(c.getName(), c.getValue());
        }
        String clickedUser = hashMap.get("clickedUser");
        LogManager.getLogger().info("\n\n\n " + clickedUser + "\n\n\n");
        for (Message message : messages) {
            if (message.getSender().getUsername().equals(currentUser.getUsername())) {
                if (message.getRecipient().getUsername().equals(clickedUser)) {
                    chat.add(message);
                }
            } else if (message.getSender().getUsername().equals(clickedUser)) {
                if (message.getRecipient().getUsername().equals(currentUser.getUsername())) {
                    chat.add(message);
                }
            }
        }
        LogManager.getLogger().info("\n\n\n " + chat + "\n\n\n");
        return SUCCESS;
    }

    public List<User> getContacts() {
        ArrayList contactsList = new ArrayList();
        for (Message message : messages) {
            if (message.getSender().getUsername().equals(currentUser.getUsername())) {
                if (!contactsList.contains(message.getRecipient())) contactsList.add(message.getRecipient());
            } else {
                if (!contactsList.contains(message.getSender())) contactsList.add(message.getSender());
            }
        }
        LogManager.getLogger().info("\n\n\n " + contactsList.toString() + " \n\n");
        return contactsList;
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
}

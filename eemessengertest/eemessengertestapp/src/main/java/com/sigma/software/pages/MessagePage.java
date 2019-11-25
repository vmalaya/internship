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
import java.util.LinkedList;
import java.util.List;

@Namespace("/send-message")
@Results({
        @Result(name = "input", type = "redirect", location = "/send-message/page"),
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
    // private User contact;
    private List<Message> chat;

    @Inject
    private MessageRepository messageRepository;
    @Inject
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        currentUser = userRepository.getCurrentUser();
        messages = messageRepository.findMessages(currentUser);
        contactsList = new LinkedList<>();
    }

    @Action("/page")
    public String open() {
        return getContacts();
    }

    @Action("/saveMessage")
    public String input() throws NamingException {
        LogManager.getLogger().info("\n\n\n ...saving message...\n\n\n");
        User recipient = userRepository.findUserByUsername(recipientUsername);
        messageRepository.save(new Message(currentUser, recipient, body,
                                           ZonedDateTime.now()));
        return INPUT;
    }

    @Action("/sign-out")
    public String signout() throws ServletException {
        ServletActionContext.getRequest().logout();
        return INPUT;
    }

    public String getContacts() {
        for (Message message : messages) {
            if (message.getSender().getUsername().equals(currentUser.getUsername())) {
                if (!contactsList.contains(message.getRecipient())) contactsList.add(message.getRecipient());
            } else {
                if (!contactsList.contains(message.getSender())) contactsList.add(message.getSender());
            }
        }
        LogManager.getLogger().info("\n\n\n " + contactsList.toString() + " \n\n");
        return SUCCESS;
    }

    @Action("/chat")
    public String chat() {
        User contact = userRepository.findUserByUsername(recipientUsername);
        for (Message message : messages) {
            if (message.getSender().getUsername().equals(currentUser.getUsername())) {
                if (message.getRecipient().getUsername().equals(contact.getUsername())) {
                    chat.add(message);
                }
            } else if (message.getSender().getUsername().equals(contact.getUsername())) {
                if (message.getRecipient().getUsername().equals(currentUser.getUsername())) {
                    chat.add(message);
                }
            }
        }
        LogManager.getLogger().info("\n\n\n " + chat + "\n\n\n");
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

    // public void setContact(User contact) {
    //     this.contact = contact;
    // }

    public List<Message> getChat() {
        return chat;
    }

    public void setChat(List<Message> chat) {
        this.chat = chat;
    }
}

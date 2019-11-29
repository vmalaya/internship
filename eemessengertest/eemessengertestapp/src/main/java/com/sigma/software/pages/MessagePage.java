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
import java.util.*;

@Namespace("/send-message")
@Results({
        @Result(name = "input", type = "redirect", location = "/send-message/page"),
        @Result(name = "view", type = "redirect", location = "/send-message/chat"),
        @Result(name = "success", location = "send-message/messenger.jsp")
})
@RequestScoped
public class MessagePage extends ActionSupport {
    private static final long serialVersionUID = -6836296086197488826L;
    private static Boolean savedMessage = false;
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
        if (recipientUsername.isEmpty()) errorMessage = "Please, input name of recipient";
        else {
            if(!userRepository.isUser(recipientUsername)) errorMessage = "There is no user with given username";
            else{
                errorMessage = null;
                LogManager.getLogger().info("\n\n\n ...saving message...\n\n\n");
                User recipient = userRepository.findUserByUsername(recipientUsername);
                messageRepository.save(new Message(currentUser, recipient, body,
                                                   ZonedDateTime.now()));
                ServletActionContext.getResponse().addCookie(new Cookie("recipient", recipientUsername));
                savedMessage = true;
            }
        }
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
        String relatedChatUser = findRelatedChatUser(savedMessage);
        savedMessage = false;
        for (Message message : messages) {
            if (message.getSender().getUsername().equals(currentUser.getUsername())) {
                if (message.getRecipient().getUsername().equals(relatedChatUser)) chat.add(message);
            } else if (message.getSender().getUsername().equals(relatedChatUser)) {
                if (message.getRecipient().getUsername().equals(currentUser.getUsername())) chat.add(message);
            }
        }
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
        return contactsList;
    }

    private String getCookieValue(String cookieName) {
        Cookie[] cookies = ServletActionContext.getRequest().getCookies();
        HashMap<String, String> hashMap = new HashMap<>();
        for (Cookie c : cookies) {
            hashMap.put(c.getName(), c.getValue());
        }
        LogManager.getLogger().info("\n\n\n" + "Cookies\n" + hashMap + "\n\n\n");
        return hashMap.get(cookieName);
    }

    private String findRelatedChatUser(Boolean savedMessage) {
        String cookieName;
        if (savedMessage.equals(true)) {
            cookieName = "recipient";
        } else cookieName = "clickedUser";
        String relatedUser = getCookieValue(cookieName);
        LogManager.getLogger().info("\n\n\n " + relatedUser + "\n\n\n");
        return relatedUser;
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

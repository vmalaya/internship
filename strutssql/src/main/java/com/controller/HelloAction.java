package com.controller;

import com.dao.GreetingDao;
import com.opensymphony.xwork2.ActionSupport;

import java.util.Objects;

import static com.dao.GreetingDao.count;

public class HelloAction extends ActionSupport {
    private static final long serialVersionUID = 1L;
    private static int counter;

    private String username;
    private String message;

    public String execute() {
        counter = count();
        message = "Hello," + username + "!\n" +
                "Total greetings:" + counter + "\n";
        GreetingDao.saveGreeting(this);
        return SUCCESS;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        String trimmed = username.trim();
        this.username = trimmed.replace(trimmed.charAt(0),
                                        Character.toUpperCase(trimmed.charAt(0)));
    }

    public int getCounter() {
        return counter;
    }

    public String getMessage() {
        return message;
    }

    public void validate() {
        if (Objects.isNull(username)) addFieldError("username", "Username may not be empty");
    }
}

package com.action;

import com.dao.UserDao;
import com.opensymphony.xwork2.ActionSupport;

public class HelloAction extends ActionSupport {
    private static final long serialVersionUID = 1L;

    private String username;
    private static int counter = 0;

    public String execute() {
        UserDao.save(username);
        HelloAction.counter = HelloAction.counter + 1;
        return SUCCESS;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCounter() {
        return counter;
    }
}

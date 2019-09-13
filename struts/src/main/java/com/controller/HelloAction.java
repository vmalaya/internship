package com.controller;

import com.opensymphony.xwork2.ActionSupport;

import static com.dao.GreetingDao.count;

public class HelloAction extends ActionSupport {
    private static final long serialVersionUID = 1L;
    private static int counter;

    private String username;

    public String execute() {
        counter = count();
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

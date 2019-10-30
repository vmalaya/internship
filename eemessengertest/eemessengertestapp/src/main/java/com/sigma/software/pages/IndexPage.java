package com.sigma.software.pages;

import com.opensymphony.xwork2.ActionSupport;
import com.sigma.software.entities.User;
import com.sigma.software.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.naming.NamingException;
import java.util.List;

@Results({
        @Result(name = "input", location = "showUsernames", type = "redirect"),
        @Result(name = "success", location = "index.jsp")
})
@RequestScoped
@Namespace("/")
public class IndexPage extends ActionSupport {

    @Inject
    private UserRepository userRepository;
    private String username;
    private List<String> usernames;

    @Action("/saveUsername")
    public String input() throws NamingException {
        LogManager.getLogger().info("\n\n\n ...saving message...\n\n\n");
        userRepository.save(new User(username));
        return INPUT;
    }

    @Action("/showUsernames")
    public String execute() {
        usernames = userRepository.findAllUsernames();
        return SUCCESS;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public List<String> getUsernames() {
        return usernames;
    }
}

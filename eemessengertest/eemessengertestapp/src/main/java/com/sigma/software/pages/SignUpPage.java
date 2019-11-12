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

@Results({
        @Result(name = "success", location = "index.jsp")
})
@RequestScoped
@Namespace("/")
public class SignUpPage extends ActionSupport {

    private static final long serialVersionUID = 4026441208456426629L;
    @Inject
    private UserRepository userRepository;
    private User userBean;

    @Action("/saveUsername")
    public String input() throws NamingException {
        LogManager.getLogger().info("\n\n\n ...saving user...\n\n\n");
        userRepository.save(userBean);
        return "send-message/messenger";
    }

    public User getUserBean() {
        return userBean;
    }

    public void setUserBean(User userBean) {
        this.userBean = userBean;
    }
}

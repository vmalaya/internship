package com.sigma.software.pages;

import com.opensymphony.xwork2.ActionSupport;
import com.sigma.software.entities.User;
import com.sigma.software.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.*;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.validation.Valid;

@Results({
        @Result(name = "success", location = "index.jsp"),
        @Result(name = "input", type = "redirect", location = "/send-message/page"),
        @Result(name = "errorValidation", location = "signin-error.jsp")
})
@ExceptionMappings(
        @ExceptionMapping(exception = "javax.validation.ConstraintViolationException", result = "errorValidation")
)
@RequestScoped
@Namespace("/sign-up")
public class SignUpPage extends ActionSupport {

    private static final long serialVersionUID = 4026441208456426629L;
    @Inject
    private UserRepository userRepository;
    @Valid
    private User userBean;
    private String errorMessage;

    @Action("/saveUsername")
    public String input() throws NamingException, ServletException {
        if (userRepository.isUser(userBean.getUsername())) {
            errorMessage = "There is such username. Please, input different one.";
            return SUCCESS;
        } else {
            LogManager.getLogger().info("\n\n\n ...saving user...\n\n\n");
            userRepository.save(userBean);
            ServletActionContext.getRequest().login(userBean.getUsername(), userBean.getPassword());
            return INPUT;
        }
    }

    public User getUserBean() {
        return userBean;
    }

    public void setUserBean(User userBean) {
        this.userBean = userBean;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

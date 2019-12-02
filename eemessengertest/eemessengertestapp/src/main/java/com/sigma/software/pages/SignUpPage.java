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
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Objects;

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
            HttpServletRequest request = ServletActionContext.getRequest();
            String remoteUser = request.getRemoteUser();
            if (Objects.nonNull(remoteUser)) request.logout();
            LogManager.getLogger().info("\n\n\n ...saving user...\n\n\n");
            format(userBean);
            userRepository.save(userBean);
            request.login(userBean.getUsername(), userBean.getPassword());
            return INPUT;
        }
    }

    private User format(User user){
        user.setUsername(user.getUsername().trim());
        user.setPassword(user.getPassword().trim());
        return user;
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

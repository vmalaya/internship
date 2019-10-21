package com.sigma.software.pages;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import javax.enterprise.context.ApplicationScoped;

@Results(
        @Result(name = "success", location = "index.jsp")
)
@ApplicationScoped
@Namespace("/")
public class IndexPage extends ActionSupport {

    private static int counter = 0;

    @Action("/hello")
    public String execute() {
        counter++;
        return SUCCESS;
    }

    public static int getCounter() {
        return counter;
    }
}

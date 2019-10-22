package com.sigma.software;

import org.apache.struts2.dispatcher.filter.StrutsPrepareAndExecuteFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

@WebFilter(
        urlPatterns = "/*",
        displayName = "JBoss EAP CDI Struts 2",
        initParams = {
                @WebInitParam(
                        description = "Set action suffix as *Page",
                        name = "struts.convention.action.suffix",
                        value = "Page"
                ),
                @WebInitParam(
                        description = "Set default package name as pages",
                        name = "struts.convention.package.locators",
                        value = "pages"
                )
        }
)
public class WebXml extends StrutsPrepareAndExecuteFilter { }

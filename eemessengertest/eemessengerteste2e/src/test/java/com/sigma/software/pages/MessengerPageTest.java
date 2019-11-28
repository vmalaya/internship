package com.sigma.software.pages;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.byValue;
import static com.codeborne.selenide.Selenide.*;

public class MessengerPageTest {
    private static final String url = String.format("http://%s:8080/eemessenger/",
                                                    System.getProperty("eemessenger.host", "127.0.0.1"));
    private static final String urlToMessenger = String.format("http://%s:8080/eemessenger/send-message/page",
                                                               System.getProperty("eemessenger.host", "127.0.0.1"));

    @Test
    void should_set_given_username_into_chat_page() {

        open(url);

        $(By.name("userBean.username")).setValue("tom");
        $(By.name("userBean.password")).setValue("supersecretpasswordfortom");
        $(byValue("Sign Up")).click();

        $(byText("tom")).should(Condition.exist);

        $(byValue("Sign Out")).click();
    }

    @Test
    void should_send_message() {
        open(url);
        $(By.name("userBean.username")).setValue("simba");
        $(By.name("userBean.password")).setValue("supersecretpasswordforsimba");
        $(byValue("Sign Up")).click();
        $(byValue("Sign Out")).click();

        $$("a").findBy(Condition.text(" Sign up")).click();

        $(By.name("userBean.username")).setValue("pumbaa");
        $(By.name("userBean.password")).setValue("supersecretpasswordforpumbaa");
        $(byValue("Sign Up")).click();

        $(By.name("recipientUsername")).setValue("pumbaa");
        $(By.name("body")).setValue("Apache Struts is a free, open-source, MVC framework.");
        $(byValue("Send")).click();

        $(byText("Apache Struts is a free, open-source, MVC framework.")).should(Condition.exist);

        $(By.name("recipientUsername")).setValue("simba");
        $(By.name("body")).setValue("Hi, simba!");
        $(byValue("Send")).click();

        $(byText("Hi, simba!")).should(Condition.exist);

        $(byValue("Sign Out")).click();
    }

    @Test
    void should_obtain_message() {
        open(urlToMessenger);

        $(By.name("j_username")).setValue("simba");
        $(By.name("j_password")).setValue("supersecretpasswordforsimba");
        $(byValue("Sign In")).click();

        $(byText("pumbaa")).should(Condition.exist);

        $(byText("pumbaa")).click();
        $(byText("Hi, simba!")).should(Condition.exist);

        $(byValue("Sign Out")).click();
    }
}

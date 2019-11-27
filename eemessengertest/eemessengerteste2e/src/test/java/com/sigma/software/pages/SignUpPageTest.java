package com.sigma.software.pages;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.byValue;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SignUpPageTest {
    private static final String url = String.format("http://%s:8080/eemessenger",
                                                    System.getProperty("eemessenger.host", "127.0.0.1"));

    @Test
    void should_sign_up() {
        open(url);

        $(By.name("userBean.username")).setValue("valentina");
        $(By.name("userBean.password")).setValue("supersecretpassword");
        $(byValue("Sign Up")).click();

        $(byValue("Send")).should(Condition.exist);
        $(byValue("Sign Out")).click();
    }
}

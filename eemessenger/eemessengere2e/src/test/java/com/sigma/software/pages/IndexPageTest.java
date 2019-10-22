package com.sigma.software.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.byValue;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class IndexPageTest {
    private static final String url = String.format("http://%s:8080/eemessenger",
                                                    System.getProperty("eemessenger.host", "127.0.0.1"));
    @Test
    void should_show_given_username() {
        open(url);

        $(By.name("username")).setValue("testuser");
        $(byValue("Press me to save username")).click();

        $(Selectors.withText("testuser")).should(Condition.exist);
    }
}

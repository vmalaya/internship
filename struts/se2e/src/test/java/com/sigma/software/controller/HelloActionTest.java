package com.sigma.software.controller;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class HelloActionTest {

    private static final String url = String.format("http://%s:8080/struts", System.getProperty("struts.host", "127.0.0.1"));

    @Test
    void home_page_should_should_be_loaded() {
        open(url);
        $(byValue("Hello")).exists();
    }

    @Test
    void should_greet_by_username() {
        open(url);

        $(By.name("username")).setValue("Valentyna");
        $(byValue("Hello")).click();

        $(byCssSelector("p")).getText().equalsIgnoreCase("Hello, Valentyna!");
    }

    @Test
    void should_display_total_greetings_counter() {
        open(url);

        $(By.name("username")).setValue("valentina");
        $(byValue("Hello")).click();
        $(withText("Go back")).click();

        $(By.name("username")).setValue("valentina.malaya");
        $(byValue("Hello")).click();

        $(Selectors.withText("Total greetings:"))
                .shouldHave(Condition.exactTextCaseSensitive("Total greetings: 3"));
    }

    @Test
    void should_not_greet_user_with_empty_username() {
        open(url);

        $(By.name("username")).setValue("");
        // url().equalsIgnoreCase("http://localhost:8080/struts/hello.action")
        $(Selectors.withText("Username may not be empty"))
                .exists();
    }

    @Test
    void should_capitalize_first_letter() {
        open(url);

        $(By.name("username")).setValue("valentyna.mala");
        $(byValue("Hello")).click();

        $$(byCssSelector("p")).filter(Condition.text("Hello"))
                              .first()
                              .should(Condition.text("Valentyna.mala!"));
    }

    @Test
    void should_trim_username() {
        open(url);

        $(By.name("username")).setValue("  valentyna.mala");
        $(byValue("Hello")).click();

        $$(byCssSelector("p")).filter(Condition.text("Hello"))
                              .first()
                              .shouldHave(Condition.exactText("Hello, Valentyna.mala!"));
    }
}

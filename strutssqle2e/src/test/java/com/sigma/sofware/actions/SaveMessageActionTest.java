package com.sigma.sofware.actions;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.byValue;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SaveMessageActionTest {
    private static final String url = String.format("http://%s:8080/strutssql", System.getProperty("strutssql.host", "127.0.0.1"));

    @Test
    void should_display_saved_messages() {
        open(url);

        $(By.name("message")).setValue("ole");
        $(byValue("Submit")).click();

        $(Selectors.withText("ole"))
                .should(Condition.exist);
    }

}

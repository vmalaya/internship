import com.codeborne.selenide.CollectionCondition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.textCaseSensitive;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class GoogleTest {
    @Test
    void should_find_and_open_page_then_open_file_and_assure_that_current_file_was_needed() {
        open("https://google.com");
        $(By.name("q")).setValue("daggerok selenide e2e tests").pressEnter();
        $$("#rso .g").shouldHave(CollectionCondition.sizeGreaterThan(2))
                     .get(1)
                     .click();
        $$(byText("README.md")).filterBy(cssClass("js-navigation-open")).first().click();
        $("#readme").shouldHave(textCaseSensitive("E2E testing"));
    }
}

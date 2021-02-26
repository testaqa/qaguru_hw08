package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import static helpers.AttachmentsHelper.*;

public class TestBase {
    @BeforeAll
    static void beforeAll() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));

        Configuration.startMaximized = true;
        Configuration.browser = System.getProperty("browser", "chrome");

        if (System.getProperty("selenoid_url") != null) {
            Configuration.remote =
                    "https://"
                    + System.getProperty("selenoid_credentials")
                    + "@"
                    + System.getProperty("selenoid_url") + ":4444/wd/hub";
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("enableVNC", true);
            capabilities.setCapability("enableVideo", true);
            Configuration.browserCapabilities = capabilities;
        }
    }

    @AfterEach
    public void afterEach() {
        attachScreenshot("Last screenshot");
        attachPageSource();
        attachAsText("Browser console logs", getConsoleLogs());
        attachVideo();
        Selenide.closeWebDriver();
    }
}

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
        Configuration.remote = "https://user1:1234@" + System.getProperty("selenoid_url") + ":4444/wd/hub";
        Configuration.browser = System.getProperty("browser", "chrome");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;
//        if(System.getProperty("remote_driver") != null) {
//            // config for Java + Selenide
//            DesiredCapabilities capabilities = new DesiredCapabilities();
//            capabilities.setCapability("enableVNC", true);
//            capabilities.setCapability("enableVideo", true);
//            Configuration.browserCapabilities = capabilities;
//            Configuration.remote = System.getProperty("remote_driver");
//
//            // config for Java + Selenium
////        DesiredCapabilities capabilities = new DesiredCapabilities();
////        capabilities.setCapability("browserName", "chrome");
////        capabilities.setCapability("browserVersion", "87.0");
////        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
////                "enableVNC", true,
////                "enableVideo", true
////        ));
////        RemoteWebDriver driver = new RemoteWebDriver(
////                URI.create("http://selenoid:4444/wd/hub").toURL(),
////                capabilities
////        );
//        }
    }

    @AfterEach
    public void afterEach() {
        attachScreenshot("Last screenshot");
        attachPageSource();
        attachAsText("Browser console logs", getConsoleLogs());
        attachVideo();
//        if(System.getProperty("video_storage") != null)
//            attachVideo();
        Selenide.closeWebDriver();
    }
}

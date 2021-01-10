package pages.ios;

import helpers.WaiterHelper;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;

public class WelcomePage {

    private final IOSDriver driver;
    private final WaiterHelper waiterHelper;

    public WelcomePage(IOSDriver driver) {
        this.driver = driver;
        waiterHelper = new WaiterHelper(driver);
    }

    public WelcomePage clickNext() {
        driver.findElementByXPath("//XCUIElementTypeButton[@name='Next']").click();
        return this;
    }

    public void waitForStep(String stepName) {
        waiterHelper.waitForElementPresentByLocator(By.id(stepName));
    }

    public void clickGetStarted() {
        driver.findElementByXPath("//XCUIElementTypeStaticText[@name='Get started']").click();
    }
}

package pages.ios;

import helpers.WaiterHelper;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import pages.WelcomePage;

public class WelcomePageIOSImpl implements WelcomePage {

    private final AppiumDriver driver;
    private final WaiterHelper waiterHelper;

    public WelcomePageIOSImpl(AppiumDriver driver) {
        this.driver = driver;
        waiterHelper = new WaiterHelper(driver);
    }

    public void clickNext() {
        driver.findElementByXPath("//XCUIElementTypeButton[@name='Next']").click();
    }

    @Override
    public void waitForStep(String stepName) {
        waiterHelper.waitForElementPresentByLocator(By.id(stepName));
    }

    @Override
    public void skipWelcomePage() {
        driver.findElementByXPath("//XCUIElementTypeButton[@name='Skip']").click();
    }

    public void clickGetStarted() {
        driver.findElementByXPath("//XCUIElementTypeStaticText[@name='Get started']").click();
    }
}

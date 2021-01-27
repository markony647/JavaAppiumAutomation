package pages.ios;

import helpers.WaiterHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import pages.WelcomePage;

public class WelcomePageIOSImpl implements WelcomePage {

    private final RemoteWebDriver driver;
    private final WaiterHelper waiterHelper;

    public WelcomePageIOSImpl(RemoteWebDriver driver) {
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

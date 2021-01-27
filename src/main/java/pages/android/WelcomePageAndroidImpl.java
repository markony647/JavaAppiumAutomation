package pages.android;

import helpers.WaiterHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import pages.WelcomePage;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class WelcomePageAndroidImpl implements WelcomePage {

    private final RemoteWebDriver driver;
    private final WaiterHelper waiterHelper;

    public WelcomePageAndroidImpl(RemoteWebDriver driver) {
        this.driver = driver;
        waiterHelper = new WaiterHelper(driver);
    }

    @Override
    public void skipWelcomePage() {
        WebElement skipButton = waiterHelper.waitForElementPresentByLocator(By.xpath("//*[contains(@text, 'SKIP')]"));
        skipButton.click();
    }

    @Override
    public void waitForStep(String step) {
        throw new NotImplementedException();
    }

    @Override
    public void clickNext() {
        throw new NotImplementedException();
    }

    @Override
    public void clickGetStarted() {
        throw new NotImplementedException();
    }
}

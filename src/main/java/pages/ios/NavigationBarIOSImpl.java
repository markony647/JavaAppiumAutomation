package pages.ios;

import helpers.WaiterHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import pages.NavigationBar;

public class NavigationBarIOSImpl implements NavigationBar {

    private final RemoteWebDriver driver;
    private final WaiterHelper waiterHelper;

    public NavigationBarIOSImpl(RemoteWebDriver driver) {
        this.driver = driver;
        waiterHelper = new WaiterHelper(driver);
    }

    @Override
    public void back() {
        waiterHelper.waitForElementPresentByLocator(By.xpath("//XCUIElementTypeButton[@name='Back']")).click();
    }

    @Override
    public void clickSearchIcon() {
        waiterHelper.waitForElementPresentByLocator
                (By.xpath("//XCUIElementTypeButton[@name='Search Wikipedia']")).click();
    }

    @Override
    public void returnToMainScreen() {
        waiterHelper.waitForElementPresentByLocator
                (By.xpath("//XCUIElementTypeButton[@name='Wikipedia, return to Explore']")).click();
    }

    public void clickSaved() {
        waiterHelper.waitForElementPresentByLocator(By.id("Saved")).click();
    }
}

package pages.android;

import helpers.WaiterHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import pages.NavigationBar;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class NavigationBarAndroidImpl implements NavigationBar {

    private final RemoteWebDriver driver;
    private final WaiterHelper waiterHelper;

    public NavigationBarAndroidImpl(RemoteWebDriver driver) {
        this.driver = driver;
        waiterHelper = new WaiterHelper(driver);
    }

    @Override
    public void back() {
        waiterHelper.waitForElementPresentByLocator(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"))
                .click();
    }

    @Override
    public void clickSearchIcon() {
        // Need to implement
        throw new NotImplementedException();
    }

    @Override
    public void returnToMainScreen() {
        // Need to implement
        throw new NotImplementedException();
    }
}

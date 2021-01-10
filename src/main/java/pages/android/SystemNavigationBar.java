package pages.android;

import helpers.WaiterHelper;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SystemNavigationBar {

    private final AppiumDriver driver;
    private final WaiterHelper waiterHelper;

    public SystemNavigationBar(AppiumDriver driver) {
        this.driver = driver;
        waiterHelper = new WaiterHelper(driver);
    }

    public void back() {
        waiterHelper.waitForElementPresentByLocator(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"))
                .click();
    }
}

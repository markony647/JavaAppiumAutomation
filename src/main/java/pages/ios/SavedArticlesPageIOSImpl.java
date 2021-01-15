package pages.ios;

import helpers.WaiterHelper;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import pages.ReadingListPage;
import pages.SavedArticlesPage;

public class SavedArticlesPageIOSImpl implements SavedArticlesPage {

    private final AppiumDriver driver;
    private final WaiterHelper waiterHelper;
    private final NavigationBarIOSImpl navigationBarIOS;

    public SavedArticlesPageIOSImpl(AppiumDriver driver) {
        this.driver = driver;
        waiterHelper = new WaiterHelper(driver);
        navigationBarIOS = new NavigationBarIOSImpl(driver);
    }

    public SavedArticlesPageIOSImpl clickSave() {
        waiterHelper.waitForElementPresentByLocator(By.xpath("//XCUIElementTypeButton[@name='Save for later']")).click();
        return this;
    }

    @Override
    public ReadingListPage visit() {
        navigationBarIOS.returnToMainScreen();
        navigationBarIOS.clickSaved();
        return new ReadingListPageIOSImpl(driver);
    }
}

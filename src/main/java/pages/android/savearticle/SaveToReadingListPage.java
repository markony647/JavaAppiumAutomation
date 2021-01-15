package pages.android.savearticle;

import helpers.WaiterHelper;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SaveToReadingListPage {

    private final AppiumDriver driver;
    private final WaiterHelper waiterHelper;

    public SaveToReadingListPage(AppiumDriver driver) {
        this.driver = driver;
        waiterHelper = new WaiterHelper(driver);
    }

    public void createNewList(String listName) {
        waiterHelper.waitForElementPresentByLocator(By.xpath("//*[@class='android.widget.ImageView']"))
                .click();
        waiterHelper.waitForElementWithExactTextPresent("Name of this list").sendKeys(listName);
        waiterHelper.waitForElementWithExactTextPresent("OK").click();
    }

    public void selectList(String listName) {
        waiterHelper.waitForElementWithExactTextPresent(listName).click();
    }
}

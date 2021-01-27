package pages.android.savearticle;

import helpers.WaiterHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SaveToReadingListPage {

    private final RemoteWebDriver driver;
    private final WaiterHelper waiterHelper;

    public SaveToReadingListPage(RemoteWebDriver driver) {
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

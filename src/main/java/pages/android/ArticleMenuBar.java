package pages.android;

import helpers.WaiterHelper;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class ArticleMenuBar {

    private final AppiumDriver driver;
    private final WaiterHelper waiterHelper;

    public ArticleMenuBar(AppiumDriver driver) {
        this.driver = driver;
        waiterHelper = new WaiterHelper(driver);
    }

    public ArticleMenuBar clickSave() {
        waiterHelper.waitForElementPresentByLocator(By.id("org.wikipedia:id/article_menu_bookmark"))
                .click();
        return this;
    }

    public SaveToReadingListPage clickAddToList() {
        waiterHelper.waitForElementPresentByLocator(By.id("org.wikipedia:id/snackbar_action"))
                .click();
        return new SaveToReadingListPage(this.driver);
    }

    public ReadingListPage goToViewList() {
        waiterHelper.waitForElementWithExactTextPresent("VIEW LIST").click();
        return new ReadingListPage(this.driver);
    }
}

package pages.android.savearticle;

import helpers.WaiterHelper;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import pages.ReadingListPage;
import pages.SavedArticlesPage;
import pages.android.ReadingListPageAndroidImpl;

public class ArticleMenuBarAndroidImpl implements SavedArticlesPage {

    private final RemoteWebDriver driver;
    private final WaiterHelper waiterHelper;

    public ArticleMenuBarAndroidImpl(RemoteWebDriver driver) {
        this.driver = driver;
        waiterHelper = new WaiterHelper(driver);
    }

    public ArticleMenuBarAndroidImpl clickSave() {
        waiterHelper.waitForElementPresentByLocator(By.id("org.wikipedia:id/article_menu_bookmark"))
                .click();
        return this;
    }

    public SaveToReadingListPage clickAddToList() {
        waiterHelper.waitForElementPresentByLocator(By.id("org.wikipedia:id/snackbar_action"))
                .click();
        return new SaveToReadingListPage(this.driver);
    }

    public ReadingListPage visit() {
        waiterHelper.waitForElementWithExactTextPresent("VIEW LIST").click();
        return new ReadingListPageAndroidImpl(this.driver);
    }
}

package pages.android;

import helpers.WaiterHelper;
import io.appium.java_client.AppiumDriver;

public class ArticlePage {

    private final AppiumDriver driver;
    private final WaiterHelper waiterHelper;

    public ArticlePage(AppiumDriver driver) {
        this.driver = driver;
        waiterHelper = new WaiterHelper(driver);
    }

    public void assertArticleHasTitle(String title) {
        waiterHelper.waitForElementWithExactTextPresent(title);
    }
}

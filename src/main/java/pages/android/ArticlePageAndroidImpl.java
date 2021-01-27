package pages.android;

import helpers.WaiterHelper;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import pages.ArticlePage;

public class ArticlePageAndroidImpl implements ArticlePage {

    private final RemoteWebDriver driver;
    private final WaiterHelper waiterHelper;
    private final NavigationBarAndroidImpl navigationBar;

    public ArticlePageAndroidImpl(RemoteWebDriver driver) {
        this.driver = driver;
        waiterHelper = new WaiterHelper(driver);
        this.navigationBar = new NavigationBarAndroidImpl(driver);
    }

    @Override
    public void assertArticleHasTitle(String title) {
        waiterHelper.waitForElementWithExactTextPresent(title);
    }

    @Override
    public void backToSearchResults() {
        navigationBar.back();
    }
}

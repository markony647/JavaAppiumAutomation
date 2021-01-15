package pages.android;

import helpers.WaiterHelper;
import io.appium.java_client.AppiumDriver;
import pages.ArticlePage;

public class ArticlePageAndroidImpl implements ArticlePage {

    private final AppiumDriver driver;
    private final WaiterHelper waiterHelper;
    private final NavigationBarAndroidImpl navigationBar;

    public ArticlePageAndroidImpl(AppiumDriver driver) {
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

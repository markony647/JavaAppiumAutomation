package pages.ios;

import helpers.WaiterHelper;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import pages.ArticlePage;

public class ArticlePageIOSImpl implements ArticlePage {
    private final AppiumDriver driver;
    private final WaiterHelper waiterHelper;
    private final NavigationBarIOSImpl navigationBar;

    public ArticlePageIOSImpl(AppiumDriver driver) {
        this.driver = driver;
        waiterHelper = new WaiterHelper(driver);
        this.navigationBar = new NavigationBarIOSImpl(driver);
    }

    @Override
    public void backToSearchResults() {
        navigationBar.clickSearchIcon();
    }

    @Override
    public void assertArticleHasTitle(String title) {
        waiterHelper.waitForElementPresentByLocator(By.id(title));
    }
}

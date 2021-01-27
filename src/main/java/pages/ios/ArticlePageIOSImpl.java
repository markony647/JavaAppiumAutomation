package pages.ios;

import helpers.WaiterHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import pages.ArticlePage;

public class ArticlePageIOSImpl implements ArticlePage {
    private final RemoteWebDriver driver;
    private final WaiterHelper waiterHelper;
    private final NavigationBarIOSImpl navigationBar;

    public ArticlePageIOSImpl(RemoteWebDriver driver) {
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

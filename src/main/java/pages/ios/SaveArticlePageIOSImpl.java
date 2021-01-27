package pages.ios;

import core.Platform;
import helpers.WaiterHelper;
import io.appium.java_client.AppiumDriver;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import pages.SaveArticlePage;

@Slf4j
public class SaveArticlePageIOSImpl implements SaveArticlePage {

    private RemoteWebDriver driver;
    private WaiterHelper waiterHelper;
    private SavedArticlesPageIOSImpl articleMenuBar;

    public SaveArticlePageIOSImpl(RemoteWebDriver driver) {
        this.driver = driver;
        this.waiterHelper = new WaiterHelper(driver);
        this.articleMenuBar = new SavedArticlesPageIOSImpl(driver);
    }

    @Override
    public void saveArticle(String folder) {
        log.info("Skipping folder name for platform {}", Platform.PLATFORM_IOS.toUpperCase());
        articleMenuBar.clickSave();
        closeSyncModalIfDisplayed();
    }

    @Override
    public void saveArticle() {

    }

    private void closeSyncModalIfDisplayed() {
        if (! driver.findElements(By.xpath("//XCUIElementTypeStaticText[@name='Log in to sync your saved articles']")).isEmpty()) {
            waiterHelper.waitForElementPresentByLocator(By.id("places auth close")).click();
        }
    }
}

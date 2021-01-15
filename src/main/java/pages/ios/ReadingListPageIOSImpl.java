package pages.ios;

import helpers.SwipeHelper;
import helpers.WaiterHelper;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.ReadingListPage;

import java.util.List;

public class ReadingListPageIOSImpl implements ReadingListPage {

    private AppiumDriver driver;
    private WaiterHelper waiterHelper;
    private SwipeHelper swipeHelper;

    public ReadingListPageIOSImpl(AppiumDriver driver) {
        this.driver = driver;
        this.swipeHelper = new SwipeHelper(driver);
        this.waiterHelper = new WaiterHelper(driver);
    }

    @Override
    public List<WebElement> findAllSavedArticles() {
        return driver.findElementsByXPath("//XCUIElementTypeCell");
    }

    @Override
    public void removeArticleWithSwipe(String articleTitle) {
        By article = By.xpath(String.format("//XCUIElementTypeStaticText[@name='%s']/..", articleTitle));
        swipeHelper.swipeElementLeft(article);
        waiterHelper.waitForElementPresentByLocator(By.id("swipe action delete")).click();
        waiterHelper.waitForElementInvisibility(article);
    }
}

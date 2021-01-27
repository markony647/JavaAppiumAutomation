package pages.ios;

import helpers.SwipeHelper;
import helpers.WaiterHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import pages.ReadingListPage;

import java.util.List;

public class ReadingListPageIOSImpl implements ReadingListPage {

    private RemoteWebDriver driver;
    private WaiterHelper waiterHelper;
    private SwipeHelper swipeHelper;

    public ReadingListPageIOSImpl(RemoteWebDriver driver) {
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

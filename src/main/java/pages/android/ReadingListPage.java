package pages.android;

import helpers.SwipeHelper;
import helpers.WaiterHelper;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ReadingListPage {

    private final AppiumDriver driver;
    private final WaiterHelper waiterHelper;
    private SwipeHelper swipeHelper;

    public ReadingListPage(AppiumDriver driver) {
        this.driver = driver;
        waiterHelper = new WaiterHelper(driver);
        swipeHelper = new SwipeHelper(driver);
    }

    public List<WebElement> findAllSavedArticles() {
        String xpath = "//*[@class='android.view.ViewGroup']/*[@class='android.widget.TextView'and @index='0']";
        List<WebElement> allElements = driver.findElementsByXPath(xpath);
        allElements.remove(0);
        return allElements;
    }

    public void removeArticleWithSwipe(String articleTitle) {
        swipeHelper.swipeElementLeft(By.xpath(String.format("//*[@text='%s']", articleTitle)));
        waiterHelper.waitForElementInvisibility(savedArticleWithTitleLocator(articleTitle));
    }

    private By savedArticleWithTitleLocator(String articleTitle) {
        return By.xpath(String.format("//*[@text='%s']", articleTitle));
    }
}

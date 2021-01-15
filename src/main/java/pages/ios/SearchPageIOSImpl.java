package pages.ios;

import helpers.WaiterHelper;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.SearchPage;

import java.util.List;

public class SearchPageIOSImpl implements SearchPage {

    private final AppiumDriver driver;
    private final WaiterHelper waiterHelper;

    private By searchResults = By.xpath("//*[@type='XCUIElementTypeCell']");

    public SearchPageIOSImpl(AppiumDriver driver) {
        this.driver = driver;
        this.waiterHelper = new WaiterHelper(driver);
    }

    @Override
    public WebElement findAndAssertSearchResultWithTitleAndDescription(String title, String description) {
        return null;
    }

    @Override
    public SearchPage performSearch(String queryToSearch) {
        waiterHelper.waitForElementPresentByLocator(By.id("Search Wikipedia")).click();
        waiterHelper.waitForElementPresentByLocator(By.xpath("//*[@type='XCUIElementTypeSearchField']")).sendKeys(queryToSearch);
        return this;
    }

    @Override
    public List<WebElement> getAllSearchResultTitles() {
        waiterHelper.waitForElementPresentByLocator(searchResults);
        return driver.findElements(searchResults);
    }

    @Override
    public String selectFirstSearchResult() {
        return selectNthSearchResult(0);
    }


    @Override
    public void cancelSearch() {

    }

    @Override
    public WebElement waitForSearchResultWithExactText(String textInSearchResult) {
        return null;
    }

    @Override
    public String selectNthSearchResult(int resultIndex) {
        WebElement result = getAllSearchResultTitles().get(resultIndex);
        String res = result.findElement(By.xpath("//*[@type='XCUIElementTypeStaticText']")).getAttribute("value");
        result.click();
        return res;
    }
}

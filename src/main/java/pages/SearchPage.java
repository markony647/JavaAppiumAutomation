package pages;

import helpers.WaiterHelper;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchPage {

    private final AppiumDriver driver;
    private final WaiterHelper waiterHelper;

    private By searchFieldTextLocator = By.id("org.wikipedia:id/search_src_text");
    private By searchResult = By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']");

    public SearchPage(AppiumDriver driver) {
        this.driver = driver;
        waiterHelper = new WaiterHelper(driver);
    }

    public SearchPage performSearch(String queryToSearch) {
        waiterHelper.waitForElementWithExactTextPresent("Search Wikipedia").click();
        waiterHelper.waitForElementPresentByLocator(searchFieldTextLocator).sendKeys(queryToSearch);
        return this;
    }

    public List<WebElement> getAllSearchResults() {
        waiterHelper.waitForElementPresentByLocator(searchResult);
        return driver.findElements(searchResult);
    }

    public String selectFirstSearchResult() {
        return selectNthSearchResult(0);
    }

    public void cancelSearch() {
        waiterHelper.waitForElementPresentByLocator(By.id("org.wikipedia:id/search_close_btn")).click();
    }

    public WebElement waitForSearchResultWithExactText(String textInSearchResult) {
        return waiterHelper.waitForElementWithExactTextPresent(textInSearchResult);
    }

    public void waitForSearchResultInvisibility() {
        waiterHelper.waitForElementInvisibility(searchResult);
    }

    public WebElement searchField() {
        return waiterHelper.waitForElementWithExactTextPresent("Search Wikipedia");
    }

    public String selectNthSearchResult(int resultIndex) {
        WebElement result = getAllSearchResults().get(resultIndex);
        String res = result.getText();
        result.click();
        return res;
    }
}

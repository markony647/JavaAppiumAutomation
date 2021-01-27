package pages.ios;

import helpers.WaiterHelper;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import pages.SearchPage;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class SearchPageIOSImpl implements SearchPage {

    private final RemoteWebDriver driver;
    private final WaiterHelper waiterHelper;
    private final static String templateMark = "{TEMPLATE}";

    private By searchResults = By.xpath("//*[@type='XCUIElementTypeCell']");
    private By text = By.xpath("//XCUIElementTypeStaticText");
    private String searchResultWithSpecificTextTemplate = "//*[@type='XCUIElementTypeCell']//*[@type= 'XCUIElementTypeStaticText' and @value='{TEMPLATE}']";

    public SearchPageIOSImpl(RemoteWebDriver driver) {
        this.driver = driver;
        this.waiterHelper = new WaiterHelper(driver);
    }

    @Override
    public WebElement findAndAssertSearchResultWithTitleAndDescription(String title, String description) {
        By titleElement = By.xpath(searchResultWithSpecificTextTemplate.replace(templateMark, title));
        By descriptionElement = By.xpath(searchResultWithSpecificTextTemplate.replace(templateMark, description));
        waiterHelper.waitForElementPresentByLocator(titleElement);
        waiterHelper.waitForElementPresentByLocator(descriptionElement);

        List<WebElement> allSearchResults = driver.findElements(searchResults);
        List<WebElement> searchResults = allSearchResults
                .stream()
                .filter(res -> res.findElement(text).getAttribute("value").equals(title))
                .collect(toList());
        String errorMessage = String.format("Failed to find single search result with title %s and description %s" +
                "\nNumber of search results that match search criteria %d", title, description, searchResults.size());
        Assert.assertEquals(errorMessage, 1, searchResults.size());
        WebElement result = searchResults.get(0);
        Assert.assertEquals(description, result.findElement(descriptionElement).getAttribute("value"));
        return result;
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
        throw new NotImplementedException();
    }

    @Override
    public WebElement waitForSearchResultWithExactText(String textInSearchResult) {
        throw new NotImplementedException();
    }

    @Override
    public String selectNthSearchResult(int resultIndex) {
        WebElement result = getAllSearchResultTitles().get(resultIndex);
        String res = result.findElement(By.xpath("//*[@type='XCUIElementTypeStaticText']")).getAttribute("value");
        result.click();
        return res;
    }

    @Override
    public void waitForSearchResultInvisibility() {
        throw new NotImplementedException();
    }

    @Override
    public WebElement searchField() {
        throw new NotImplementedException();
    }
}

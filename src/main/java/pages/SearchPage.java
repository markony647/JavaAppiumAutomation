package pages;

import helpers.WaiterHelper;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.junit.Assert;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class SearchPage {

    private final static String templateMark = "{TEMPLATE}";

    private final AppiumDriver driver;
    private final WaiterHelper waiterHelper;

    private String searchResultTitlesXpath = "//*[@resource-id='org.wikipedia:id/page_list_item_title']";
    private String searchResultDescriptionsXpath = "//*[@resource-id='org.wikipedia:id/page_list_item_description']";

    private String searchResultTitleWithSpecificTextTemplate = "//*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='{TEMPLATE}']";
    private String searchResultDescriptionWithSpecificTextTemplate = "//*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='{TEMPLATE}']";

    private By searchFieldTextLocator = By.id("org.wikipedia:id/search_src_text");
    private By searchResultTitles = By.xpath(searchResultTitlesXpath);
    private By searchResultDescriptions = By.xpath(searchResultDescriptionsXpath);

    public SearchPage(AppiumDriver driver) {
        this.driver = driver;
        waiterHelper = new WaiterHelper(driver);
    }

    public WebElement findAndAssertSearchResultWithTitleAndDescription(String title, String description) {
        By titleElement = By.xpath(searchResultTitleWithSpecificTextTemplate.replace(templateMark, title));
        By descriptionElement = By.xpath(searchResultDescriptionWithSpecificTextTemplate.replace(templateMark, description));
        waiterHelper.waitForElementPresentByLocator(titleElement);
        waiterHelper.waitForElementPresentByLocator(descriptionElement);

        List<WebElement> allSearchResults = searchResults();
        List<WebElement> searchResults = allSearchResults.stream()
                .filter(res -> res.findElement(searchResultTitles).getText().equals(title)
                        && res.findElement(searchResultDescriptions).getText().equals(description))
                .collect(toList());
        String errorMessage = String.format("Failed to find single search result with title %s and description %s" +
                "\nNumber of search results that match search criteria %d", title, description, searchResults.size());
        Assert.assertEquals(errorMessage, 1, searchResults.size());
        return searchResults.get(0);
    }

    public SearchPage performSearch(String queryToSearch) {
        waiterHelper.waitForElementWithExactTextPresent("Search Wikipedia").click();
        waiterHelper.waitForElementPresentByLocator(searchFieldTextLocator).sendKeys(queryToSearch);
        return this;
    }

    public List<WebElement> getAllSearchResultTitles() {
        waiterHelper.waitForElementPresentByLocator(searchResultTitles);
        return driver.findElements(searchResultTitles);
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
        waiterHelper.waitForElementInvisibility(searchResultTitles);
    }

    public WebElement searchField() {
        return waiterHelper.waitForElementWithExactTextPresent("Search Wikipedia");
    }

    public String selectNthSearchResult(int resultIndex) {
        WebElement result = getAllSearchResultTitles().get(resultIndex);
        String res = result.getText();
        result.click();
        return res;
    }

    private List<WebElement> searchResults() {
        waiterHelper.waitForElementPresentByLocator(searchResultTitles);
        return driver.findElementById("org.wikipedia:id/search_results_list").findElements(By.className("android.view.ViewGroup"));
    }
}

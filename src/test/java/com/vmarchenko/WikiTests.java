package com.vmarchenko;

import io.appium.java_client.android.AndroidElement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.DeviceRotation;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;

import java.util.List;

public class WikiTests extends BaseTest {

    private By searchFieldTextLocator = By.id("org.wikipedia:id/search_src_text");
    private By searchResult = By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']");
    private By endOfArticle = By.xpath("//*[@text='View article in browser']");

    @Before
    public void navigateMainScreen() {
        makeDefaultOrientation();
        skipTutorial();
    }

    @Test
    public void testCanSearch() {
        performSearch("Java");
        waitForElementWithExactTextPresent("Object-oriented programming language");
    }

    @Test
    public void testCanCancelSearch() {
        performSearch("Python");
        List<WebElement> foundResults = getAllSearchResults();
        Assert.assertTrue(foundResults.size() > 1);

        cancelSearch();
        waitForElementNotPresent(searchResult);
    }

    @Test
    public void testArticleTileShouldMatchSearchQuery() {
        performSearch("Java");
        waitForElementWithTextPresent("Object-oriented programming language").click();
        WebElement articleTitle = waitForElementWithTextPresent("(programming language)");
        Assert.assertEquals("Java (programming language)", articleTitle.getText());
    }

    @Test
    public void testSearchFieldContainsCorrectText() {
        waitForElementWithExactTextPresent("Search Wikipedia").click();
        assertElementHasText(searchFieldTextLocator, "Search Wikipedia", "Search field text mismatch");
    }

    @Test
    public void testEachSearchResultContainsSearchQuery() {
        String searchQuery = "Java";

        performSearch(searchQuery);
        List<WebElement> searchResults = getAllSearchResults();
        searchResults.forEach(res -> Assert.assertTrue(res.getText().contains(searchQuery)));
    }

    @Test
    public void testSwipeArticle() {
        performSearch("Appium");
        getAllSearchResults().get(0).click();
        swipeUpForElement(endOfArticle);
    }

    @Test
    public void testUserCanManageArticlesInCreatedList() {
        String listName = "chemistry";

        performSearch("Selenium");

        String firstResult = selectNthResult(0);

        clickSave();

        clickAddToList();

        createNewList(listName);

        back();

        String secondResult = selectNthResult(1);

        clickSave();

        clickAddToList();

        selectList(listName);

        goToViewList();

        List<WebElement> allSavedArticles = findAllSavedArticles();
        Assert.assertTrue(allSavedArticles.size() == 2);

        removeArticleWithSwipe(secondResult);

        allSavedArticles = findAllSavedArticles();
        Assert.assertTrue(allSavedArticles.size() == 1);
        WebElement singleArticle = allSavedArticles.get(0);
        Assert.assertTrue(singleArticle.getText().equals(firstResult));

        singleArticle.click();

        WebElement currentArticleTitle = waitForElementWithExactTextPresent(firstResult);
        Assert.assertTrue(currentArticleTitle.getText().equals(firstResult));
    }

    @Test
    public void testTitleElementPresentInArticle() {
        performSearch("Selenium");

        String articleTitle = selectNthResult(2);
        assertArticleHasTitle(articleTitle);
    }

    @Test
    public void testTitleRemainsUnchangedWhenChangingDeviceOrientation() {
        performSearch("Java");
        waitForElementWithTextPresent("Object-oriented programming language").click();
        WebElement articleTitle = waitForElementWithTextPresent("(programming language)");
        Assert.assertEquals("Java (programming language)", articleTitle.getText());

        driver.rotate(ScreenOrientation.LANDSCAPE);

        articleTitle = waitForElementWithTextPresent("(programming language)");
        Assert.assertEquals("Java (programming language)", articleTitle.getText());
    }

    private void assertArticleHasTitle(String articleTitle) {
        List<AndroidElement> title = driver.findElementsByXPath(String.format("//*[@class='android.view.View' and @text='%s']", articleTitle));
        Assert.assertFalse(String.format("Title %s was not found", articleTitle), title.isEmpty());
        Assert.assertEquals("It seems the locator is broken, more than 1 title found", 1, title.size());
    }

    private By savedArticleWithTitleLocator(String articleTitle) {
        return By.xpath(String.format("//*[@text='%s']", articleTitle));
    }

    private List<WebElement> findAllSavedArticles() {
        String xpath = "//*[@class='android.view.ViewGroup']/*[@class='android.widget.TextView'and @index='0']";
        List<WebElement> allElements = driver.findElementsByXPath(xpath);
        allElements.remove(0);
        return allElements;
    }

    private void goToViewList() {
        waitForElementWithExactTextPresent("VIEW LIST").click();
    }

    private void removeArticleWithSwipe(String articleTitle) {
        swipeElementLeft(By.xpath(String.format("//*[@text='%s']", articleTitle)));
        waitForElementNotPresent(savedArticleWithTitleLocator(articleTitle));
    }

    private void selectList(String listName) {
        waitForElementWithExactTextPresent(listName).click();
    }

    private String selectNthResult(int resultIndex) {
        WebElement result = getAllSearchResults().get(resultIndex);
        String res = result.getText();
        result.click();
        return res;
    }

    private void back() {
        waitForElementPresentByLocator(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"))
            .click();
    }

    private void createNewList(String listName) {
        waitForElementPresentByLocator(By.xpath("//*[@class='android.widget.ImageView']"))
                .click();
        waitForElementWithExactTextPresent("Name of this list").sendKeys(listName);
        waitForElementWithExactTextPresent("OK").click();
    }

    private void clickAddToList() {
        waitForElementPresentByLocator(By.id("org.wikipedia:id/snackbar_action"))
                .click();
    }

    private void clickSave() {
        waitForElementPresentByLocator(By.id("org.wikipedia:id/article_menu_bookmark"))
                .click();
    }

    private List<WebElement> getAllSearchResults() {
        waitForElementPresentByLocator(searchResult);
        return driver.findElements(searchResult);
    }

    private void skipTutorial() {
        WebElement skipButton = waitForElementPresentByLocator(By.xpath("//*[contains(@text, 'SKIP')]"));
        skipButton.click();
    }
}

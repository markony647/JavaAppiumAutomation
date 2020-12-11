package com.vmarchenko;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class FirstTest {

    private AppiumDriver driver;
    private WebDriverWait wait;
    private final static int timeout = 5;

    private By searchFieldTextLocator = By.id("org.wikipedia:id/search_src_text");
    private By searchResult = By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']");

    @Before
    public void setUp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", new File("apks/org.wikipedia.apk").getAbsolutePath());

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        wait = new WebDriverWait(driver, timeout);

        skipTutorial();
    }

    @Test
    public void testCanSearch() {
        performSearch("Java");
        waitForElementWithTextPresent("Object-oriented programming language");
    }

    @Test
    public void testCanCancelSearch() {
        performSearch("Python");
        List<WebElement> foundResults = getAllSearchResults();
        Assert.assertTrue(foundResults.size() > 1);

        cancelSearch();
        waitForElementNotPresent(searchResult);
    }

    private List<WebElement> getAllSearchResults() {
        waitForElementPresentByLocator(searchResult);
        return driver.findElements(searchResult);
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
        waitForElementWithTextPresent("Search Wikipedia").click();
        assertElementHasText(searchFieldTextLocator, "Search Wikipedia", "Search field text mismatch");
    }

    @Test
    public void testEachSearchResultContainsSearchQuery() {
        String searchQuery = "Java";

        performSearch(searchQuery);
        List<WebElement> searchResults = getAllSearchResults();
        searchResults.forEach(res -> Assert.assertTrue(res.getText().contains(searchQuery)));
    }

    private void assertElementHasText(By by, String expectedText, String erroMessage) {
        wait.withMessage(erroMessage);
        wait.until(ExpectedConditions.textToBe(by, expectedText));
    }

    private void cancelSearch() {
        waitForElementPresentByLocator(By.id("org.wikipedia:id/search_close_btn")).click();
    }

    private void performSearch(String queryToSearch) {
        waitForElementWithTextPresent("Search Wikipedia").click();
        waitForElementPresentByLocator(searchFieldTextLocator).sendKeys(queryToSearch);
    }

    private WebElement waitForElementPresentByLocator(By by) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementWithTextPresent(String text) {
        return waitForElementPresentByLocator(By.xpath(String.format("//*[contains(@text, '%s')]", text)));
    }

    private boolean waitForElementNotPresent(By by) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    private void skipTutorial() {
        WebElement skipButton = waitForElementPresentByLocator(By.xpath("//*[contains(@text, 'SKIP')]"));
        skipButton.click();
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}

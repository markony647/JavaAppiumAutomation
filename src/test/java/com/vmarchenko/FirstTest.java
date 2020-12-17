package com.vmarchenko;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

import static io.appium.java_client.touch.offset.PointOption.point;

public class FirstTest {

    private AppiumDriver driver;
    private WebDriverWait wait;
    private final static int timeout = 5;
    private final static int minWaitingTimeMilliseconds = 500;

    private Dimension deviceResolution;

    private By searchFieldTextLocator = By.id("org.wikipedia:id/search_src_text");
    private By searchResult = By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']");
    private By endOfArticle = By.xpath("//*[@text='View article in browser']");

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
        deviceResolution = driver.manage().window().getSize();
        wait = new WebDriverWait(driver, timeout);

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

    private void swipeElementLeft(By element) {
        WebElement elementToSwipe = waitForElementPresentByLocator(element);

        int left_x = elementToSwipe.getLocation().getX();
        int right_x = left_x + elementToSwipe.getSize().getWidth();

        int upper_y = elementToSwipe.getLocation().getY();
        int lower_y = upper_y + elementToSwipe.getSize().getHeight();

        int middle_y = (upper_y + lower_y) / 2;

        touchAction().press(point(right_x, middle_y))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(minWaitingTimeMilliseconds)))
                .moveTo(point(left_x, middle_y))
                .release().perform();
    }

    private TouchAction touchAction() {
        return new TouchAction(driver);
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

    private void swipeUpForElement(By by) {
        int maxSwipeCount = 20;
        int swipeCount = 0;
        while (driver.findElements(by).isEmpty()) {
            if (swipeCount >= maxSwipeCount) {
                waitForElementPresentByLocator(by);
                return;
            }
            swipeUp();
            swipeCount++;
        }
    }

    private void swipeUp() {
        int centerOfDeviceBy_X_Axis = deviceResolution.width / 2;
        int startBy_Y_Axis = (int) (deviceResolution.height * 0.8);
        int endBy_Y_Axis = (int) (deviceResolution.height * 0.2);

        PointOption start = point(centerOfDeviceBy_X_Axis, startBy_Y_Axis);
        PointOption end = point(centerOfDeviceBy_X_Axis, endBy_Y_Axis);

        touchAction().press(start)
                .waitAction()
                .moveTo(end)
                .release()
                .perform();
    }

    private List<WebElement> getAllSearchResults() {
        waitForElementPresentByLocator(searchResult);
        return driver.findElements(searchResult);
    }

    private void assertElementHasText(By by, String expectedText, String erroMessage) {
        wait.withMessage(erroMessage);
        wait.until(ExpectedConditions.textToBe(by, expectedText));
    }

    private void cancelSearch() {
        waitForElementPresentByLocator(By.id("org.wikipedia:id/search_close_btn")).click();
    }

    private void performSearch(String queryToSearch) {
        waitForElementWithExactTextPresent("Search Wikipedia").click();
        waitForElementPresentByLocator(searchFieldTextLocator).sendKeys(queryToSearch);
    }

    private WebElement waitForElementPresentByLocator(By by) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementWithTextPresent(String text) {
        return waitForElementPresentByLocator(By.xpath(String.format("//*[contains(@text, '%s')]", text)));
    }

    private WebElement waitForElementWithExactTextPresent(String text) {
        return waitForElementPresentByLocator(By.xpath(String.format("//*[@text='%s']", text)));
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

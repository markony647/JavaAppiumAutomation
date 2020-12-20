package com.vmarchenko;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static io.appium.java_client.touch.offset.PointOption.point;

public class BaseTest {

    protected AppiumDriver driver;
    protected WebDriverWait wait;
    protected final static int timeout = 5;
    protected final static int minWaitingTimeMilliseconds = 500;

    private final static ScreenOrientation screenDefaultOrientation = ScreenOrientation.PORTRAIT;

    protected Dimension deviceResolution;

    private By searchFieldTextLocator = By.id("org.wikipedia:id/search_src_text");

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
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    protected void swipeUp() {
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

    protected void makeDefaultOrientation() {
        if (! driver.getOrientation().value().equals(screenDefaultOrientation.value())) {
            driver.rotate(screenDefaultOrientation);
        }
    }

    protected void performSearch(String queryToSearch) {
        waitForElementWithExactTextPresent("Search Wikipedia").click();
        waitForElementPresentByLocator(searchFieldTextLocator).sendKeys(queryToSearch);
    }

    protected WebElement waitForElementPresentByLocator(By by) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    protected WebElement waitForElementWithTextPresent(String text) {
        return waitForElementPresentByLocator(By.xpath(String.format("//*[contains(@text, '%s')]", text)));
    }

    protected WebElement waitForElementWithExactTextPresent(String text) {
        return waitForElementPresentByLocator(By.xpath(String.format("//*[@text='%s']", text)));
    }

    protected boolean waitForElementNotPresent(By by) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    protected void assertElementHasText(By by, String expectedText, String errorMessage) {
        wait.withMessage(errorMessage);
        wait.until(ExpectedConditions.textToBe(by, expectedText));
    }

    protected void cancelSearch() {
        waitForElementPresentByLocator(By.id("org.wikipedia:id/search_close_btn")).click();
    }

    protected void swipeUpForElement(By by) {
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

    protected void swipeElementLeft(By element) {
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
}

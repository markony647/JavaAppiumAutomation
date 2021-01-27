package helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaiterHelper {

    private WebDriverWait wait;

    private final static int timeout = 5;

    public WaiterHelper(RemoteWebDriver driver) {
        this.wait = new WebDriverWait(driver, timeout);
    }

    public WebElement waitForElementPresentByLocator(By by) {
        return waitFor(ExpectedConditions.presenceOfElementLocated(by));
    }

    public WebElement waitForElementWithExactTextPresent(String text) {
        return waitForElementPresentByLocator(By.xpath(String.format("//*[@text='%s']", text)));
    }

    public void waitForElementInvisibility(By by) {
        waitFor(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    private <T> T waitFor(ExpectedCondition<T> condition) {
        return wait.until(condition);
    }
}

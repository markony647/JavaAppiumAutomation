package pages.android;

import helpers.WaiterHelper;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TutorialPage {

    private final AppiumDriver driver;
    private final WaiterHelper waiterHelper;

    public TutorialPage(AppiumDriver driver) {
        this.driver = driver;
        waiterHelper = new WaiterHelper(driver);
    }

    public void skipTutorial() {
        WebElement skipButton = waiterHelper.waitForElementPresentByLocator(By.xpath("//*[contains(@text, 'SKIP')]"));
        skipButton.click();
    }
}

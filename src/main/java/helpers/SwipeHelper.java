package helpers;

import core.Platform;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import java.time.Duration;

import static io.appium.java_client.touch.offset.PointOption.point;

public class SwipeHelper {

    private final AppiumDriver driver;
    private final WaiterHelper waiterHelper;

    private static final int maxSwipeCount = 20;
    private final static int minWaitingTimeMilliseconds = 500;
    private Dimension deviceResolution;

    public SwipeHelper(AppiumDriver driver) {
        this.driver = driver;
        waiterHelper = new WaiterHelper(driver);
        deviceResolution = driver.manage().window().getSize();
    }

    public void swipeUpForElement(By by) {
        int swipeCount = 0;
        while (driver.findElements(by).isEmpty()) {
            if (swipeCount >= maxSwipeCount) {
                waiterHelper.waitForElementPresentByLocator(by);
                return;
            }
            swipeUp();
            swipeCount++;
        }
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

    public void swipeElementLeft(By element) {
        WebElement elementToSwipe = waiterHelper.waitForElementPresentByLocator(element);

        int left_x = elementToSwipe.getLocation().getX();
        int right_x = left_x + elementToSwipe.getSize().getWidth();

        int upper_y = elementToSwipe.getLocation().getY();
        int lower_y = upper_y + elementToSwipe.getSize().getHeight();

        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = touchAction();

        action.press(point(right_x, middle_y))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(minWaitingTimeMilliseconds)));

        if (Platform.getInstance().isAndroid()) {
            action.moveTo(point(left_x, middle_y));
        } else {
            int offset_x = (-1) * (elementToSwipe.getSize().getWidth());
            action.moveTo(point(offset_x, 0));
        }
        action.release();
        action.perform();
    }

    private TouchAction touchAction() {
        return new TouchAction(driver);
    }
}

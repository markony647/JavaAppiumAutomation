package helpers;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.ScreenOrientation;

public class DeviceRotationHelper {

    private final AppiumDriver driver;
    private final static ScreenOrientation screenDefaultOrientation = ScreenOrientation.PORTRAIT;

    public DeviceRotationHelper(AppiumDriver driver) {
        this.driver = driver;
    }

    public void makeDefaultOrientation() {
        if (! driver.getOrientation().value().equals(screenDefaultOrientation.value())) {
            driver.rotate(screenDefaultOrientation);
        }
    }

    public void rotate(ScreenOrientation screenOrientation) {
        driver.rotate(screenOrientation);
    }
}

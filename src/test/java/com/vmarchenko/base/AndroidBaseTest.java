package com.vmarchenko.base;

import helpers.DeviceRotationHelper;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.remote.DesiredCapabilities;
import pages.android.TutorialPage;

import java.net.MalformedURLException;
import java.net.URL;

public class AndroidBaseTest extends BaseTest {

    protected AppiumDriver driver;

    private TutorialPage tutorialPage;
    private DeviceRotationHelper rotationHelper;

    @Before
    public void setUp() throws MalformedURLException {
        DesiredCapabilities capabilities = getDesiredCapabilitiesForPlatform();

        driver = new AndroidDriver(new URL(appiumLocalUrl), capabilities);
        tutorialPage = new TutorialPage(driver);
        rotationHelper = new DeviceRotationHelper(driver);
        navigateMainScreen();
    }

    private void navigateMainScreen() {
        rotationHelper.makeDefaultOrientation();
        tutorialPage.skipTutorial();
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}

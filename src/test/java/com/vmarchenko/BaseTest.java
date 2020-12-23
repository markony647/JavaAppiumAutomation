package com.vmarchenko;

import helpers.DeviceRotationHelper;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.remote.DesiredCapabilities;
import pages.TutorialPage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class BaseTest {

    private static final String appiumLocalUrl = "http://127.0.0.1:4723/wd/hub";
    protected AppiumDriver driver;

    private TutorialPage tutorialPage;
    private DeviceRotationHelper rotationHelper;

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

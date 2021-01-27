package com.vmarchenko.base;

import core.Platform;
import factories.AndroidPageFactory;
import factories.IOSPageFactory;
import factories.MobileWebPageFactory;
import factories.PageFactory;
import helpers.DeviceRotationHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.remote.RemoteWebDriver;
import pages.WelcomePage;

public class BaseTest {

    public RemoteWebDriver driver;

    private WelcomePage welcomePage;
    private DeviceRotationHelper rotationHelper;
    public static PageFactory pageFactory;


    @BeforeClass
    public static void determineFactory() {
        Platform platform = Platform.getInstance();
        if (platform.isAndroid()) {
            pageFactory = new AndroidPageFactory();
        } else if (platform.isIOS()) {
            pageFactory = new IOSPageFactory();
        } else if (platform.isMobileWeb()) {
            pageFactory = new MobileWebPageFactory();
        } else {
            throw new RuntimeException("Cannot determine the platform");
        }
    }

    @Before
    public void setUp() {
        this.driver = Platform.getInstance().getDriver();
        welcomePage = pageFactory.welcomePage();
        rotationHelper = new DeviceRotationHelper(driver);
        navigateMainScreen();
    }

    private void navigateMainScreen() {
        rotationHelper.makeDefaultOrientation();
        welcomePage.skipWelcomePage();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

}

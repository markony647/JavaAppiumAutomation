package com.vmarchenko.base;

import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

public class BaseTest {

    protected static final String appiumLocalUrl = "http://127.0.0.1:4723/wd/hub";
    private final static String PLATFORM_IOS = "ios";
    private final static String PLATFORM_ANDROID = "android";

    protected DesiredCapabilities getDesiredCapabilitiesForPlatform() {
        String platform = System.getenv("PLATFORM");
        if (PLATFORM_ANDROID.equalsIgnoreCase(platform)) {
            return androidCapabilities();
        }
        if (PLATFORM_IOS.equalsIgnoreCase(platform)) {
            return iOSCapabilities();
        }
        throw new RuntimeException("Cannot get desired capabilities for platform " + platform);
    }

    private DesiredCapabilities androidCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", new File("apks/org.wikipedia.apk").getAbsolutePath());
        return capabilities;
    }

    private DesiredCapabilities iOSCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("deviceName", "iPhone 11");
        capabilities.setCapability("platformVersion", "13.4");
        capabilities.setCapability("app", new File("apks/Wikipedia.app").getAbsolutePath());
        return capabilities;
    }
}

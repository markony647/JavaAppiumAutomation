package core;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import lombok.SneakyThrows;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;

public class Platform {
    public static final String APPIUM_LOCAL_URL = "http://127.0.0.1:4723/wd/hub";
    public final static String PLATFORM_IOS = "ios";
    public final static String PLATFORM_ANDROID = "android";
    private static Platform platform;
    private AppiumDriver appiumDriver;

    private Platform() {
    }

    public static Platform getInstance() {
        if (platform == null) {
            platform = new Platform();
        }
        return platform;
    }

    @SneakyThrows
    public AppiumDriver getDriver() {
        URL url = new URL(APPIUM_LOCAL_URL);
        if (appiumDriver != null) {
            return appiumDriver;
        }
        if (isAndroid()) {
            appiumDriver = new AndroidDriver(url, androidCapabilities());
            return appiumDriver;
        }
        if (isIOS()) {
            appiumDriver = new IOSDriver(url, iOSCapabilities());
            return appiumDriver;
        } else {
            throw new RuntimeException("Cannot detect driver type for creation");
        }
    }

    public boolean isAndroid() {
        return PLATFORM_ANDROID.equalsIgnoreCase(getPlatform());
    }

    public boolean isIOS() {
        return PLATFORM_IOS.equalsIgnoreCase(getPlatform());
    }

    public String getPlatform() {
        return System.getenv("PLATFORM");
    }

    private static DesiredCapabilities androidCapabilities() {
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

    private static DesiredCapabilities iOSCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("deviceName", "iPhone 11");
        capabilities.setCapability("platformVersion", "13.4");
        capabilities.setCapability("app", new File("apks/Wikipedia.app").getAbsolutePath());
        return capabilities;
    }
}

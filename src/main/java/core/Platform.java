package core;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import lombok.SneakyThrows;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Platform {
    public static final String APPIUM_LOCAL_URL = "http://127.0.0.1:4723/wd/hub";
    public final static String PLATFORM_IOS = "ios";
    public final static String PLATFORM_ANDROID = "android";
    public final static String PLATFORM_MOBILE_WEB = "mobile-web";
    private static Platform platform;
    private RemoteWebDriver driver;

    private Platform() {
    }

    public static Platform getInstance() {
        if (platform == null) {
            platform = new Platform();
        }
        return platform;
    }

    @SneakyThrows
    public RemoteWebDriver getDriver() {
        URL url = new URL(APPIUM_LOCAL_URL);
        if (driver != null) {
            return driver;
        }
        if (isAndroid()) {
            driver = new AndroidDriver(url, androidCapabilities());
            return driver;
        }
        if (isIOS()) {
            driver = new IOSDriver(url, iOSCapabilities());
            return driver;
        }
        if (isMobileWeb()) {
            driver = new ChromeDriver(chromeOptions());
            return driver;
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

    public boolean isMobileWeb() {
        return PLATFORM_MOBILE_WEB.equalsIgnoreCase(getPlatform());
    }

    public String getPlatform() {
        return System.getenv("PLATFORM");
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

    private ChromeOptions chromeOptions() {
        Map<String, Object> deviceMetrics = new HashMap<>();
        deviceMetrics.put("width", 360);
        deviceMetrics.put("height", 640);
        deviceMetrics.put("pixelRation", 3.0);

        Map<String, Object> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceMetrics", deviceMetrics);
        mobileEmulation.put("userAgent", "Mozilla/5.0 (Linux; Android 4.2.1; en-us; Nexus 5 Build/JOP40D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19");

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("window-size=360,640");
        return chromeOptions;
    }
}

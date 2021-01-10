package com.vmarchenko.base;

import io.appium.java_client.ios.IOSDriver;
import org.junit.Before;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class IOSBaseTest extends BaseTest {

    protected IOSDriver driver;

    @Before
    public void setUp() throws MalformedURLException {
        DesiredCapabilities capabilities = getDesiredCapabilitiesForPlatform();

        driver = new IOSDriver(new URL(appiumLocalUrl), capabilities);
    }
}

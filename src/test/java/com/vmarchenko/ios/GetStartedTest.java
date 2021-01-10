package com.vmarchenko.ios;

import com.vmarchenko.base.IOSBaseTest;
import org.junit.Before;
import org.junit.Test;
import pages.ios.WelcomePage;

public class GetStartedTest extends IOSBaseTest {

    private WelcomePage welcomePage;

    @Before
    public void initPages() {
        welcomePage = new WelcomePage(driver);
    }

    @Test
    public void testPassWelcome() {
        welcomePage.waitForStep("The free encyclopedia");
        welcomePage.clickNext();

        welcomePage.waitForStep("New ways to explore");
        welcomePage.clickNext();

        welcomePage.waitForStep("Search in nearly 300 languages");
        welcomePage.clickNext();

        welcomePage.waitForStep("Help make the app better");
        welcomePage.clickGetStarted();
    }
}

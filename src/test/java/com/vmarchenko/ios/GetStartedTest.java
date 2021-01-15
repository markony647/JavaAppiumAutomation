package com.vmarchenko.ios;

import com.vmarchenko.base.BaseTest;
import org.junit.Before;
import org.junit.Test;
import pages.WelcomePage;

public class GetStartedTest extends BaseTest {

    private WelcomePage welcomePage;

    @Before
    public void initPages() {
        welcomePage = pageFactory.welcomePage();
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

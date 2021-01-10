package com.vmarchenko.android;

import com.vmarchenko.base.AndroidBaseTest;
import helpers.DeviceRotationHelper;
import helpers.SwipeHelper;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import pages.android.ArticlePage;
import pages.android.SearchPage;

public class ArticleTests extends AndroidBaseTest {

    private SearchPage searchPage;
    private ArticlePage articlePage;
    private SwipeHelper swipeHelper;
    private DeviceRotationHelper rotationHelper;

    @Before
    public void initPages() {
        searchPage = new SearchPage(driver);
        articlePage = new ArticlePage(driver);
        swipeHelper = new SwipeHelper(driver);
        rotationHelper = new DeviceRotationHelper(driver);
    }

    @Test
    public void testTitleElementPresentInArticle() {
        searchPage.performSearch("Selenium");

        String articleTitle = searchPage.selectNthSearchResult(2);
        articlePage.assertArticleHasTitle(articleTitle);
    }

    @Test
    public void testArticleTileShouldMatchSearchQuery() {
        searchPage.performSearch("Java")
                .waitForSearchResultWithExactText("Object-oriented programming language")
                .click();

        articlePage.assertArticleHasTitle("Java (programming language)");
    }

    @Test
    public void testSwipeArticle() {
        By endOfArticle = By.xpath("//*[@text='View article in browser']");

        searchPage.performSearch("Appium")
                .selectFirstSearchResult();
        swipeHelper.swipeUpForElement(endOfArticle);
    }

    @Test
    public void testTitleRemainsUnchangedWhenChangingDeviceOrientation() {
        String expectedArticleTitle = "Java (programming language)";

        searchPage.performSearch("Java")
                .waitForSearchResultWithExactText("Object-oriented programming language")
                .click();
        articlePage.assertArticleHasTitle(expectedArticleTitle);

        rotationHelper.rotate(ScreenOrientation.LANDSCAPE);

        articlePage.assertArticleHasTitle(expectedArticleTitle);
    }
}

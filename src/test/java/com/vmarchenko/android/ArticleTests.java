package com.vmarchenko.android;

import com.vmarchenko.base.BaseTest;
import helpers.DeviceRotationHelper;
import helpers.SwipeHelper;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import pages.android.ArticlePageAndroidImpl;
import pages.android.SearchPageAndroidImpl;

public class ArticleTests extends BaseTest {

    private SearchPageAndroidImpl searchPageAndroidImpl;
    private ArticlePageAndroidImpl articlePage;
    private SwipeHelper swipeHelper;
    private DeviceRotationHelper rotationHelper;

    @Before
    public void initPages() {
        searchPageAndroidImpl = new SearchPageAndroidImpl(driver);
        articlePage = new ArticlePageAndroidImpl(driver);
        swipeHelper = new SwipeHelper(driver);
        rotationHelper = new DeviceRotationHelper(driver);
    }

    @Test
    public void testTitleElementPresentInArticle() {
        searchPageAndroidImpl.performSearch("Selenium");

        String articleTitle = searchPageAndroidImpl.selectNthSearchResult(2);
        articlePage.assertArticleHasTitle(articleTitle);
    }

    @Test
    public void testArticleTileShouldMatchSearchQuery() {
        searchPageAndroidImpl.performSearch("Java")
                .waitForSearchResultWithExactText("Object-oriented programming language")
                .click();

        articlePage.assertArticleHasTitle("Java (programming language)");
    }

    @Test
    public void testSwipeArticle() {
        By endOfArticle = By.xpath("//*[@text='View article in browser']");

        searchPageAndroidImpl.performSearch("Appium")
                .selectFirstSearchResult();
        swipeHelper.swipeUpForElement(endOfArticle);
    }

    @Test
    public void testTitleRemainsUnchangedWhenChangingDeviceOrientation() {
        String expectedArticleTitle = "Java (programming language)";

        searchPageAndroidImpl.performSearch("Java")
                .waitForSearchResultWithExactText("Object-oriented programming language")
                .click();
        articlePage.assertArticleHasTitle(expectedArticleTitle);

        rotationHelper.rotate(ScreenOrientation.LANDSCAPE);

        articlePage.assertArticleHasTitle(expectedArticleTitle);
    }
}

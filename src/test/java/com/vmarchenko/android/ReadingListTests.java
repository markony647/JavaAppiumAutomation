package com.vmarchenko.android;

import com.vmarchenko.base.AndroidBaseTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import pages.android.*;

import java.util.List;

public class ReadingListTests extends AndroidBaseTest {

    private SearchPage searchPage;
    private ArticleMenuBar articleMenuBar;
    private SystemNavigationBar systemNavigationBar;
    private ArticlePage articlePage;

    @Before
    public void initPages() {
        searchPage = new SearchPage(driver);
        articleMenuBar = new ArticleMenuBar(driver);
        systemNavigationBar = new SystemNavigationBar(driver);
        articlePage = new ArticlePage(driver);
    }

    @Test
    public void testUserCanManageArticlesInCreatedList() {
        String listName = "chemistry";

        String firstResult = searchPage
                .performSearch("Selenium")
                .selectFirstSearchResult();

        articleMenuBar.clickSave()
                .clickAddToList()
                .createNewList(listName);

        systemNavigationBar.back();

        String secondResult = searchPage.selectNthSearchResult(1);

        articleMenuBar
                .clickSave()
                .clickAddToList()
                .selectList(listName);

        ReadingListPage readingList = articleMenuBar.goToViewList();

        List<WebElement> allSavedArticles = readingList.findAllSavedArticles();
        Assert.assertEquals(2, allSavedArticles.size());

        readingList.removeArticleWithSwipe(secondResult);

        allSavedArticles = readingList.findAllSavedArticles();
        Assert.assertEquals(1, allSavedArticles.size());
        WebElement singleArticle = allSavedArticles.get(0);
        Assert.assertEquals(singleArticle.getText(), firstResult);

        singleArticle.click();

        articlePage.assertArticleHasTitle(firstResult);
    }
}

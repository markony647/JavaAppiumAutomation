package com.vmarchenko.android;

import com.vmarchenko.base.BaseTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import pages.*;

import java.util.List;

public class ReadingListTests extends BaseTest {

    private SearchPage searchPage;
    private SavedArticlesPage savedArticlesPage;
    private ArticlePage articlePage;
    private SaveArticlePage saveArticlePage;

    @Before
    public void initPages() {
        searchPage = pageFactory.searchPage();
        savedArticlesPage = pageFactory.savedArticlesPage();
        articlePage = pageFactory.articlePage();
        saveArticlePage = pageFactory.saveArticlePage();
    }

    @Test
    public void testUserCanManageArticlesInCreatedList() {
        String listName = "chemistry";

        String firstResult = searchPage
                .performSearch("Selenium")
                .selectFirstSearchResult();

        saveArticlePage.saveArticle(listName);

        articlePage.backToSearchResults();

        String secondResult = searchPage.selectNthSearchResult(1);

        saveArticlePage.saveArticle(listName);

        ReadingListPage readingList = savedArticlesPage.visit();

        List<WebElement> allSavedArticles = readingList.findAllSavedArticles();
        Assert.assertEquals(2, allSavedArticles.size());

        readingList.removeArticleWithSwipe(secondResult);

        allSavedArticles = readingList.findAllSavedArticles();
        Assert.assertEquals(1, allSavedArticles.size());
        WebElement singleArticle = allSavedArticles.get(0);

        singleArticle.click();

        articlePage.assertArticleHasTitle(firstResult);
    }
}

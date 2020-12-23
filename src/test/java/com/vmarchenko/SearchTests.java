package com.vmarchenko;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import pages.SearchPage;

import java.util.List;

public class SearchTests extends BaseTest {

    private SearchPage searchPage;

    @Before
    public void initPages() {
        searchPage = new SearchPage(driver);
    }

    @Test
    public void testCanSearch() {
        searchPage
                .performSearch("Java")
                .waitForSearchResultWithExactText("Object-oriented programming language");
    }

    @Test
    public void testCanCancelSearch() {
        List<WebElement> foundResults = searchPage
                .performSearch("Python")
                .getAllSearchResults();

        Assert.assertTrue(foundResults.size() > 1);

        searchPage.cancelSearch();
        searchPage.waitForSearchResultInvisibility();
    }

    @Test
    public void testSearchFieldContainsCorrectText() {
        Assert.assertEquals("Search field text mismatch", "Search Wikipedia", searchPage.searchField().getText());
    }

    @Test
    public void testEachSearchResultContainsSearchQuery() {
        String searchQuery = "Java";

        List<WebElement> searchResults = searchPage.performSearch(searchQuery)
                .getAllSearchResults();
        searchResults.forEach(res -> Assert.assertTrue(res.getText().contains(searchQuery)));
    }
}

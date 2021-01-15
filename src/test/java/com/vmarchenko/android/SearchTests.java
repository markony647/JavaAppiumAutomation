package com.vmarchenko.android;

import com.vmarchenko.base.BaseTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import pages.android.SearchPageAndroidImpl;

import java.util.List;

public class SearchTests extends BaseTest {

    private SearchPageAndroidImpl searchPageAndroidImpl;

    @Before
    public void initPages() {
        searchPageAndroidImpl = new SearchPageAndroidImpl(driver);
    }

    @Test
    public void testCanSearch() {
        searchPageAndroidImpl
                .performSearch("Java")
                .waitForSearchResultWithExactText("Object-oriented programming language");
    }

    @Test
    public void testCanCancelSearch() {
        List<WebElement> foundResults = searchPageAndroidImpl
                .performSearch("Python")
                .getAllSearchResultTitles();

        Assert.assertTrue(foundResults.size() > 1);

        searchPageAndroidImpl.cancelSearch();
        searchPageAndroidImpl.waitForSearchResultInvisibility();
    }

    @Test
    public void testSearchFieldContainsCorrectText() {
        Assert.assertEquals("Search field text mismatch", "Search Wikipedia", searchPageAndroidImpl.searchField().getText());
    }

    @Test
    public void testEachSearchResultContainsSearchQuery() {
        String searchQuery = "Java";

        List<WebElement> searchResults = searchPageAndroidImpl.performSearch(searchQuery)
                .getAllSearchResultTitles();
        searchResults.forEach(res -> Assert.assertTrue(res.getText().contains(searchQuery)));
    }

    @Test
    public void testCanSearchAndFindSearchResultWithTitleAndDescription() {
        String searchQuery = "Apple";
        List<WebElement> searchResults = searchPageAndroidImpl
                .performSearch(searchQuery)
                .getAllSearchResultTitles();

        Assert.assertTrue(searchResults.size() >= 2);

        searchPageAndroidImpl.findAndAssertSearchResultWithTitleAndDescription(searchQuery, "Edible fruit of domesticated deciduous tree");
    }
}

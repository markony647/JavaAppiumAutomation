package pages;

import org.openqa.selenium.WebElement;

import java.util.List;

public interface SearchPage {

    WebElement findAndAssertSearchResultWithTitleAndDescription(String title, String description);
    SearchPage performSearch(String queryToSearch);
    List<WebElement> getAllSearchResultTitles();
    String selectFirstSearchResult();
    void cancelSearch();
    WebElement waitForSearchResultWithExactText(String textInSearchResult);
    String selectNthSearchResult(int resultIndex);
}

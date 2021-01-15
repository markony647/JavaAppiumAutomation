package pages;

import org.openqa.selenium.WebElement;

import java.util.List;

public interface ReadingListPage {
    List<WebElement> findAllSavedArticles();
    void removeArticleWithSwipe(String articleTitle);
}

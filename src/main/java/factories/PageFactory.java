package factories;

import core.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;
import pages.*;

public interface PageFactory {
    WelcomePage welcomePage();
    SearchPage searchPage();
    SaveArticlePage saveArticlePage();
    SavedArticlesPage savedArticlesPage();
    NavigationBar systemNavigationBar();
    ArticlePage articlePage();

    default RemoteWebDriver driver() {
        return Platform.getInstance().getDriver();
    }
}

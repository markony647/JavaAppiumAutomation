package factories;

import core.Platform;
import io.appium.java_client.AppiumDriver;
import pages.*;

public interface PageFactory {
    WelcomePage welcomePage();
    SearchPage searchPage();
    SaveArticlePage saveArticlePage();
    SavedArticlesPage savedArticlesPage();
    NavigationBar systemNavigationBar();
    ArticlePage articlePage();

    default AppiumDriver driver() {
        return Platform.getInstance().getDriver();
    }
}

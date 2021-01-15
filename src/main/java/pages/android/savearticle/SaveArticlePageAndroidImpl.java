package pages.android.savearticle;

import core.Platform;
import io.appium.java_client.AppiumDriver;
import pages.SaveArticlePage;

public class SaveArticlePageAndroidImpl implements SaveArticlePage {

    private AppiumDriver driver;
    private ArticleMenuBarAndroidImpl articleMenuBar;

    public SaveArticlePageAndroidImpl(AppiumDriver driver) {
        this.driver = driver;
        this.articleMenuBar = new ArticleMenuBarAndroidImpl(driver);
    }

    @Override
    public void saveArticle(String folder) {
        articleMenuBar
                .clickSave()
                .clickAddToList()
                .createNewList(folder);
    }

    @Override
    public void saveArticle() {
        throw new RuntimeException("Cannot save article without specifying folder name for platform " + Platform.PLATFORM_ANDROID.toUpperCase());
    }
}

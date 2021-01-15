package factories;

import pages.*;
import pages.android.ArticlePageAndroidImpl;
import pages.android.NavigationBarAndroidImpl;
import pages.android.SearchPageAndroidImpl;
import pages.android.WelcomePageAndroidImpl;
import pages.android.savearticle.SaveArticlePageAndroidImpl;
import pages.ios.SavedArticlesPageIOSImpl;

public class AndroidPageFactory implements PageFactory {

    @Override
    public WelcomePage welcomePage() {
        return new WelcomePageAndroidImpl(driver());
    }

    @Override
    public SearchPage searchPage() {
        return new SearchPageAndroidImpl(driver());
    }

    @Override
    public SaveArticlePage saveArticlePage() {
        return new SaveArticlePageAndroidImpl(driver());
    }

    @Override
    public SavedArticlesPageIOSImpl savedArticlesPage() {
        return new SavedArticlesPageIOSImpl(driver());
    }

    @Override
    public NavigationBar systemNavigationBar() {
        return new NavigationBarAndroidImpl(driver());
    }

    @Override
    public ArticlePage articlePage() {
        return new ArticlePageAndroidImpl(driver());
    }
}

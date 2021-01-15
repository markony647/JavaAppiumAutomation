package factories;

import pages.*;
import pages.ios.*;

public class IOSPageFactory implements PageFactory {

    @Override
    public WelcomePage welcomePage() {
        return new WelcomePageIOSImpl(driver());
    }

    @Override
    public SearchPage searchPage() {
        return new SearchPageIOSImpl(driver());
    }

    @Override
    public SaveArticlePage saveArticlePage() {
        return new SaveArticlePageIOSImpl(driver());
    }

    @Override
    public SavedArticlesPageIOSImpl savedArticlesPage() {
        return new SavedArticlesPageIOSImpl(driver());
    }

    @Override
    public NavigationBar systemNavigationBar() {
        return new NavigationBarIOSImpl(driver());
    }

    @Override
    public ArticlePage articlePage() {
        return new ArticlePageIOSImpl(driver());
    }

}

package pages;

public interface WelcomePage {

    void clickNext();
    void clickGetStarted();
    void skipWelcomePage();
    void waitForStep(String step);
}

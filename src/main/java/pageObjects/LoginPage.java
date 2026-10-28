package pageObjects;

import common.Constant;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    // Locator
    private final By usernameInput = By.xpath("//*[@id='username']");
    private final By passwordInput = By.xpath("//*[@id='password']");
    private final By loginButton = By.xpath("//input[@type='submit' and @value='login']");
    private final By resetPasswordLink = By.xpath("//a[contains(@href,'ForgotPassword')]");
    private final By loginMessage = By.xpath("//*[@id='content']/p");

    // Constructor
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // Function

    @Step("Log in with username: {username} and password: {password} ")
    public void login(String username, String password) {
        typeInTextBox(usernameInput, username);
        typeInTextBox(passwordInput, password);
        clickButtonWithJS(loginButton);
    }

    @Step("Repeat logging in several times with username: {username} and password: {password} ")
    public void repeatLoginAction(int loginAttempt, String username, String password) {
        for (int i = 0; i < loginAttempt; i++) {
            login(username, password);
        }
    }

    @Step("Click on Forgot Password link")
    public void clickResetPasswordLink(){
        clickLink(resetPasswordLink);
    }

    @Step("Get login message")
    public String getLoginMessage(){
        String message = getTextOfElement(loginMessage);
        Allure.getLifecycle().updateStep(step -> step.setName("Get login message: " + message));
        return message;
    }
}
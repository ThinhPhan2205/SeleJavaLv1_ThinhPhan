package pageObjects;

import common.Constant;
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

    // Login to Railway
    public void login(String username, String password) {
        typeInTextBox(usernameInput, username);
        typeInTextBox(passwordInput, password);
        clickButtonWithJS(loginButton);
        new BasePage(driver);
    }

    public void repeatLoginAction(int loginAttempt) {
        for (int i = 0; i < loginAttempt; i++) {
            login(Constant.AccountToLogin.USERNAME.getValue(),
                    Constant.InvalidInputs.WRONG_PASSWORD.getValue()
            );
        }
    }

    // Click on Forget Password link
    public void clickResetPasswordLink(){
        clickLink(resetPasswordLink);
    }

    // Check login message
    public String getLoginMessage(){
        return getTextOfElement(loginMessage);
    }
}
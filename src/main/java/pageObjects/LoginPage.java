package pageObjects;

import common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends GeneralPage {

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

    GeneralPage generalPage = new GeneralPage(driver);

    // Function

    // Login to Railway
    public void login(String username, String password) {
        type(usernameInput, username);
        type(passwordInput, password);
        clickButton(loginButton);
        new BasePage(driver);
    }

    public void repeatLoginActionAndCheckMessage(int loginAttempt) {
        for (int i = 0; i < loginAttempt; i++) {
            login(Constant.AccountToLogin.USERNAME.getValue(),
                    Constant.InvalidInputs.WRONG_PASSWORD.getValue()
            );
            if (i == 0) {
                checkLoginMessage(Constant.WRONG_ACCOUNT_LOGIN_MESSAGE);

                // Check warning message
            } else if (i == (loginAttempt - 1)) {
                checkLoginMessage(Constant.DISABLED_LOGIN_WARNING);
            }
        }
    }

    // Click on Forget Password link
    public void clickResetPasswordLink(){
        clickOnLink(resetPasswordLink);
    }

    // Check login message
    public void checkLoginMessage(String expectedMessage){
        String ACTUAL_MESSAGE = generalPage.getMessage(loginMessage);
        generalPage.checkMessage(ACTUAL_MESSAGE, expectedMessage);
    }
}
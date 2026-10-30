package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ResetPasswordPage extends BasePage {

    // Constructor
    public ResetPasswordPage(WebDriver driver) {
        super(driver);
    }

    // Locator
    private final By emailInput               = By.id("email");
    private final By passwordInput            = By.id("newPassword");
    private final By confirmPasswordInput     = By.id("confirmPassword");
    private final By sendButton               = By.xpath("//input[@type='submit' and @value='Send Instructions']");;
    private final By resetButton              = By.xpath("//input[@type='submit' and @value='Reset Password']");
    private final By resetMessage             = By.xpath("//*[@id='content']/p");
    private final By unmatchedPasswordMessage = By.xpath("//label[@for='confirmPassword' and contains(@class,'validation-error')]");
    private static final By resetToken = By.id("resetToken");

    public void getInstructionEmail(String email) {
        typeInTextBox(emailInput, email);
        clickButtonWithJS(sendButton);
    }

    public void resetPassword(String password, String confirmPassword) {
        typeInTextBox(passwordInput, password);
        typeInTextBox(confirmPasswordInput, confirmPassword);
        clickButtonWithJS(resetButton);
    }

    public String getResetToken() {
        return getInputValue(resetToken);
    }

    public String getResetMessage(){
        return getTextOfElement(resetMessage);
    }

    public String getPasswordWarning(){
        return getTextOfElement(unmatchedPasswordMessage);
    }


}

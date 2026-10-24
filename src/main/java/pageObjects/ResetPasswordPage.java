package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResetPasswordPage extends GeneralPage {

    // Constructor
    public ResetPasswordPage(WebDriver driver) {
        super(driver);
    }
    GuerrillaMailPage guerrillaPage = new GuerrillaMailPage(driver);

    // Locator
    private final By emailInput               = By.id("email");
    private final By passwordInput            = By.id("newPassword");
    private final By confirmPasswordInput     = By.id("confirmPassword");
    private final By sendButton               = By.xpath("//input[@type='submit' and @value='Send Instructions']");;
    private final By resetButton              = By.xpath("//input[@type='submit' and @value='Reset Password']");
    private final By resetMessage             = By.xpath("//*[@id='content']/p");
    private final By unmatchedPasswordMessage = By.xpath("//label[@for='confirmPassword' and contains(@class,'validation-error')]");

    private static final By resetToken = By.id("resetToken");
    public void getInstructionToEmail(String email) {
        type(emailInput, email);
        clickButton(sendButton);
    }

    public void resetPassword(String password, String confirmPassword) {
        type(passwordInput, password);
        type(confirmPasswordInput, confirmPassword);
        clickButton(resetButton);
    }

    public String getInputValue(By locator) {
        return driver.findElement(locator).getAttribute("value");
    }

    public void checkResetToken(String ACTUAL_TOKEN) {
        checkMessage(ACTUAL_TOKEN,getInputValue(resetToken));
    }

    public String getResetMessage(){
        return getMessage(resetMessage);
    }

    public String getPasswordWarning(){
        return getMessage(unmatchedPasswordMessage);
    }


}

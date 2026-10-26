package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage extends BasePage {

    // Locator
    private final By emailInput           = By.id("email");
    private final By passwordInput        = By.id("password");
    private final By confirmPasswordInput = By.id("confirmPassword");
    private final By pidInput             = By.id("pid");
    private final By registerButton       = By.xpath("//*[@id='RegisterForm']/fieldset/p/input");
    public static final By errorRegisterMessage  = By.xpath("//*[@id='content']/p[2]");
    public static final By registerConfirmMessage = By.xpath("//*[@id='content']/p");

    // Constructor
    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    // Fill registration form
    public void fillRegisterForm(String email, String password,
                                 String confirmPassword, String pid) {
        typeInTextBox(emailInput, email);
        typeInTextBox(passwordInput, password);
        typeInTextBox(confirmPasswordInput, confirmPassword);
        typeInTextBox(pidInput, pid);
    }

    // Click register button
    public void clickRegisterButton() {
        clickButtonWithJS(registerButton);
    }

    // Register method
    public void register(String email, String password,
                         String confirmPassword, String pid) {
        fillRegisterForm(email, password, confirmPassword, pid);
        clickRegisterButton();
    }

    // Check error login message
    public String getErrorRegisterMessage(){
        return getTextOfElement(errorRegisterMessage);
    }

    // Check register confirmation message
    public String getConfirmRegisterMessage() {
        return getTextOfElement(registerConfirmMessage);
    }
}
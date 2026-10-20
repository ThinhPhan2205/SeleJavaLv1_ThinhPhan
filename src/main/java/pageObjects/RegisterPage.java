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

    // Constructor
    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    // Fill registration form
    public void fillRegisterForm(String email, String password,
                                 String confirmPassword, String pid) {
        type(emailInput, email);
        type(passwordInput, password);
        type(confirmPasswordInput, confirmPassword);
        type(pidInput, pid);
    }

    // Click register button
    public void clickRegisterButton() {
        ClickButton(registerButton);
    }

    // Register method
    public void register(String email, String password,
                         String confirmPassword, String pid) {
        fillRegisterForm(email, password, confirmPassword, pid);
        clickRegisterButton();
    }
}
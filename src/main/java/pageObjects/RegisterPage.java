package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

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
    GeneralPage generalPage = new GeneralPage(driver);

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
        clickButton(registerButton);
    }

    // Register method
    public void register(String email, String password,
                         String confirmPassword, String pid) {
        fillRegisterForm(email, password, confirmPassword, pid);
        clickRegisterButton();
    }

    // Check error login message
    public void checkErrorMessage(String expectedMessage){
        String ACTUAL_MESSAGE = generalPage.getMessage(errorRegisterMessage);
        generalPage.checkMessage(ACTUAL_MESSAGE, expectedMessage);
    }

    // Check register confirmation message
    public void checkConfirmRegisterMessage(String expectedMessage){
        String ACTUAL_MESSAGE = generalPage.getMessage(registerConfirmMessage);
        generalPage.checkMessage(ACTUAL_MESSAGE, expectedMessage);
    }
}
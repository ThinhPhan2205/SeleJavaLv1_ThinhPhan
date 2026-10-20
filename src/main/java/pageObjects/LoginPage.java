package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends GeneralPage {

    // Locator
    private final By usernameInput = By.xpath("//*[@id='username']");
    private final By passwordInput = By.xpath("//*[@id='password']");
    private final By loginButton   = By.xpath("//*[@id='content']/form/fieldset/p/input");

    // Constructor
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // Function
    public BasePage login(String username, String password) {
        type(usernameInput, username);
        type(passwordInput, password);
        ClickButton(loginButton);
        return new BasePage(driver);
    }
}
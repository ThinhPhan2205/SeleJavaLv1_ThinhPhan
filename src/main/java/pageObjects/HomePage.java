package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public final By registerLink = By.xpath("//a[@href='/Account/Register.cshtml']");

    public boolean isAtHomePage() {
        try {
            wait.until(ExpectedConditions.urlContains("Home"));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isRegisterLinkPresent() {
        return !driver.findElements(registerLink).isEmpty();
    }

    public void clickRegisterLink() {
        clickLink(registerLink);
    }
}

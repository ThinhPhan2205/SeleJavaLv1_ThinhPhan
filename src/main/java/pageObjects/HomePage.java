package pageObjects;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public boolean isAtHomePage() {
        try {
            wait.until(ExpectedConditions.urlContains("Home"));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}

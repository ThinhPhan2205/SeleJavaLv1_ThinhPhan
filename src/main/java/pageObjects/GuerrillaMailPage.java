package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GuerrillaMailPage extends BasePage {

    // Wait for email to display
    private final WebDriverWait mailWait;
    private static final int MAIL_TIMEOUT_SECONDS = 30;

    // Locator
    private final By emailAddress = By.xpath("//*[@id='email-widget']");
    private final By emailBody    = By.xpath("//*[@id='display_email']/div/div[2]/h3");
    private final By scrambleCheckbox = By.xpath("//*[@id='use-alias']");

    // Get row containing expected email
    private By emailRow(String subjectKeyword) {
        return By.xpath("//tbody[@id='email_list']//tr[contains(normalize-space(.), '"
                + subjectKeyword + "')]");
    }

    // Constructor
    public GuerrillaMailPage(WebDriver driver) {
        super(driver);
        this.mailWait = new WebDriverWait(driver, Duration.ofSeconds(MAIL_TIMEOUT_SECONDS));
    }

    // Get email
    public String getEmail() {
        ClickButton(scrambleCheckbox);
        wait.until(d -> !find(emailAddress).getText().trim().isEmpty());
        return find(emailAddress).getText().trim();
    }

    // Wait for expected email
    public boolean isEmailDisplayed(String subjectKeyword) {
        try {
            mailWait.until(ExpectedConditions.visibilityOfElementLocated(emailRow(subjectKeyword)));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    // Open email
    public void openEmail(String subjectKeyword) {
        mailWait.until(ExpectedConditions.visibilityOfElementLocated(emailRow(subjectKeyword)));
        ClickButton(emailRow(subjectKeyword));
    }

    // Get email message
    public String getEmailContent() {
        return getText(emailBody);
    }

    // Combining open email and return message
    public String openEmailAndGetContent(String subjectKeyword) {
        openEmail(subjectKeyword);
        return getEmailContent();
    }
}
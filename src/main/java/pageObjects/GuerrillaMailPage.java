package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuerrillaMailPage extends BasePage {

    // Constructor
    public GuerrillaMailPage(WebDriver driver) {
        super(driver);
        this.mailWait = new WebDriverWait(driver, Duration.ofSeconds(MAIL_TIMEOUT_SECONDS));
    }
    GeneralPage generalPage = new GeneralPage(driver);

    // Locator
    private final WebDriverWait mailWait;
    private static final int MAIL_TIMEOUT_SECONDS = 30;
    private final By emailAddress                 = By.id("email-widget");
    private final By emailConfirmBody                    = By.xpath("//*[@id='display_email']//h3");
    private final By scrambleCheckbox             = By.xpath("//*[@id='use-alias']");
    private final By goBackToInboxButton          = By.id("back_to_inbox_link");
    private final By activationLink               = By.xpath("//*[@id='display_email']//a[contains(@href,'Account')]");
    private final By emailResetBody               = By.xpath("//div[@id='display_email']//div[contains(@class,'body')]");
    private static final Pattern tokenPattern     = Pattern.compile("The token is:\\s*([A-Za-z0-9+/=]+)");

    // Get row containing expected email
    private By emailRow(String subjectKeyword) {
        return By.xpath("//tbody[@id='email_list']//tr[contains(normalize-space(.), '"
                + subjectKeyword + "')]");
    }

    // Get email
    public String getEmail() {
        clickButton(scrambleCheckbox);
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
        clickButton(emailRow(subjectKeyword));
    }

    // Get activation link
    public String getActivationLink() {

        return wait.until(ExpectedConditions.visibilityOfElementLocated(activationLink))
                .getAttribute("href");
    }

    // Click link in mailbox
    public void clickLinkInMail() {
        clickLink(activationLink);
    }

    // Get email message
    public String getEmailContent() {
        return getText(emailConfirmBody);
    }

    // Click go back to inbox button
    public void goBackToInbox(){
        generalPage.refreshPage();
        clickButton(goBackToInboxButton);
    }


    // Get Reset Token
    public String getResetToken() {
        String emailText = generalPage.getMessage(emailResetBody);

        Matcher matcher = tokenPattern.matcher(emailText);
        if (matcher.find()) {
            return matcher.group(1);
        }
        throw new IllegalStateException("Token not found in email: " + emailText);

    }

    // Combining open email and return message
    public String openEmailAndGetContent(String subjectKeyword) {
        openEmail(subjectKeyword);
        return getEmailContent();
    }
}
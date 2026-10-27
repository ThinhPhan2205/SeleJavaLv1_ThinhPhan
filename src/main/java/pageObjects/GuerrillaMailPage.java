package pageObjects;

import common.Constant;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
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

    // Locator
    private final WebDriverWait mailWait;
    private static final int MAIL_TIMEOUT_SECONDS = 30;
    private final By emailAddress                 = By.id("email-widget");
    private final By emailConfirmBody             = By.xpath("//*[@id='display_email']//h3");
    private final By scrambleCheckbox             = By.xpath("//*[@id='use-alias']");
    private final By goBackToInboxButton          = By.id("back_to_inbox_link");
    private final By linkInMailBox                = By.xpath("//*[@id='display_email']//a[contains(@href,'Account')]");
    private final By emailResetBody               = By.xpath("//div[@id='display_email']//div[contains(@class,'body')]");
    private static final Pattern tokenPattern     = Pattern.compile("The token is:\\s*([A-Za-z0-9+/=]+)");

    @Step("Navigate to Guerrilla Mail")
    public String goToGuerrillaSite() {
        driver.get(Constant.MAILBOX_URL);
        return driver.getWindowHandle();
    }

    // Get row containing expected email
    private By emailRow(String subjectKeyword) {
        return By.xpath("//tbody[@id='email_list']//tr[contains(normalize-space(.), '"
                + subjectKeyword + "')]");
    }

    @Step("Get a clone email")
    public String getEmail() {
        clickButtonWithJS(scrambleCheckbox);
        wait.until(d -> !findElement(emailAddress).getText().trim().isEmpty());
        String email = findElement(emailAddress).getText().trim();
        Allure.getLifecycle().updateStep(step -> step.setName("Get clone email: " + email));
        return email;
    }

    @Step("Open email containing keyword: {subjectKeyword}")
    public void openEmail(String subjectKeyword) {
        mailWait.until(ExpectedConditions.visibilityOfElementLocated(emailRow(subjectKeyword)));
        clickButtonWithJS(emailRow(subjectKeyword));
    }

    @Step("Get link attached in mail")
    public String getLinkInMailBox() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(linkInMailBox))
                .getAttribute("href");
    }

    @Step("Click link in mail")
    public void clickLinkInMail() {
        clickLink(linkInMailBox);
    }

    @Step("Go back to mailbox")
    public void goBackToInbox(){
        refreshPage();
        clickButtonWithJS(goBackToInboxButton);
    }

    @Step("Get reset token")
    public String getResetToken() {
        String emailText = getTextOfElement(emailResetBody);
        Matcher matcher = tokenPattern.matcher(emailText);
        if (matcher.find()) {
            String url = matcher.group(1);
            Allure.getLifecycle().updateStep(step -> step.setName("Get reset token: " + url));
            return url;
        }
        throw new IllegalStateException("Token not found in email: " + emailText);
    }
}
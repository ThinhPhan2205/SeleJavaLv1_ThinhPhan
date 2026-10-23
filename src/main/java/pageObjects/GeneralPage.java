package pageObjects;

import common.Account;
import common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class GeneralPage extends BasePage {

    // Locator
    private By menuTab(String tabName) {
        String lower = tabName.trim().toLowerCase();
        return By.xpath("//*[@id='menu']//a[translate(normalize-space(.), "
                + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')='" + lower + "']");
    }
    final By welcomeMessage = By.xpath("//div[@class='account']/strong");
    protected String guerrillaTab;
    protected String railwayTab;

    // Constructor
    public GeneralPage(WebDriver driver) {
        super(driver);
    }
    BasePage basepage = new BasePage(driver);

    // Function

    // Click on menu tabs
    private void clickOnPage(String tabName) {
        clickButton(menuTab(tabName));
    }

    public LoginPage clickOnLoginTab() {
        clickOnPage(Constant.MenuTab.LOGIN.getValue());
        return new LoginPage(driver);
    }

    public RegisterPage clickOnRegisterTab() {
        clickOnPage(Constant.MenuTab.REGISTER.getValue());
        return new RegisterPage(driver);
    }

    public MyticketPage clickOnMyticketTab() {
        clickOnPage(Constant.MenuTab.MY_TICKET.getValue());
        return new MyticketPage(driver);
    }

    public TimetablePage clickOnTimetableTab() {
        clickOnPage(Constant.MenuTab.TIMETABLE.getValue());
        return new TimetablePage(driver);
    }

    public BookticketPage clickOnBookTicketTab() {
        clickOnPage(Constant.MenuTab.BOOK_TICKET.getValue());
        return new BookticketPage(driver);
    }

    public LogoutPage clickOnLogoutTab() {
        clickOnPage(Constant.MenuTab.LOGOUT.getValue());
        return new LogoutPage(driver);
    }

    public FAQPage clickOnFAQTab() {
        clickOnPage(Constant.MenuTab.FAQ.getValue());
        return new FAQPage(driver);
    }

    // Refresh page
    public void refreshPage() {
        driver.navigate().refresh();
    }

    // Click on link
    public void clickOnLink(By linkText){
        clickLink(linkText);
    }

    // Get Message by locator
    public String getMessage(By locator) {
        return getText(locator);
    }

    // Check Welcome message
    public void checkWelcomeMessage() {
        String ACTUAL_MESSAGE = getMessage(welcomeMessage);
        basepage.checkMessage(ACTUAL_MESSAGE, Constant.WELCOME_MESSAGE);
    }

    // Check if tab is disappeared
    public boolean isTabDisappeared(String tabName) {
        By tabXPath = By.xpath("//div[@id='menu']//a[span='" + tabName + "']");
        try {
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(tabXPath));
        } catch (TimeoutException e) {
            return false;
        }
    }

    // Check if at Railway Page
    public boolean isAtRailwayPage() {
        try {
            wait.until(driver -> driver.getCurrentUrl().toLowerCase()
                    .contains("railway".toLowerCase()));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    // Create and activate a new account
    public Account createAndActivateAccount() {
        String subjectKeyword = "confirm";

        // 1. Navigate to Guerrilla Mail
        driver.get(Constant.MAILBOX_URL);
        guerrillaTab = driver.getWindowHandle();

        // 2. Get a free email
        GuerrillaMailPage mailPage = new GuerrillaMailPage(driver);
        String email = mailPage.getEmail();

        // 3. Navigate to Railway
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(Constant.RAILWAY_URL);
        railwayTab = driver.getWindowHandle();

        // 4. Register a valid account
        GeneralPage homePage = new GeneralPage(driver);
        RegisterPage registerPage = homePage.clickOnRegisterTab();
        registerPage.register(email,
                Constant.AccountToRegister.PASSWORD.getValue(),
                Constant.AccountToRegister.PASSWORD.getValue(),
                Constant.AccountToRegister.PID.getValue());

        // 5. Go back to Guerrilla Mail
        driver.switchTo().window(guerrillaTab);
        homePage.refreshPage();

        // 6. Open confirmation email
        mailPage.openEmail(subjectKeyword);

        // 7. Click on Activation Link
        String activationUrl = mailPage.getActivationLink();

        // 8. Redirected to Railway and confirmation message displays
        driver.switchTo().window(railwayTab);
        driver.get(activationUrl);

        Assert.assertTrue(homePage.isAtRailwayPage(), "Không ở trang Railway sau khi kích hoạt");

        return new Account(email, Constant.AccountToRegister.PASSWORD.getValue());
    }

    // Go back to Guerrilla site
    public void switchToMailboxPage() {
        driver.switchTo().window(guerrillaTab);
    }
    public void switchToRailwayPage() {
        driver.switchTo().window(railwayTab);
    }

}
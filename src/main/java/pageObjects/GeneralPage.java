package pageObjects;

import common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class GeneralPage extends BasePage {

    // Locator
    private By menuTab(String tabName) {
        String lower = tabName.trim().toLowerCase();
        return By.xpath("//*[@id='menu']//a[translate(normalize-space(.), "
                + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')='" + lower + "']");
    }

    // Constructor
    public GeneralPage(WebDriver driver) {
        super(driver);
    }

    // Function

    private void goToPage(String tabName) {
        clickButton(menuTab(tabName));
    }

    public LoginPage goToLoginPage() {
        goToPage(Constant.MenuTab.LOGIN.getValue());
        return new LoginPage(driver);
    }

    public RegisterPage goToRegisterPage() {
        goToPage(Constant.MenuTab.REGISTER.getValue());
        return new RegisterPage(driver);
    }

    public TimetablePage goToTimetablePage() {
        goToPage(Constant.MenuTab.TIMETABLE.getValue());
        return new TimetablePage(driver);
    }

    public BookticketPage goToBookTicketPage() {
        goToPage(Constant.MenuTab.BOOK_TICKET.getValue());
        return new BookticketPage(driver);
    }

    public LogoutPage goToLogoutPage() {
        goToPage(Constant.MenuTab.LOGOUT.getValue());
        return new LogoutPage(driver);
    }

    public FAQPage goToFAQPage() {
        goToPage(Constant.MenuTab.LOGOUT.getValue());
        return new FAQPage(driver);
    }

    public String getMessage(By locator) {
        return getText(locator);
    }

    public void checkMessage(String ACTUAL_MESSAGE, String EXPECTED_MESSAGE) {
        System.out.println("Observed message: " + ACTUAL_MESSAGE);
        System.out.println("Expected message: " + EXPECTED_MESSAGE);
        Boolean isequal = isEqual(ACTUAL_MESSAGE, EXPECTED_MESSAGE);
        if (isequal) {
            System.out.println("Displayed Message is correct");
        }
        else {
            System.out.println("Displayed Message is wrong");
        }
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
}
package pageObjects;

import common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

// Chứa những phần tử xuất hiện ở MỌI trang: menu, dòng Welcome
public class GeneralPage extends BasePage {

    // ===== Locator =====
    private final By welcomeMessage = By.xpath("//div[@class='account']/strong");

    // Locator có tham số: tab trên menu, không phân biệt hoa thường
    private By menuTab(String tabName) {
        String lower = tabName.trim().toLowerCase();
        return By.xpath("//*[@id='menu']//a[translate(normalize-space(.), "
                + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')='" + lower + "']");
    }

    // ===== Constructor =====
    public GeneralPage(WebDriver driver) {
        super(driver);
    }

    // ===== Function =====
    private void goToPage(String tabName) {
        ClickButton(menuTab(tabName));
    }

    public LoginPage goToLoginPage() {
        goToPage(Constant.PAGE_LOGIN);
        return new LoginPage(driver);
    }

    public RegisterPage goToRegisterPage() {
        goToPage(Constant.PAGE_REGISTER);
        return new RegisterPage(driver);
    }

    public TimetablePage goToTimetablePage() {
        goToPage(Constant.PAGE_TIMETABLE);
        return new TimetablePage(driver);
    }

    public BookticketPage goToBookTicketPage() {
        goToPage(Constant.PAGE_BOOK_TICKET);
        return new BookticketPage(driver);
    }

    public String getWelcomeMessage() {
        return getText(welcomeMessage);
    }
}
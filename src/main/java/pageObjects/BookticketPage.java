package pageObjects;

import helpers.AjaxWaiter;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BookticketPage extends BasePage {

    // Constructor
    public BookticketPage(WebDriver driver) {
        super(driver);
    }
    AjaxWaiter ajax = new AjaxWaiter(driver, "GetArriveList");

    // Locator
    private final By departDate   = By.name("Date");
    private final By departFrom   = By.name("DepartStation");
    private final By arriveAt     = By.name("ArriveStation");
    private final By seatType     = By.name("SeatType");
    private final By ticketAmount = By.name("TicketAmount");
    private final By bookButton   = By.xpath("//input[@type='submit' and @value='Book ticket']");
    private final By ticketTableTitle = By.xpath("//div[@id='content']//table//th");

    // Function

    @Step("Select values from dropdown list")
    public void fillBookTicketForm(String date, String from, String to,
                                   String seat, String amount) {
        ajax.installTracker();

        selectByText(departDate, date);
        selectByText(departFrom, from);

        ajax.waitForRequestsDone();

        selectByText(arriveAt, to);
        selectByText(seatType, seat);
        selectByText(ticketAmount, amount);
    }

    @Step("Select depart date")
    public void selectDepartDate(String date) {
        selectByText(departDate, date);
    }

    @Step("Select ticket amount")
    public void selectTicketAmount(String amount) {
        selectByText(ticketAmount, amount);
    }

    @Step("Click book ticket button")
    public void clickBookTicketButton() {
        clickButtonWithJS(bookButton);
        new BookingSuccessPage(driver);
    }

    @Step("Get ticket confirmation title")
    public String getTicketTableTitle() {
        String title = getTextOfElement(ticketTableTitle);
        Allure.getLifecycle().updateStep(
                step -> step.setName("Get ticket confirmation title: " + title)
        );
        return title;
    }

    @Step("Book ticket")
    public void bookTicket(String date, String from, String to,
                           String seat, String amount) {
        fillBookTicketForm(date, from, to, seat, amount);
        clickBookTicketButton();
    }
}
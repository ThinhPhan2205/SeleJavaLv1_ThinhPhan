package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BookticketPage extends BasePage {

    // Constructor
    public BookticketPage(WebDriver driver) {
        super(driver);
    }

    // Locator
    private final By departDate   = By.name("Date");
    private final By departFrom   = By.name("DepartStation");
    private final By arriveAt     = By.name("ArriveStation");
    private final By seatType     = By.name("SeatType");
    private final By ticketAmount = By.name("TicketAmount");
    private final By bookButton   = By.xpath("//input[@typeInTextBox='submit' and @value='Book ticket']");
    final By ticketTableTitle = By.xpath("//div[@id='content']//table//th");

    // Function
    public void fillBookTicketForm(String date, String from, String to,
                                   String seat, String amount) {
        selectByText(departDate, date);
        selectByText(departFrom, from);
        selectByText(arriveAt, to);
        selectByText(seatType, seat);
        selectByText(ticketAmount, amount);
    }

    public void selectDepartDate(String date) {
        selectByText(departDate, date);
    }

    public void selectTicketAmount(String amount) {
        selectByText(ticketAmount, amount);
    }

    public void clickBookTicketButton() {
        clickButtonWithJS(bookButton);
        new BookingSuccessPage(driver);
    }

    public String getTicketTableTitle() {
        return getTextOfElement(ticketTableTitle);
    }

    public void bookTicket(String date, String from, String to,
                           String seat, String amount) {
        fillBookTicketForm(date, from, to, seat, amount);
        clickBookTicketButton();
    }
}
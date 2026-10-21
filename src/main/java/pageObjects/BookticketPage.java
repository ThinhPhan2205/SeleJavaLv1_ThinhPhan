package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BookticketPage extends BasePage {

    // ===== Locator =====
    private final By departDate   = By.name("Date");
    private final By departFrom   = By.name("DepartStation");
    private final By arriveAt     = By.name("ArriveStation");
    private final By seatType     = By.name("SeatType");
    private final By ticketAmount = By.name("TicketAmount");
    private final By bookButton   = By.xpath("//input[@type='submit' and @value='Book ticket']");

    // Constructor
    public BookticketPage(WebDriver driver) {
        super(driver);
    }

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

    public BookingSuccessPage clickBookTicketButton() {
        clickButton(bookButton);
        return new BookingSuccessPage(driver);
    }

    // Gộp: điền form rồi bấm đặt vé
    public BookingSuccessPage bookTicket(String date, String from, String to,
                                         String seat, String amount) {
        fillBookTicketForm(date, from, to, seat, amount);
        return clickBookTicketButton();
    }
}
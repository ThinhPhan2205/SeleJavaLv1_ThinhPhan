package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BookticketPage extends BasePage {

    // ===== Locator =====
    private final By departDate   = By.xpath("//*[@id='content']/div[1]/form/fieldset/ol/li[1]/select");
    private final By departFrom   = By.xpath("//*[@id='content']/div[1]/form/fieldset/ol/li[2]/select");
    private final By arriveAt     = By.xpath("//*[@id='ArriveStation']/select");
    private final By seatType     = By.xpath("//*[@id='content']/div[1]/form/fieldset/ol/li[4]/select");
    private final By ticketAmount = By.xpath("//*[@id='content']/div[1]/form/fieldset/ol/li[5]/select");
    private final By bookButton   = By.xpath("//*[@id='content']/div[1]/form/fieldset/input");

    // ===== Constructor =====
    public BookticketPage(WebDriver driver) {
        super(driver);
    }

    // ===== Function =====
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
        ClickButton(bookButton);
        return new BookingSuccessPage(driver);
    }

    // Gộp: điền form rồi bấm đặt vé
    public BookingSuccessPage bookTicket(String date, String from, String to,
                                         String seat, String amount) {
        fillBookTicketForm(date, from, to, seat, amount);
        return clickBookTicketButton();
    }
}
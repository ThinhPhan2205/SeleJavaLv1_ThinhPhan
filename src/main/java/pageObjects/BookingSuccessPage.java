package pageObjects;

import common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

public class BookingSuccessPage extends BasePage {

    // Constructor
    public BookingSuccessPage(WebDriver driver) {
        super(driver);
    }

    // Locator
    private final By successMessage = By.xpath("//*[@id='content']/h1");
    private By ticketCell(int columnIndex) {
        return By.xpath("(//table//tr[td])[1]/td[" + columnIndex + "]");
    }

    // Function

    // Get booking successfully message
    public String getSuccessMessage() {
        return getTextOfElement(successMessage);
    }

    // Get information by column header
    public String getTicketInfo(String columnName) {
        return getTextOfElement(ticketCell(getColumnIndex(columnName)));
    }

    public String getTicketID() {
        return driver.getCurrentUrl().replaceAll(".*id=(\\d+).*", "$1");
    }

    public String getTicketSummary() {
        return String.join(" | ",
                getTicketInfo(Constant.TableHeader.DEPART_STATION.getValue()),
                getTicketInfo(Constant.TableHeader.ARRIVE_STATION.getValue()),
                getTicketInfo(Constant.TableHeader.SEAT_TYPE.getValue()),
                getTicketInfo(Constant.TableHeader.DEPART_DATE.getValue()),
                getTicketInfo(Constant.TableHeader.TICKET_AMOUNT.getValue()));
    }
}
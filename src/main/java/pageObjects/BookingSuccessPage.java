package pageObjects;

import common.Constant;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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

    @Step("Get successfully booking message")
    public String getSuccessMessage() {
        String message = getTextOfElement(successMessage);
        Allure.getLifecycle().updateStep(
                step -> step.setName("Get successfully booking message: " + message)
        );
        return message;
    }

    // Get information by column header
    public String getTicketInfo(String columnName) {
        return getTextOfElement(ticketCell(getColumnIndex(columnName)));
    }

    @Step("Get booked ticket ID")
    public String getTicketID() {

        String id = driver.getCurrentUrl().replaceAll(".*id=(\\d+).*", "$1");
        Allure.getLifecycle().updateStep(
                step -> step.setName("Get booked ticket ID: " + id)
        );
        return id;
    }

    @Step("Get ticket")
    public String getTicketSummary() {
        return String.join(" | ",
                getTicketInfo(Constant.TableHeader.DEPART_STATION.getValue()),
                getTicketInfo(Constant.TableHeader.ARRIVE_STATION.getValue()),
                getTicketInfo(Constant.TableHeader.SEAT_TYPE.getValue()),
                getTicketInfo(Constant.TableHeader.DEPART_DATE.getValue()),
                getTicketInfo(Constant.TableHeader.TICKET_AMOUNT.getValue()));
    }
}
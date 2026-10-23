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
        return getText(successMessage);
    }

    // Get information by column header
    public String getTicketInfo(String columnName) {
        return getText(ticketCell(getColumnIndex(columnName)));
    }

    //Get ticket ID from URL
    public String getTicketID() {
        return driver.getCurrentUrl().replaceAll(".*id=(\\d+).*", "$1");
    }

    // Check booked ticket information
    public void verifyBookingSuccess(String departStation, String arriveStation,
                                     String seatType, String departDate, String ticketAmount) {

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(getSuccessMessage(),
                Constant.CONFIRMATION_MESSAGE,
                "Thông báo đặt vé không đúng");

        softAssert.assertEquals(getTicketInfo(
                        Constant.TableHeader.DEPART_STATION.getValue()),
                departStation, "Ga đi không đúng");

        softAssert.assertEquals(getTicketInfo(
                        Constant.TableHeader.ARRIVE_STATION.getValue()),
                arriveStation, "Ga đến không đúng");

        softAssert.assertEquals(getTicketInfo(
                        Constant.TableHeader.SEAT_TYPE.getValue()),
                seatType, "Loại ghế không đúng");

        softAssert.assertEquals(getTicketInfo(
                        Constant.TableHeader.DEPART_DATE.getValue()),
                departDate, "Ngày đi không đúng");

        softAssert.assertEquals(getTicketInfo(
                        Constant.TableHeader.TICKET_AMOUNT.getValue()),
                ticketAmount, "Số lượng vé không đúng");

        softAssert.assertAll();
    }

}
package testcases;

import common.Constant;
import common.DriverManager;
import enums.SeatType;
import enums.Station;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class CancelBookingTest extends BaseTest {

    @Test(description = "TC16: User can cancel a ticket")
    public void tc16_cancelTicket() {

        // Constructor
        BasePage basePage = new BasePage(DriverManager.getDriver());
        LoginPage  loginPage = new LoginPage(DriverManager.getDriver());
        BookticketPage  bookTicketPage = new BookticketPage(DriverManager.getDriver());
        MyticketPage   myTicketPage = new MyticketPage(DriverManager.getDriver());
        BookingSuccessPage bookSuccessPage = new BookingSuccessPage(DriverManager.getDriver());

        // Test Data
        String departDate = LocalDate.now().plusDays(7)
                .format(DateTimeFormatter.ofPattern("M/d/yyyy"));
        String departStation = Station.DANANG.getValue();
        String arriveStation = Station.HUE.getValue();
        String seatType = SeatType.SOFT_SEAT.getValue();
        String ticketAmount = "1";

        basePage.clickOnLoginTab();

        loginPage.login(
                Constant.LOGIN_USERNAME,
                Constant.LOGIN_PASSWORD
        );

        basePage.clickOnBookTicketTab();

        bookTicketPage.bookTicket(departDate, departStation, arriveStation, seatType, ticketAmount);

        String ticketID = bookSuccessPage.getTicketID();

        Assert.assertEquals(bookSuccessPage.getSuccessMessage(), Constant.CONFIRMATION_MESSAGE,
                "Confirmation message is wrong");

        String expectedSummary = String.join(" | ",
                departStation, arriveStation, seatType, departDate, ticketAmount);
        Assert.assertEquals(bookSuccessPage.getTicketSummary(), expectedSummary,
                "Ticket information does not match with input information");

        basePage.clickOnMyticketTab();

        Assert.assertTrue(myTicketPage.isTicketDisplayed(ticketID),
                "Ticket " + ticketID + " does not display in My ticket table");

        myTicketPage.cancelTicket(departStation, arriveStation, seatType, departDate, ticketAmount);

        Assert.assertTrue(myTicketPage.isTicketDeleted(ticketID),
                "Ticket " + ticketID + " still displays in My ticket table after cancellation");
    }
}

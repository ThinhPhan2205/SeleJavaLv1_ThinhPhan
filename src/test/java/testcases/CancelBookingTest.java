package testcases;

import common.Constant;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class CancelBookingTest extends BaseTest {

    @Test(description = "TC16: User can cancel a ticket")
    public void tc16_cancelTicket() {

        // Constructor
        BasePage basePage = new BasePage(driver);
        LoginPage  loginPage = new LoginPage(driver);
        BookticketPage  bookTicketPage = new BookticketPage(driver);
        MyticketPage   myTicketPage = new MyticketPage(driver);
        BookingSuccessPage bookSuccessPage = new BookingSuccessPage(driver);

        // Test Data
        String departDate = LocalDate.now().plusDays(7)
                .format(DateTimeFormatter.ofPattern("M/d/yyyy"));
        String departStation = Constant.Station.DANANG.getValue();
        String arriveStation = Constant.Station.HUE.getValue();
        String seatType = Constant.SeatType.SOFT_SEAT.getValue();
        String ticketAmount = "1";

        basePage.clickOnLoginTab();

        loginPage.login(
                Constant.AccountToLogin.USERNAME.getValue(),
                Constant.AccountToLogin.PASSWORD.getValue()
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

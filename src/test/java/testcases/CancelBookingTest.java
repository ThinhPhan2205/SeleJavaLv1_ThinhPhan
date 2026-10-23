package testcases;

import common.Constant;
import org.testng.annotations.Test;
import pageObjects.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class CancelBookingTest extends BaseTest {

    @Test(description = "TC16: User can cancel a ticket")
    public void tc16_cancelTicket() {

        // Test Data
        String DATE = LocalDate.now().plusDays(7)
                .format(DateTimeFormatter.ofPattern("M/d/yyyy"));
        String DEPART = Constant.Station.DANANG.getValue();
        String ARRIVE = Constant.Station.HUE.getValue();
        String SEAT   = Constant.SeatType.SOFT_SEAT.getValue();
        String AMOUNT = "2";

        // Constructor
        BookingSuccessPage bookSuccessPage = new BookingSuccessPage(driver);

        // 1. Navigate to Railway
        GeneralPage homePage = new GeneralPage(driver);

        // 2. Login with a valid account
        LoginPage loginPage = homePage.clickOnLoginTab();
        loginPage.login(
                Constant.AccountToLogin.USERNAME.getValue(),
                Constant.AccountToLogin.PASSWORD.getValue()
        );

        // 3. Book a ticket
        BookticketPage bookTicketPage = homePage.clickOnBookTicketTab();
        bookTicketPage.bookTicket(DATE, DEPART, ARRIVE, SEAT, AMOUNT);

        // Get ticket ID
        String ticketID = bookSuccessPage.getTicketID();

        // Check booked ticket information
        bookSuccessPage.verifyBookingSuccess(DEPART, ARRIVE, SEAT, DATE, AMOUNT);

        // 4. Click on "My ticket" tab
        MyticketPage myTicketPage = homePage.clickOnMyticketTab();

        // Check if booked ticket is added to My ticket table
        myTicketPage.isTicketDisplayed(ticketID);

        // 5. Click on "Cancel" button of ticket which user want to cancel.
        // 6. Click on "OK" button on Confirmation message "Are you sure?"

        myTicketPage.cancelTicket(DEPART, ARRIVE, SEAT, DATE, AMOUNT);

        // Expectation: The canceled ticket is disappeared
        myTicketPage.checkIsTicketDeleted();
    }
}

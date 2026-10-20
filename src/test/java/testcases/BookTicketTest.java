package testcases;

import common.Constant;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageObjects.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BookTicketTest extends BaseTest {

    @Test
    public void bookTicketSuccessfully() {
        final String departStation = "Sài Gòn";
        final String arriveStation = "Đà Nẵng";
        final String ticketAmount = "2";
        String departDate = LocalDate.now().plusDays(7)
                .format(DateTimeFormatter.ofPattern("M/d/yyyy"));

        GeneralPage generalPage = new GeneralPage(driver);
        LoginPage loginPage = generalPage.goToLoginPage();

        // Step 1: Login Railway with a valid account
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        // Step 2: Click on Timetable tab
        TimetablePage timetablePage = loginPage.goToTimetablePage();

        // Step 3: Click on "check price" link of Sai Gon-Da Nang
        TicketpricePage ticketPricePage = timetablePage.clickCheckPrice(departStation, arriveStation);

        // Step 4: Click on "book ticket" link of "Soft seat"
        BookticketPage bookTicketPage = ticketPricePage.clickBookTicket(Constant.SEAT_SOFTSEAT);

        // Step 5: Choose Depart date is next 7 days and Ticket amount is 2
        bookTicketPage.selectDepartDate(departDate);
        bookTicketPage.selectTicketAmount(ticketAmount);

        // Step 6: Click on "book ticket"
        BookingSuccessPage successPage = bookTicketPage.clickBookTicketButton();

        // Expectation: "Ticket booked successfully!" is shown with corrected ticket info
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(successPage.getSuccessMessage(), Constant.CONFIRMATION_MESSAGE,
                "Thông báo đặt vé không đúng");
        softAssert.assertEquals(successPage.getTicketInfo(Constant.DEPART_STATION_HEADER), departStation,
                "Ga đi không đúng");
        softAssert.assertEquals(successPage.getTicketInfo(Constant.ARRIVE_STATION_HEADER), arriveStation,
                "Ga đến không đúng");
        softAssert.assertEquals(successPage.getTicketInfo(Constant.SEAT_TYPE_HEADER), Constant.SEAT_SOFTSEAT,
                "Loại ghế không đúng");
        softAssert.assertEquals(successPage.getTicketInfo(Constant.DEPART_DATE_HEADER), departDate,
                "Ngày đi không đúng");
        softAssert.assertEquals(successPage.getTicketInfo(Constant.TICKET_AMOUNT_HEADER), ticketAmount,
                "Số lượng vé không đúng");
        softAssert.assertAll();
    }
}
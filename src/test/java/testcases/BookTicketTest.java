package testcases;

import common.Constant;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageObjects.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class BookTicketTest extends BaseTest {

    @Test(description = "TC12: User can book 1 ticket at a time")
    public void tc12_userCanBookOneTicketAtATime() {

        // Constructor
        GeneralPage generalPage = new GeneralPage(driver);
        LoginPage loginPage = generalPage.clickOnLoginTab();
        BookingSuccessPage successPage = new  BookingSuccessPage(driver);

        // Test Data
        final String departStation = Constant.Station.NHATRANG.getValue();
        final String arriveStation = Constant.Station.HUE.getValue();
        final String seatType = Constant.SeatType.SOFT_BED_AIR.getValue();
        final String ticketAmount = "1";
        String departDate = LocalDate.now().plusDays(12)
                .format(DateTimeFormatter.ofPattern("M/d/yyyy"));

        // 1. Navigate to QA Railway Website
        // 2. Login with a valid account
        loginPage.login(
                Constant.AccountToLogin.USERNAME.getValue(),
                Constant.AccountToLogin.PASSWORD.getValue()
        );

        // 3. Click on Bookticket tab
        BookticketPage bookTicketPage = generalPage.clickOnBookTicketTab();

        // 4. Choose Depart date is next 12 days
        // 5. Select "Nha Trang" for "Depart from" and "Huế" for "Arrive at"
        // 6. Select "Soft bed with air conditioner" for "Seat type"
        // 7. Select "1" for "Ticket amount"
        // 8. Click on "book ticket"
        bookTicketPage.bookTicket(
                departDate,
                departStation,
                arriveStation,
                seatType,
                ticketAmount
        );

        // Expectation: "Ticket booked successfully!" is shown with corrected ticket info
        successPage.verifyBookingSuccess(departStation, arriveStation, seatType, departDate, ticketAmount);
    }

    @Test(description = "TC13: User can book many tickets at a time")
    public void tc13_userCanBookManyTicketsAtATime() {

        // Constructor
        GeneralPage generalPage = new GeneralPage(driver);
        LoginPage loginPage = generalPage.clickOnLoginTab();
        BookingSuccessPage successPage = new  BookingSuccessPage(driver);

        // Test Data
        final String departStation = Constant.Station.NHATRANG.getValue();
        final String arriveStation = Constant.Station.SAIGON.getValue();
        final String seatType = Constant.SeatType.SOFT_SEAT_AIR.getValue();
        final String ticketAmount = "3";
        String departDate = LocalDate.now().plusDays(25)
                .format(DateTimeFormatter.ofPattern("M/d/yyyy"));


        // 1. Navigate to QA Railway Website
        // 2. Login with a valid account
        loginPage.login(
                Constant.AccountToLogin.USERNAME.getValue(),
                Constant.AccountToLogin.PASSWORD.getValue()
        );

        // 3. Click on Bookticket tab
        BookticketPage bookTicketPage = generalPage.clickOnBookTicketTab();

        // 4. Choose Depart date is next 25 days
        // 5. Select "Nha Trang" for "Depart from" and "Sài Gòn" for "Arrive at"
        // 6. Select "Soft seat with air conditioner" for "Seat type"
        // 7. Select "5" for "Ticket amount"
        // 8. Click on "book ticket" button
        bookTicketPage.bookTicket(
                departDate,
                departStation,
                arriveStation,
                seatType,
                ticketAmount
        );

        // Expectation: "Ticket booked successfully!" is shown with corrected ticket info
        successPage.verifyBookingSuccess(departStation, arriveStation, seatType, departDate, ticketAmount);
    }

    @Test(description = "TC14: User can check price of ticket from Timetable")
    public void tc14_checkPriceOfTicketFromTimetable() {

        // Test Data
        final String departStation = Constant.Station.DANANG.getValue();
        final String arriveStation = Constant.Station.SAIGON.getValue();
        final String priceTableTitle = "Ticket price from " + departStation + " to " + arriveStation;

        Map<String, String> expectedTicketPrice = new HashMap<>();
        expectedTicketPrice.put("HS","310000");
        expectedTicketPrice.put("SS","335000");
        expectedTicketPrice.put("SSC","360000");
        expectedTicketPrice.put("HB","410000");
        expectedTicketPrice.put("SB","460000");
        expectedTicketPrice.put("SBC","510000");

        // Constructor
        GeneralPage homePage = new GeneralPage(driver);
        TicketpricePage ticketpricePage = new TicketpricePage(driver);
        BookticketPage bookticketPage = new BookticketPage(driver);

        // 1. Login Railway with a valid account
        LoginPage loginPage = homePage.clickOnLoginTab();
        loginPage.login(
                Constant.AccountToLogin.USERNAME.getValue(),
                Constant.AccountToLogin.PASSWORD.getValue()
        );

        // 2. Click on Timetable tab
        TimetablePage timetablePage = loginPage.clickOnTimetableTab();

        // 3. Click on "check price" link of Da Nang-Sai Gon
        TicketpricePage ticketPricePage = timetablePage
                .clickCheckPrice(departStation, arriveStation);

        // Expectation: Price for each seat displays correctly
        bookticketPage.checkTicketTableTitle(priceTableTitle);

        SoftAssert softAssert = new  SoftAssert();
        for (Map.Entry<String, String> entry : expectedTicketPrice.entrySet()) {
            softAssert.assertEquals(ticketpricePage.getSeatTypePrice(entry.getKey()),
                    entry.getValue(),
                    entry.getKey() + " không đúng");
        }
        softAssert.assertAll();
    }

    @Test(description = "TC15: User can book ticket from Timetable")
    public void tc15_bookTicketSuccessfully() {

        // Constructor
        GeneralPage generalPage = new GeneralPage(driver);
        LoginPage loginPage = generalPage.clickOnLoginTab();

        // Test Data
        final String departStation = Constant.Station.QUANGNGAI.getValue();
        final String arriveStation = Constant.Station.HUE.getValue();
        final String seatType = Constant.SeatType.SOFT_SEAT.getValue();
        final String ticketAmount = "2";
        String departDate = LocalDate.now().plusDays(10)
                .format(DateTimeFormatter.ofPattern("M/d/yyyy"));

        // 1. Login Railway with a valid account
        loginPage.login(
                Constant.AccountToLogin.USERNAME.getValue(),
                Constant.AccountToLogin.PASSWORD.getValue()
        );

        // 2. Click on Timetable tab
        TimetablePage timetablePage = loginPage.clickOnTimetableTab();

        // 3. Click on "check price" link of Quang Ngai-Hue
        TicketpricePage ticketPricePage = timetablePage
                .clickCheckPrice(departStation, arriveStation);

        // 4. Click on "book ticket" link of "Soft seat"
        BookticketPage bookTicketPage = ticketPricePage.clickBookTicket(seatType);

        // 5. Choose Depart date is next 10 days and Ticket amount is 2
        bookTicketPage.selectDepartDate(departDate);
        bookTicketPage.selectTicketAmount(ticketAmount);

        // 6. Click on "book ticket"
        BookingSuccessPage successPage = bookTicketPage.clickBookTicketButton();

        // Expectation: "Ticket booked successfully!" is shown with corrected ticket info
        successPage.verifyBookingSuccess(departStation, arriveStation, seatType, departDate, ticketAmount);
    }
}
package testcases;

import common.Constant;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageObjects.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class BookTicketTest extends BaseTest {

    @Test(description = "User can book 1 ticket at a time")
    public void userCanBookOneTicketsAtATime() {

        // Locator
        final String DEPARTSTATION = Constant.Station.NHATRANG.getValue();
        final String ARRIVESTATION = Constant.Station.HUE.getValue();
        final String SEATTYPE = Constant.SeatType.SOFT_BED_AIR.getValue();
        final String ticketAmount = "1";
        String departDate = LocalDate.now().plusDays(12)
                .format(DateTimeFormatter.ofPattern("M/d/yyyy"));

        GeneralPage generalPage = new GeneralPage(driver);
        LoginPage loginPage = generalPage.goToLoginPage();

        // Step 1: Login Railway with a valid account
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        // Step 2: Click on Timetable tab
        TimetablePage timetablePage = loginPage.goToTimetablePage();

        // Step 3: Click on "check price" link of Quang Ngai-Hue
        TicketpricePage ticketPricePage = timetablePage
                .clickCheckPrice(DEPARTSTATION, ARRIVESTATION);

        // Step 4: Click on "book ticket" link of "Soft seat"
        BookticketPage bookTicketPage = ticketPricePage.clickBookTicket(SEATTYPE);

        // Step 5: Choose Depart date is next 25 days and Ticket amount is 3
        bookTicketPage.selectDepartDate(departDate);
        bookTicketPage.selectTicketAmount(ticketAmount);

        // Step 6: Click on "book ticket"
        BookingSuccessPage successPage = bookTicketPage.clickBookTicketButton();

        // Expectation: "Ticket booked successfully!" is shown with corrected ticket info
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(successPage.getSuccessMessage(),
                Constant.CONFIRMATION_MESSAGE,
                "Thông báo đặt vé không đúng");

        softAssert.assertEquals(successPage.getTicketInfo(
                        Constant.TableHeader.DEPART_STATION.getValue()),
                DEPARTSTATION, "Ga đi không đúng");

        softAssert.assertEquals(successPage.getTicketInfo(
                        Constant.TableHeader.ARRIVE_STATION.getValue()),
                ARRIVESTATION, "Ga đến không đúng");

        softAssert.assertEquals(successPage.getTicketInfo(
                        Constant.TableHeader.SEAT_TYPE.getValue()),
                SEATTYPE, "Loại ghế không đúng");

        softAssert.assertEquals(successPage.getTicketInfo(
                        Constant.TableHeader.DEPART_DATE.getValue()),
                departDate, "Ngày đi không đúng");

        softAssert.assertEquals(successPage.getTicketInfo(
                        Constant.TableHeader.TICKET_AMOUNT.getValue()),
                ticketAmount, "Số lượng vé không đúng");
        softAssert.assertAll();
    }

    @Test(description = "User can book many tickets at a time")
    public void userCanBookManyTicketsAtATime() {

        // Locator
        final String DEPARTSTATION = Constant.Station.NHATRANG.getValue();
        final String ARRIVESTATION = Constant.Station.SAIGON.getValue();
        final String SEATTYPE = Constant.SeatType.SOFT_SEAT_AIR.getValue();
        final String ticketAmount = "3";
        String departDate = LocalDate.now().plusDays(25)
                .format(DateTimeFormatter.ofPattern("M/d/yyyy"));

        GeneralPage generalPage = new GeneralPage(driver);
        LoginPage loginPage = generalPage.goToLoginPage();

        // Step 1: Login Railway with a valid account
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        // Step 2: Click on Timetable tab
        TimetablePage timetablePage = loginPage.goToTimetablePage();

        // Step 3: Click on "check price" link of Quang Ngai-Hue
        TicketpricePage ticketPricePage = timetablePage
                .clickCheckPrice(DEPARTSTATION, ARRIVESTATION);

        // Step 4: Click on "book ticket" link of "Soft seat"
        BookticketPage bookTicketPage = ticketPricePage.clickBookTicket(SEATTYPE);

        // Step 5: Choose Depart date is next 25 days and Ticket amount is 3
        bookTicketPage.selectDepartDate(departDate);
        bookTicketPage.selectTicketAmount(ticketAmount);

        // Step 6: Click on "book ticket"
        BookingSuccessPage successPage = bookTicketPage.clickBookTicketButton();

        // Expectation: "Ticket booked successfully!" is shown with corrected ticket info
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(successPage.getSuccessMessage(),
                Constant.CONFIRMATION_MESSAGE,
                "Thông báo đặt vé không đúng");

        softAssert.assertEquals(successPage.getTicketInfo(
                Constant.TableHeader.DEPART_STATION.getValue()),
                DEPARTSTATION, "Ga đi không đúng");

        softAssert.assertEquals(successPage.getTicketInfo(
                Constant.TableHeader.ARRIVE_STATION.getValue()),
                ARRIVESTATION, "Ga đến không đúng");

        softAssert.assertEquals(successPage.getTicketInfo(
                Constant.TableHeader.SEAT_TYPE.getValue()),
                SEATTYPE, "Loại ghế không đúng");

        softAssert.assertEquals(successPage.getTicketInfo(
                Constant.TableHeader.DEPART_DATE.getValue()),
                departDate, "Ngày đi không đúng");

        softAssert.assertEquals(successPage.getTicketInfo(
                Constant.TableHeader.TICKET_AMOUNT.getValue()),
                ticketAmount, "Số lượng vé không đúng");
        softAssert.assertAll();
    }

    @Test(description = "User can check price of ticket from Timetable")
    public void checkPriceOfTicketFromTimetable() {

        //Locator
        final By TicketpriceTitle  = By.xpath("//div[@id='content']//table//th");
        final String DEPARTSTATION = Constant.Station.DANANG.getValue();
        final String ARRIVESTATION = Constant.Station.SAIGON.getValue();
        final String TICKETPRICE_TITLE = "Ticket price from " + DEPARTSTATION + " to " + ARRIVESTATION ;

        Map<String, String> expectedTicketPrice = new HashMap<>();
        expectedTicketPrice.put("HS","310000");
        expectedTicketPrice.put("SS","335000");
        expectedTicketPrice.put("SSC","360000");
        expectedTicketPrice.put("HB","410000");
        expectedTicketPrice.put("SB","460000");
        expectedTicketPrice.put("SBC","510000");


        GeneralPage homePage = new GeneralPage(driver);
        TicketpricePage  ticketpricePage = new TicketpricePage(driver);

        // Step 1: Login Railway with a valid account
        LoginPage loginPage = homePage.goToLoginPage();
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        // Step 2: Click on Timetable tab
        TimetablePage timetablePage = loginPage.goToTimetablePage();

        // Step 3: Click on "check price" link of Da Nang-Sai Gon
        TicketpricePage ticketPricePage = timetablePage
                .clickCheckPrice(DEPARTSTATION, ARRIVESTATION);

        // Check Ticket price table
        String ACTUAL_MESSAGE = homePage.getMessage(TicketpriceTitle);
        homePage.checkMessage(ACTUAL_MESSAGE, TICKETPRICE_TITLE);

        SoftAssert softAssert = new  SoftAssert();
        for (Map.Entry<String, String> entry : expectedTicketPrice.entrySet()) {
            softAssert.assertEquals(ticketpricePage.getSeatTypePrice(entry.getKey()),
                    entry.getValue(),
                    entry.getKey() + " không đúng");
        }
        softAssert.assertAll();
    }

    @Test(description = "User can book ticket from Timetable")
    public void bookTicketSuccessfully() {

        // Locator
        final String DEPARTSTATION = Constant.Station.QUANGNGAI.getValue();
        final String ARRIVESTATION = Constant.Station.HUE.getValue();
        final String SEATTYPE = Constant.SeatType.SOFT_SEAT.getValue();
        final String ticketAmount = "2";
        String departDate = LocalDate.now().plusDays(10)
                .format(DateTimeFormatter.ofPattern("M/d/yyyy"));

        GeneralPage generalPage = new GeneralPage(driver);
        LoginPage loginPage = generalPage.goToLoginPage();

        // Step 1: Login Railway with a valid account
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        // Step 2: Click on Timetable tab
        TimetablePage timetablePage = loginPage.goToTimetablePage();

        // Step 3: Click on "check price" link of Quang Ngai-Hue
        TicketpricePage ticketPricePage = timetablePage
                .clickCheckPrice(DEPARTSTATION, ARRIVESTATION);

        // Step 4: Click on "book ticket" link of "Soft seat"
        BookticketPage bookTicketPage = ticketPricePage.clickBookTicket(SEATTYPE);

        // Step 5: Choose Depart date is next 10 days and Ticket amount is 2
        bookTicketPage.selectDepartDate(departDate);
        bookTicketPage.selectTicketAmount(ticketAmount);

        // Step 6: Click on "book ticket"
        BookingSuccessPage successPage = bookTicketPage.clickBookTicketButton();

        // Expectation: "Ticket booked successfully!" is shown with corrected ticket info
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(successPage.getSuccessMessage(),
                Constant.CONFIRMATION_MESSAGE,
                "Thông báo đặt vé không đúng");

        softAssert.assertEquals(successPage.getTicketInfo(
                Constant.TableHeader.DEPART_STATION.getValue()),
                DEPARTSTATION, "Ga đi không đúng");

        softAssert.assertEquals(successPage.getTicketInfo(
                Constant.TableHeader.ARRIVE_STATION.getValue()
        ), ARRIVESTATION, "Ga đến không đúng");

        softAssert.assertEquals(successPage.getTicketInfo(
                Constant.TableHeader.SEAT_TYPE.getValue()
        ), SEATTYPE, "Loại ghế không đúng");

        softAssert.assertEquals(successPage.getTicketInfo(
                Constant.TableHeader.DEPART_DATE.getValue()
        ), departDate, "Ngày đi không đúng");

        softAssert.assertEquals(successPage.getTicketInfo(
                Constant.TableHeader.TICKET_AMOUNT.getValue()
        ), ticketAmount, "Số lượng vé không đúng");
        softAssert.assertAll();
    }
}
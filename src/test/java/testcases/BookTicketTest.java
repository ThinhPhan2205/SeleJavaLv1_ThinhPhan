package testcases;

import common.CleanTicket;
import common.Constant;
import common.DriverManager;
import enums.SeatType;
import enums.Station;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageObjects.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class BookTicketTest extends BaseTest {

    @Test(description = "TC12: User can book 1 ticket at a time")
    @CleanTicket
    public void tc12_userCanBookOneTicketAtATime() {

        // Constructor
        WebDriver driver = DriverManager.getDriver();
        BasePage basePage = new BasePage(driver);
        BookingSuccessPage successPage = new BookingSuccessPage(driver);
        BookticketPage  bookTicketPage = new BookticketPage(driver);
        LoginPage loginPage = new LoginPage(driver);

        // Test Data
        final String departStation = Station.NHATRANG.getValue();
        final String arriveStation = Station.HUE.getValue();
        final String seatType = SeatType.SOFT_BED_AIR.getValue();
        final String ticketAmount = "1";
        String departDate = LocalDate.now().plusDays(12)
                .format(DateTimeFormatter.ofPattern("M/d/yyyy"));

        basePage.clickOnLoginTab();

        loginPage.login(
                Constant.LOGIN_USERNAME,
                Constant.LOGIN_PASSWORD
        );

        basePage.clickOnBookTicketTab();

        bookTicketPage.bookTicket(departDate, departStation, arriveStation, seatType, ticketAmount);

        Assert.assertEquals(successPage.getSuccessMessage(), Constant.CONFIRMATION_MESSAGE,
                "Confirmation message is wrong");

        String expectedSummary = String.join(" | ",
                departStation, arriveStation, seatType, departDate, ticketAmount);
        Assert.assertEquals(successPage.getTicketSummary(), expectedSummary,
                "Ticket information does not match with input information");
    }

    @Test(description = "TC13: User can book many tickets at a time")
    @CleanTicket
    public void tc13_userCanBookManyTicketsAtATime() {

        // Constructor
        WebDriver driver = DriverManager.getDriver();
        BasePage basePage = new BasePage(driver);
        BookingSuccessPage successPage = new BookingSuccessPage(driver);
        BookticketPage  bookTicketPage = new BookticketPage(driver);
        LoginPage loginPage = new LoginPage(driver);

        // Test Data
        final String departStation = Station.NHATRANG.getValue();
        final String arriveStation = Station.SAIGON.getValue();
        final String seatType = SeatType.SOFT_SEAT_AIR.getValue();
        final String ticketAmount = "3";
        String departDate = LocalDate.now().plusDays(25)
                .format(DateTimeFormatter.ofPattern("M/d/yyyy"));

        basePage.clickOnLoginTab();

        loginPage.login(
                Constant.LOGIN_USERNAME,
                Constant.LOGIN_PASSWORD
        );

        basePage.clickOnBookTicketTab();

        bookTicketPage.bookTicket(departDate, departStation, arriveStation, seatType, ticketAmount);

        Assert.assertEquals(successPage.getSuccessMessage(), Constant.CONFIRMATION_MESSAGE,
                "Confirmation message is wrong");

        String expectedSummary = String.join(" | ",
                departStation, arriveStation, seatType, departDate, ticketAmount);
        Assert.assertEquals(successPage.getTicketSummary(), expectedSummary,
                "Ticket information does not match with input information");
    }

    @Test(description = "TC14: User can check price of ticket from Timetable")
    public void tc14_checkPriceOfTicketFromTimetable() {

        // Constructor
        WebDriver driver = DriverManager.getDriver();
        BasePage basePage = new BasePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        TicketpricePage ticketpricePage = new TicketpricePage(driver);
        BookticketPage bookTicketPage = new BookticketPage(driver);

        // Test Data
        final String departStation = Station.DANANG.getValue();
        final String arriveStation = Station.SAIGON.getValue();
        final String priceTableTitle = "Ticket price from " + departStation + " to " + arriveStation;
        Map<String, String> expectedTicketPrice = new HashMap<>();
        expectedTicketPrice.put("HS","310000");
        expectedTicketPrice.put("SS","335000");
        expectedTicketPrice.put("SSC","360000");
        expectedTicketPrice.put("HB","410000");
        expectedTicketPrice.put("SB","460000");
        expectedTicketPrice.put("SBC","510000");

        basePage.clickOnLoginTab();

        loginPage.login(
                Constant.LOGIN_USERNAME,
                Constant.LOGIN_PASSWORD
        );

        TimetablePage timetablePage = basePage.clickOnTimetableTab();

        timetablePage.clickCheckPrice(departStation, arriveStation);

        Assert.assertEquals(bookTicketPage.getTicketTableTitle(),
                priceTableTitle,
                "Price table title is wrong");

        SoftAssert softAssert = new  SoftAssert();
        for (Map.Entry<String, String> entry : expectedTicketPrice.entrySet()) {
            softAssert.assertEquals(ticketpricePage.getSeatTypePrice(entry.getKey()),
                    entry.getValue(),
                    entry.getKey() + " is wrong");
        }
        softAssert.assertAll();
    }

    @Test(description = "TC15: User can book ticket from Timetable")
    @CleanTicket
    public void tc15_bookTicketSuccessfully() {

        // Constructor
        WebDriver driver = DriverManager.getDriver();
        BasePage basePage = new BasePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        BookingSuccessPage successPage = new BookingSuccessPage(driver);
        TicketpricePage ticketPricePage = new TicketpricePage(driver);
        BookticketPage bookTicketPage = new BookticketPage(driver);
        TimetablePage  timetablePage = basePage.clickOnTimetableTab();

        // Test Data
        final String departStation = Station.QUANGNGAI.getValue();
        final String arriveStation = Station.HUE.getValue();
        final String seatType = SeatType.SOFT_SEAT.getValue();
        final String ticketAmount = "2";
        String departDate = LocalDate.now().plusDays(10)
                .format(DateTimeFormatter.ofPattern("M/d/yyyy"));

        basePage.clickOnLoginTab();

        loginPage.login(
                Constant.LOGIN_USERNAME,
                Constant.LOGIN_PASSWORD
        );

        basePage.clickOnTimetableTab();

        timetablePage.clickCheckPrice(departStation, arriveStation);

        ticketPricePage.clickBookTicket(seatType);

        bookTicketPage.selectDepartDate(departDate);
        bookTicketPage.selectTicketAmount(ticketAmount);

        bookTicketPage.clickBookTicketButton();

        Assert.assertEquals(successPage.getSuccessMessage(), Constant.CONFIRMATION_MESSAGE,
                "Confirmation message is wrong");

        String expectedSummary = String.join(" | ",
                departStation, arriveStation, seatType, departDate, ticketAmount);
        Assert.assertEquals(successPage.getTicketSummary(), expectedSummary,
                "Ticket information does not match with input information");
    }
}
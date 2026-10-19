package common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;

public class BookTicket {
    private static final String railway_URL = "http://saferailway.somee.com/";
    private static final String username = "nmpoliek@sharklasers.com";
    private static final String password = "123123123";

    static final String PAGE_REGISTER    = "Register";
    static final String PAGE_LOGIN       = "Login";
    static final String PAGE_BOOK_TICKET = "Book ticket";
    static final String PAGE_TIMETABLE   = "Timetable";
    static final String PAGE_TICKETPRICE = "Ticket price";
    static final String PAGE_BOOKTICKET  = "Book ticket";
    static final String SEAT_HARDSEAT = "Hard seat";
    static final String SEAT_SOFTSEAT = "Soft seat";
    static final String SEAT_SOFTSEATAIR = "Soft seat with air conditioner";
    static final String SEAT_HARDBED = "Hard bed";
    static final String SEAT_SOFTBED = "Soft bed";
    static final String SEAT_SOFTBEDAIR = "Soft bed with air conditioner";
    static final String departstationcol = "Depart Station";
    static final String arrivestationcol = "Arrive Station";
    static final String checkpricecol = "Check Price";
    static final String bookticketcol = "Book Ticket";




    public static void main(String[] args){

        WebDriver driver = new ChromeDriver();

        // 1. Go to Railway site
        driver.manage().window().maximize();
        driver.get(railway_URL);

        // 2. Go to Login page
        goToPage(driver, PAGE_LOGIN);

        // 3. Login to Railway
        driver.findElement(By.xpath("//*[@id=\"username\"]")).sendKeys(username);
        driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys(password);

        String LoginButton_xpath = "//*[@id='content']/form/fieldset/p/input";
        ClickonButton(driver, By.xpath(LoginButton_xpath));

        // 4. Go to Timetable tab
        goToPage(driver, PAGE_TIMETABLE);

        // 5. Click on "check price" of Sai Gon-Da Nang
        final String depart_station = "Sài Gòn";
        final String arrive_station = "Đà Nẵng";

        goToCheckPrice(driver, depart_station, arrive_station);

        // 6. Click on "book ticket" of "Soft seat"
        ClickBookticketonTicketpricePage(driver, SEAT_SOFTSEAT);

    }

    static void goToPage(WebDriver driver, String tabname) {

        // Không phân biệt hoa thường, bỏ khoảng trắng thừa
        String lower = tabname.trim().toLowerCase();

        driver.findElement(By.xpath("//*[@id='menu']//a[translate(normalize-space(.), "
                + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')='" + lower + "']")).click();

    }

    static void goToCheckPrice(WebDriver driver, String departstation, String arrivestation) {

        String row =
                "//*[@id='content']/div/div/table/tbody//tr["
                        + "td[count(//table//th[normalize-space()='" + departstationcol + "']/preceding-sibling::th)+1]"
                        + "[normalize-space()='" + departstation + "']"
                        + " and "
                        + "td[count(//table//th[normalize-space()='" + arrivestationcol + "']/preceding-sibling::th)+1]"
                        + "[normalize-space()='" + arrivestation + "']"
                        + "]";

        driver.findElement(By.xpath(
                row
                        + "/td[count(//table//th[normalize-space()='" + checkpricecol + "']/preceding-sibling::th)+1]//a"))
                .click();
    }

    static void ClickonButton(WebDriver driver, By locator){

        WebElement element = driver.findElement(locator);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block: 'center'});", element);

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    static void ClickBookticketonTicketpricePage(WebDriver driver, String SeatType){
        driver.findElement(By.xpath(
                "//*[@id='content']//table//tr[.//a]"
                        + "/td[count((//table//*[self::th or self::td]"
                        + "[normalize-space()='" + SeatType + "'])[1]"
                        + "/preceding-sibling::*)+1]//a")
        ).click();
    }


    //*[@id="content"]/table/tbody/tr[3]/td[3]/a

}


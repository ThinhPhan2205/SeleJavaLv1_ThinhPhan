package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TicketpricePage extends GeneralPage {

    // Get Book ticket link by seat type
    private By bookTicketLink(String seatType) {
        return By.xpath("//td[@class='NoBorder'][normalize-space()='" + seatType + "']"
                + "/following-sibling::td/a");
    }
    //*[@id="content"]/table/tbody/tr[2]/td[3]/a
    // Constructor
    public TicketpricePage(WebDriver driver) {
        super(driver);
    }

    // Function
    public BookticketPage clickBookTicket(String seatType) {
        ClickButton(bookTicketLink(seatType));
        return new BookticketPage(driver);
    }
}
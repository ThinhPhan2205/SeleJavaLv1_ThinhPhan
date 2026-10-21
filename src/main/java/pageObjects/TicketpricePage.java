package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TicketpricePage extends GeneralPage {

    // Locator
    private By seattypeRow(int columnIndex) {
        return By.xpath("(//table//tr[2]/td[" + columnIndex + "]");
    }

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
        clickButton(bookTicketLink(seatType));
        return new BookticketPage(driver);
    }

    public String getSeatTypePrice (String seatType) {
        return getText(seattypeRow(getColumnIndex(seatType)));
    }

}
package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TicketpricePage extends BasePage {

    // Locator
    private By seattypeRow(int columnIndex) {
        return By.xpath("//*[@id='content']//table//tr[3]/td[" + columnIndex + "]");
    }

    // Constructor
    public TicketpricePage(WebDriver driver) {
        super(driver);
    }

    // Function

    // Get Book ticket link by seat typeInTextBox
    private By bookTicketLink(String seatType) {
        return By.xpath("//td[@class='NoBorder'][normalize-space()='" + seatType + "']"
                + "/following-sibling::td/a");
    }

    // Click Book ticket link
    public BookticketPage clickBookTicket(String seatType) {
        clickButtonWithJS(bookTicketLink(seatType));
        return new BookticketPage(driver);
    }

    // Get ticket price of each seat typeInTextBox
    public String getSeatTypePrice (String seatType) {
        return getTextOfElement(seattypeRow(getColumnIndexBySeatType(seatType)));
    }

    // Get column index
    protected int getColumnIndexBySeatType(String columnName) {
        By cellLocator = By.xpath(
                "(//table//*[(self::td) and normalize-space()='" + columnName + "'])[1]");
        WebElement cell = findElement(cellLocator);
        return cell.findElements(By.xpath("preceding-sibling::*")).size();
    }

}
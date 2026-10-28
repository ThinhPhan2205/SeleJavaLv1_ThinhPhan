package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MyticketPage extends BasePage {

    // Locator
    private By cancelButton(String ticketId) {
        return By.xpath("//input[@value='Cancel' and @onclick='DeleteTicket(" + ticketId + ");']");
    }

    // Constructor
    public MyticketPage(WebDriver driver) {
        super(driver);
    }

    public boolean isTicketDisplayed(String ticketId) {
        return !driver.findElements(cancelButton(ticketId)).isEmpty();
    }

    // Check cells value
    private String cellCondition(String header, String value) {
        return "td[count(ancestor::table[1]//th[normalize-space()='" + header
                + "']/preceding-sibling::th)+1][normalize-space()='" + value + "']";
    }

    public void cancelTicket(String depart, String arrive, String seat,
                             String date, String amount) {

        By cancelLocator = By.xpath(
                "//table//tr["
                        + cellCondition("Depart Station", depart) + " and "
                        + cellCondition("Arrive Station", arrive) + " and "
                        + cellCondition("Seat Type", seat) + " and "
                        + cellCondition("Depart Date", date) + " and "
                        + cellCondition("Amount", amount)
                        + "]//input[@value='Cancel']");

        findElement(cancelLocator).getAttribute("onclick").replaceAll("\\D+", "");

        clickButtonWithJS(cancelLocator);

        driver.switchTo().alert().accept();
    }

    public boolean isTicketDeleted(String ticketId) {
        return driver.findElements(cancelButton(ticketId)).isEmpty();
    }

    public void cancelFirstTicket(){
        By cancelButton = By.xpath("//table//tr[2]//input[@value='Cancel']");
        clickButtonWithJS(cancelButton);
        driver.switchTo().alert().accept();
    }
}

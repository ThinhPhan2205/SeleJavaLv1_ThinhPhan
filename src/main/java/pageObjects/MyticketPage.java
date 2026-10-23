package pageObjects;

import common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MyticketPage extends GeneralPage {

    // Locator
    private By cancelButton(String ticketId) {
        return By.xpath("//input[@value='Cancel' and @onclick='DeleteTicket(" + ticketId + ");']");
    }
    private String deletedTicketId;

    // Constructor
    public MyticketPage(WebDriver driver) {
        super(driver);
    }

    // Check is ticket displayed in My ticket table
    public boolean isTicketDisplayed(String ticketId) {
        return !driver.findElements(cancelButton(ticketId)).isEmpty();
    }

    // Check cells value
    private String cellCondition(Constant.TableHeader header, String value) {
        return "td[count(ancestor::table[1]//th[normalize-space()='" + header.getValue()
                + "']/preceding-sibling::th)+1][normalize-space()='" + value + "']";
    }

    // Cancel ticket by ID
    public void cancelTicket(String depart, String arrive, String seat,
                             String date, String amount) {

        By cancelLocator = By.xpath(
                "//table//tr["
                        + cellCondition(Constant.TableHeader.DEPART_STATION, depart) + " and "
                        + cellCondition(Constant.TableHeader.ARRIVE_STATION, arrive) + " and "
                        + cellCondition(Constant.TableHeader.SEAT_TYPE, seat) + " and "
                        + cellCondition(Constant.TableHeader.DEPART_DATE, date) + " and "
                        + cellCondition(Constant.TableHeader.TICKET_AMOUNT, amount)
                        + "]//input[@value='Cancel']");

        deletedTicketId = find(cancelLocator).getAttribute("onclick").replaceAll("\\D+", "");

        clickButton(cancelLocator);

        driver.switchTo().alert().accept();
    }

    public void checkIsTicketDeleted() {
        driver.findElements(By.xpath(
                "//input[@value='Cancel' and contains(@onclick,'" + deletedTicketId + "')]"
        ));
    }

}

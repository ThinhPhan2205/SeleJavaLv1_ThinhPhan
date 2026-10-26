package pageObjects;

import common.Constant;
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
    private String cellCondition(Constant.TableHeader header, String value) {
        return "td[count(ancestor::table[1]//th[normalize-space()='" + header.getValue()
                + "']/preceding-sibling::th)+1][normalize-space()='" + value + "']";
    }

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

        findElement(cancelLocator).getAttribute("onclick").replaceAll("\\D+", "");

        clickButtonWithJS(cancelLocator);

        driver.switchTo().alert().accept();
    }

    public boolean isTicketDeleted(String ticketId) {
        return driver.findElements(cancelButton(ticketId)).isEmpty();
    }

}

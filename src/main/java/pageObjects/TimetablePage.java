package pageObjects;

import common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TimetablePage extends BasePage {

    // Constructor
    public TimetablePage(WebDriver driver) {
        super(driver);
    }

    // Function

    // Get "check price" link of expected routes
    private By checkPriceLink(int departCol, int arriveCol, int priceCol,
                              String departStation, String arriveStation) {
        return By.xpath("//table//tr[td[" + departCol + "][normalize-space()='" + departStation + "']"
                + " and td[" + arriveCol + "][normalize-space()='" + arriveStation + "']]"
                + "/td[" + priceCol + "]//a");
    }

    // Click "check price" link
    public TicketpricePage clickCheckPrice(String departStation, String arriveStation) {
        int departCol = getColumnIndex(Constant.TableHeader.DEPART_STATION.getValue());
        int arriveCol = getColumnIndex(Constant.TableHeader.ARRIVE_STATION.getValue());
        int priceCol  = getColumnIndex(Constant.TableHeader.CHECK_PRICE.getValue());

        clickButtonWithJS(checkPriceLink(departCol, arriveCol, priceCol, departStation, arriveStation));
        return new TicketpricePage(driver);
    }
}
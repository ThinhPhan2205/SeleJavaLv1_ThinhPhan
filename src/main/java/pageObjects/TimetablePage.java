package pageObjects;

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
    public void clickCheckPrice(String departStation, String arriveStation) {
        int departCol = getColumnIndex("Depart Station");
        int arriveCol = getColumnIndex("Arrive Station");
        int priceCol  = getColumnIndex("Check Price");

        clickButtonWithJS(checkPriceLink(departCol, arriveCol, priceCol, departStation, arriveStation));
    }
}
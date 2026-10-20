package pageObjects;

import common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TimetablePage extends GeneralPage {

    // Get "check price" link of expected routes
    private By checkPriceLink(int departCol, int arriveCol, int priceCol,
                              String departStation, String arriveStation) {
        return By.xpath("//table//tr[td[" + departCol + "][normalize-space()='" + departStation + "']"
                + " and td[" + arriveCol + "][normalize-space()='" + arriveStation + "']]"
                + "/td[" + priceCol + "]//a");
    }

    // Constructor
    public TimetablePage(WebDriver driver) {
        super(driver);
    }

    // Function
    public TicketpricePage clickCheckPrice(String departStation, String arriveStation) {
        int departCol = getColumnIndex(Constant.DEPART_STATION_HEADER);
        int arriveCol = getColumnIndex(Constant.ARRIVE_STATION_HEADER);
        int priceCol  = getColumnIndex(Constant.CHECK_PRICE_HEADER);

        ClickButton(checkPriceLink(departCol, arriveCol, priceCol, departStation, arriveStation));
        return new TicketpricePage(driver);
    }
}
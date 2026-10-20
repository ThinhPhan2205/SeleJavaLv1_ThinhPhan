package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BookingSuccessPage extends BasePage {

    // ===== Locator (cần Inspect để xác nhận lại) =====
    private final By successMessage = By.xpath("//*[@id='content']/h1");

    // Locator có tham số: ô của dòng vé đầu tiên theo vị trí cột
    private By ticketCell(int columnIndex) {
        return By.xpath("(//table//tr[td])[1]/td[" + columnIndex + "]");
    }

    // ===== Constructor =====
    public BookingSuccessPage(WebDriver driver) {
        super(driver);
    }

    // ===== Function: chỉ trả dữ liệu, assert nằm ở test =====
    public String getSuccessMessage() {
        return getText(successMessage);
    }

    // Lấy thông tin vé theo tên cột, ví dụ "Depart Station", "Amount"
    public String getTicketInfo(String columnName) {
        return getText(ticketCell(getColumnIndex(columnName)));
    }
}
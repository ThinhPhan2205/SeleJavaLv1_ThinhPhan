package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    // Constructor
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Wait
    protected WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Find element
    protected WebElement find(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Click button with JS
    protected void clickButton(By locator){

        WebElement element = driver.findElement(locator);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block: 'center'});", element);

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    // Click on links
    protected void clickLink(String linkText) {
        By locator = By.xpath("//a[normalize-space()='" + linkText + "']");
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    // Type in textbox
    protected void type(By locator, String text) {
        WebElement element = find(locator);
        element.clear();
        element.sendKeys(text);
    }

    // Select
    protected void selectByText(By locator, String text) {
        new Select(find(locator)).selectByVisibleText(text);
    }

    // Get content of element
    protected String getText(By locator) {
        return find(locator).getText().trim();
    }

    // Check displayed content
    protected boolean isEqual(String actual, String expected) {
        return actual.trim().equals(expected.trim());
    }

    // Get index of header column
    protected int getColumnIndex(String columnName) {
        List<WebElement> headers = driver.findElements(By.xpath("(//table//tr[th])[1]/th"));
        for (int i = 0; i < headers.size(); i++) {
            if (headers.get(i).getText().trim().equalsIgnoreCase(columnName)) {
                return i + 1;
            }
        }
        throw new NoSuchElementException("Không tìm thấy cột: " + columnName);
    }
}
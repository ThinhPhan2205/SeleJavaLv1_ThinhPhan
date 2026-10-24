package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

import static java.lang.Thread.sleep;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    // Constructor
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Find element
    protected WebElement find(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Select
    protected void selectByText(By locator, String text) {
        if (locator.equals(By.name("ArriveStation"))) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        wait.ignoring(StaleElementReferenceException.class).until(d -> {
            Select select = new Select(d.findElement(locator));
            boolean hasOption = select.getOptions().stream()
                    .anyMatch(o -> o.getText().trim().equals(text));
            if (hasOption) {
                select.selectByVisibleText(text);
                return true;
            }
            return false;
        });
    }

    // Get content of element
    protected String getText(By locator) {
        return find(locator).getText().trim();
    }

    // Click on links
    protected void clickLink(By linkText) {
        wait.until(ExpectedConditions.elementToBeClickable(linkText)).click();
    }

    // Click button with JS
    protected void clickButton(By locator) {

        WebElement element = driver.findElement(locator);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block: 'center'});", element);

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }


    // Type in textbox
    protected void type(By locator, String text) {
        WebElement element = find(locator);
        element.clear();
        element.sendKeys(text);
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

    // Check message
    public void checkMessage(String actualMessage, String expectedMessage) {
        Assert.assertEquals(actualMessage, expectedMessage,
                "Displayed message is wrong");
    }
}
package pageObjects;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static java.lang.Thread.sleep;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    // Constructor
    final By welcomeMessage = By.xpath("//div[@class='account']/strong");
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    private By menuTab(String tabName) {
        String lower = tabName.trim().toLowerCase();
        return By.xpath("//*[@id='menu']//a[translate(normalize-space(.), "
                + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')='" + lower + "']");
    }

    public String getInputValue(By locator) {
        return driver.findElement(locator).getAttribute("value");
    }

    protected WebElement findElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void selectByText(By locator, String text) {
        if (locator.equals(By.name("ArriveStation"))) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
            wait.until(ExpectedConditions.elementToBeClickable(locator));
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

    protected String getTextOfElement(By locator) {
        return findElement(locator).getText().trim();
    }

    protected void clickLink(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void clickButtonWithJS(By locator) {

        WebElement element = driver.findElement(locator);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block: 'center'});", element);

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    protected void typeInTextBox(By locator, String text) {
        WebElement element = findElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected int getColumnIndex(String columnName) {
        List<WebElement> headers = driver.findElements(By.xpath("(//table//tr[th])[1]/th"));
        for (int i = 0; i < headers.size(); i++) {
            if (headers.get(i).getText().trim().equalsIgnoreCase(columnName)) {
                return i + 1;
            }
        }
        throw new NoSuchElementException("Can not found column: " + columnName);
    }

    private void clickOnTab(String tabName) {
        clickButtonWithJS(menuTab(tabName));
    }

    @Step("Click on Login tab")
    public LoginPage clickOnLoginTab() {
        clickOnTab("Login");
        return new LoginPage(driver);
    }

    @Step("Click on Register tab")
    public RegisterPage clickOnRegisterTab() {
        clickOnTab("Register");
        return new RegisterPage(driver);
    }

    @Step("Click on My ticket tab")
    public MyticketPage clickOnMyticketTab() {
        clickOnTab("My ticket");
        return new MyticketPage(driver);
    }

    @Step("Click on Timetable tab")
    public TimetablePage clickOnTimetableTab() {
        clickOnTab("Timetable");
        return new TimetablePage(driver);
    }

    @Step("Click on Book ticket tab")
    public BookticketPage clickOnBookTicketTab() {
        clickOnTab("Book ticket");
        return new BookticketPage(driver);
    }

    @Step("Click on Log out tab")
    public LogoutPage clickOnLogoutTab() {
        clickOnTab("Log out");
        return new LogoutPage(driver);
    }

    @Step("Click on FAQ tab")
    public FAQPage clickOnFAQTab() {
        clickOnTab("FAQ");
        return new FAQPage(driver);
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }

    @Step("Get welcome message")
    public String getWelcomeMessage() {
        String welcomeMess = getTextOfElement(welcomeMessage);
        Allure.getLifecycle().updateStep(step -> step.setName("Get welcome message: " + welcomeMess));
        return welcomeMess;
    }

    @Step("Check {tabName} tab disappeared from menu bar")
    public boolean isTabDisappeared(String tabName) {
        By tabXPath = By.xpath("//div[@id='menu']//a[span='" + tabName + "']");
        try {
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(tabXPath));
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Check is user at Railway site or not")
    public boolean isAtRailwayPage() {
        try {
            wait.until(driver -> driver.getCurrentUrl().toLowerCase()
                    .contains("railway".toLowerCase()));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void switchToMailboxPage() {
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
            if (driver.getCurrentUrl().contains("guerrillamail")) {
                return;
            }
        }
        throw new NoSuchWindowException("Guerrilla Mail site not found");
    }
}
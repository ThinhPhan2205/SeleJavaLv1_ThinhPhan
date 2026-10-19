package common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterOnRailway {

    private static final String railway_URL = "http://saferailway.somee.com/";
    private static final String mailbox_URL = "https://www.guerrillamail.com/inbox";
    private static final String password = "123123123";
    private static final String pid = "123123123";

    public static void main(String[] args){

        WebDriver driver = new ChromeDriver();
        WebDriverWait mailWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 1. Get email on Guerrilla site
        driver.manage().window().maximize();
        driver.get(mailbox_URL);
        String mailTab = driver.getWindowHandle();

        driver.findElement(By.xpath("//*[@id=\"use-alias\"]")).click();
        String email = driver.findElement(By.xpath("//*[@id=\"email-widget\"]")).getText().trim();

        String EXPECTED_MESSAGE = "Please confirm your account " + email;

        // 2. Go to Railway site
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(railway_URL);
        String railwayTab = driver.getWindowHandle();

        // 2. Go to Register page
        driver.findElement(By.xpath("//*[@id=\"menu\"]/ul/li[7]/a/span")).click();

        // 3. Register new account
        driver.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys(email);
        driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys(password);
        driver.findElement(By.xpath("//*[@id=\"confirmPassword\"]")).sendKeys(password);
        driver.findElement(By.xpath("//*[@id=\"pid\"]")).sendKeys(pid);
        driver.findElement(By.xpath("//*[@id=\"RegisterForm\"]/fieldset/p/input")).click();

        // 4. Confirm via mailbox on Guerrilla
        driver.switchTo().window(mailTab);
        By confirmMailRow = By.xpath(
                "//*[@id=\"email_list\"]//tr[contains(translate(., 'CONFIRM', 'confirm'), 'confirm')]");
        WebElement mailRow = mailWait.until(ExpectedConditions.elementToBeClickable(confirmMailRow));
        mailRow.click();

        String ACTUAL_MESSAGE = driver.findElement(By.xpath("//*[@id=\"display_email\"]/div/div[2]/h3")).getText();

        if (ACTUAL_MESSAGE.equals(EXPECTED_MESSAGE)) {
            System.out.println("RESULT: PASSED");
        } else {
            System.out.println("RESULT: FAILED");
        }

        // 5. Close browser
        driver.quit();

    }
}
package testcases;

import common.Constant;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();
    protected WebDriver driver;

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        DRIVER.set(driver);
        driver.manage().window().maximize();
        driver.get(Constant.RAILWAY_URL);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (DRIVER.get() != null) {
            DRIVER.get().quit();
            DRIVER.remove();
        }
    }
}
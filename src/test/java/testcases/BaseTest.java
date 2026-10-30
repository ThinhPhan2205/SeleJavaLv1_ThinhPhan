package testcases;

import common.CleanTicket;
import common.Constant;
import common.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pageObjects.BasePage;
import pageObjects.MyticketPage;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        WebDriver driver = new ChromeDriver();
        DriverManager.setDriver(driver);
        driver.manage().window().maximize();
        driver.get(Constant.RAILWAY_URL);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        WebDriver currentDriver = DriverManager.getDriver();
        if (currentDriver == null) {
            return;
        }
        try {
            boolean needCleanup = result.getMethod().getConstructorOrMethod()
                    .getMethod().isAnnotationPresent(CleanTicket.class);
            if (needCleanup) {
                new BasePage(currentDriver).clickOnMyticketTab();
                new MyticketPage(currentDriver).cancelFirstTicket();
            }
        } catch (Exception e) {
                System.out.println("Cleanup failed for " + result.getName());
                e.printStackTrace();
        } finally {
            currentDriver.quit();
            DriverManager.removeDriver();
        }
    }
}
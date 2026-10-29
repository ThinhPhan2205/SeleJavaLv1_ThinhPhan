package testcases;

import common.Constant;
import common.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.*;

public class LogoutTest extends BaseTest {

    @Test(description = "TC06: User is redirected to Home page after logging out")
    public void tc06_LogoutAndRedirectToHomePage() {

        // Constructor
        WebDriver driver = DriverManager.getDriver();
        BasePage basePage = new BasePage(driver);
        HomePage homePage = new HomePage(driver);
        LoginPage  loginPage = new LoginPage(driver);

        driver.manage().window().maximize();
        driver.get(Constant.RAILWAY_URL);

        basePage.clickOnLoginTab();

        loginPage.login(
                Constant.LOGIN_USERNAME,
                Constant.LOGIN_PASSWORD
        );

        FAQPage faqPage = basePage.clickOnFAQTab();

        LogoutPage logoutPage = basePage.clickOnLogoutTab();

        // 5. Check if Home page displays
        Assert.assertTrue(homePage.isAtHomePage(), "Not at Home page");

        // 6. Check if "Log out" tab is disappeared
        Assert.assertTrue(basePage.isTabDisappeared("Log out"),
                "Tab 'Log out' should disappear but it is still displayed");

    }

}

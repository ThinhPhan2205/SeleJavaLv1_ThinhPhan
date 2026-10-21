package testcases;

import common.Constant;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.*;

public class LogoutTest extends BaseTest {

    @Test
    public void LogoutAndRedirectToHomePage() {

        GeneralPage generalPage = new GeneralPage(driver);

        // 1. Navigate to Railway
        driver.manage().window().maximize();
        driver.get(Constant.RAILWAY_URL);

        // 2. Login with valid username and password
        LoginPage loginPage = generalPage.goToLoginPage();
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        // 3. Click on "FAQ" tab
        FAQPage  faqPage = loginPage.goToFAQPage();

        // 4. Click on "Log out" tab
        LogoutPage logoutPage = generalPage.goToLogoutPage();

        // 5. Check if Home page displays
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isAtHomePage(), "Không ở trang Home");

        // 6. Check if "Log out" tab is disappeared
        final String logoutTab = "Log out";
        generalPage.isTabDisappeared(logoutTab);





    }

}

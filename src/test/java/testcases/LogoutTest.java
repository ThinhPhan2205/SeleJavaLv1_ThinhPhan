package testcases;

import common.Constant;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.*;

public class LogoutTest extends BaseTest {

    @Test(description = "TC06: User is redirected to Home page after logging out")
    public void tc06_LogoutAndRedirectToHomePage() {

        // Constructor
        GeneralPage generalPage = new GeneralPage(driver);

        // 1. Navigate to Railway
        driver.manage().window().maximize();
        driver.get(Constant.RAILWAY_URL);

        // 2. Login with valid username and password
        LoginPage loginPage = generalPage.clickOnLoginTab();
        loginPage.login(
                Constant.AccountToLogin.USERNAME.getValue(),
                Constant.AccountToLogin.PASSWORD.getValue()
        );

        // 3. Click on "FAQ" tab
        FAQPage faqPage = loginPage.clickOnFAQTab();

        // 4. Click on "Log out" tab
        LogoutPage logoutPage = generalPage.clickOnLogoutTab();

        // 5. Check if Home page displays
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isAtHomePage(), "Not at Home page");

        // 6. Check if "Log out" tab is disappeared
        final String logoutTab = "Log out";
        Assert.assertTrue(generalPage.isTabDisappeared(logoutTab),
                "Tab 'Log out' should disappear but it is still displayed");

    }

}

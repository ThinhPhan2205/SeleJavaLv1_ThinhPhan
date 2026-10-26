package testcases;

import common.Constant;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.*;

public class LogoutTest extends BaseTest {

    @Test(description = "TC06: User is redirected to Home page after logging out")
    public void tc06_LogoutAndRedirectToHomePage() {

        // Constructor
        BasePage basePage = new BasePage(driver);
        HomePage homePage = new HomePage(driver);
        LoginPage  loginPage = new LoginPage(driver);

        driver.manage().window().maximize();
        driver.get(Constant.RAILWAY_URL);

        basePage.clickOnLoginTab();
        loginPage.login(
                Constant.AccountToLogin.USERNAME.getValue(),
                Constant.AccountToLogin.PASSWORD.getValue()
        );

        FAQPage faqPage = basePage.clickOnFAQTab();

        LogoutPage logoutPage = basePage.clickOnLogoutTab();

        // 5. Check if Home page displays
        Assert.assertTrue(homePage.isAtHomePage(), "Not at Home page");

        // 6. Check if "Log out" tab is disappeared
        Assert.assertTrue(basePage.isTabDisappeared(Constant.MenuTab.LOGOUT.getValue()),
                "Tab 'Log out' should disappear but it is still displayed");

    }

}

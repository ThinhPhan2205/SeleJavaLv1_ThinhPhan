package testcases;

import common.Constant;
import common.DriverManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObjects.*;

public class LoginTest extends BaseTest {

    private BasePage basePage;
    private LoginPage loginPage;

    @BeforeMethod
    public void initPages() {
        basePage = new BasePage(DriverManager.getDriver());
        loginPage = new LoginPage(DriverManager.getDriver());
    }

    @Test(description = "TC1: User can log into Railway with valid username and password")
    public void tc01_loginWithValidAccount() {

        basePage.clickOnLoginTab();

        loginPage.login(
                Constant.LOGIN_USERNAME,
                Constant.LOGIN_PASSWORD
        );

        Assert.assertEquals(basePage.getWelcomeMessage(),
                Constant.WELCOME_MESSAGE,
                "Welcome message is wrong");
    }

    @Test(description = "TC2: User cannot login with blank Username textbox")
    public void tc02_loginWithBlankUsername() {

        basePage.clickOnLoginTab();

        loginPage.login(
                Constant.BLANK_INPUT,
                Constant.LOGIN_PASSWORD
        );

        Assert.assertEquals(loginPage.getLoginMessage(),
                Constant.INVALID_LOGIN_VALUE_MESSAGE,
                "Error login message is wrong");
    }

    @Test(description = "TC3: User cannot log into Railway with invalid password")
    public void tc03_loginWithInvalidPassword() {

        basePage.clickOnLoginTab();

        loginPage.login(
                Constant.LOGIN_USERNAME,
                Constant.INVALID_PASSWORD
        );

        Assert.assertEquals(loginPage.getLoginMessage(),
                Constant.INVALID_LOGIN_VALUE_MESSAGE,
                "Error login message is wrong");
    }

    @Test(description = "TC04: System shows message when user enters wrong password many times")
    public void tc04_loginWithWrongPasswordManyTimes() {

        // Test Data
        final int loginAttempt = 3;

        basePage.clickOnLoginTab();

        loginPage.login(
                Constant.LOGIN_USERNAME,
                Constant.WRONG_PASSWORD
        );

        Assert.assertEquals(loginPage.getLoginMessage(),
                Constant.WRONG_ACCOUNT_LOGIN_MESSAGE,
                "Error login message is wrong");

        // 5. Repeat step 3 and 4 three more times
        loginPage.repeatLoginAction(loginAttempt,
                Constant.LOGIN_USERNAME,
                Constant.WRONG_PASSWORD
        );

        Assert.assertEquals(loginPage.getLoginMessage(),
                Constant.DISABLED_LOGIN_WARNING,
                "Error login message is wrong");
    }

    @Test(description = "TC05: User can't login with an account hasn't been activated")
    public void tc05_loginWithInactivatedAccount() {

        basePage.clickOnLoginTab();

        loginPage.login(
                Constant.INACTIVATED_EMAIL,
                Constant.LOGIN_PASSWORD
        );

        Assert.assertEquals(loginPage.getLoginMessage(),
                Constant.WRONG_ACCOUNT_LOGIN_MESSAGE,
                "Error login message is wrong");
    }
}

package testcases;

import common.Constant;
import org.testng.annotations.Test;
import pageObjects.GeneralPage;
import pageObjects.LoginPage;

public class LoginTest extends BaseTest {

    @Test(description = "TC1: User can log into Railway with valid username and password")
    public void tc01_loginWithValidAccount() {

        // 1. Navigate to Railway site
        // 2. Click on "Login" tab
        GeneralPage generalPage = new GeneralPage(driver);
        LoginPage loginPage = generalPage.clickOnLoginTab();

        // 3. Enter valid email and password
        // 4. Click on "Login" button
        loginPage.login(
                Constant.AccountToLogin.USERNAME.getValue(),
                Constant.AccountToLogin.PASSWORD.getValue()
        );

        // Expectation: Welcome user message is displayed
        generalPage.checkWelcomeMessage();
    }

    @Test(description = "TC2: User cannot login with blank Username textbox")
    public void tc02_loginWithBlankUsername() {

        // 1. Navigate to Railway site
        // 2. Click on "Login" tab
        GeneralPage homePage = new GeneralPage(driver);
        LoginPage loginPage = homePage.clickOnLoginTab();

        // 3. User doesn't type any words into "Username" textbox but enter valid information into "Password" textbox
        // 4. Click on "Login" button
        loginPage.login(
                Constant.InvalidInputs.BLANK_INPUT.getValue(),
                Constant.AccountToLogin.PASSWORD.getValue()
        );

        // Expectation: User can't log in and message
        // "There was a problem with your login and/or errors exist in your form. " appears
        loginPage.checkLoginMessage(Constant.INVALID_LOGIN_VALUE_MESSAGE);
    }

    @Test(description = "TC3: User cannot log into Railway with invalid password")
    public void tc03_loginWithInvalidPassword() {

        // 1. Navigate to Railway site
        // 2. Click on "Login" tab
        GeneralPage generalPage = new GeneralPage(driver);
        LoginPage loginPage = generalPage.clickOnLoginTab();

        // 3. Enter username and password of account hasn't been activated
        // 4. Click on "Login" button
        loginPage.login(
                Constant.AccountToLogin.USERNAME.getValue(),
                Constant.InvalidInputs.INVALID_PASSWORD.getValue()
        );

        // Expectation: Error message "There was a problem with your login and/or errors exist in your form." is displayed
        loginPage.checkLoginMessage(Constant.INVALID_LOGIN_VALUE_MESSAGE);

    }

    @Test(description = "TC04: System shows message when user enters wrong password many times")
    public void tc04_loginWithWrongPasswordManyTimes() {

        // Test Data
        final int loginAttempt = 4;

        // 1. Navigate to Railway site
        // 2. Click on "Login" tab
        GeneralPage generalPage = new GeneralPage(driver);
        LoginPage loginPage = generalPage.clickOnLoginTab();

        // 3. Enter username and password of account hasn't been activated
        // 4. Click on "Login" button
        // Expectation: "Invalid username or password. Please try again" is shown
        // 5. Repeat step 3 and 4 three more times
        // Expectation: User can't login and message
        // "You have used 4 out of 5 login attempts.
        // After all 5 have been used, you will be unable to login for 15 minutes." appears
        loginPage.repeatLoginActionAndCheckMessage(loginAttempt);
    }

    @Test(description = "TC05: User can't login with an account hasn't been activated")
    public void tc05_loginWithInactivatedAccount() {

        // 1. Navigate to Railway site
        // 2. Click on "Login" tab
        GeneralPage generalPage = new GeneralPage(driver);
        LoginPage loginPage = generalPage.clickOnLoginTab();

        // 3. Enter username and password of account hasn't been activated
        // 4. Click on "Login" button
        loginPage.login(
                Constant.InvalidInputs.INACTIVATED_EMAIL.getValue(),
                Constant.AccountToLogin.PASSWORD.getValue()
        );

        // Expectation: User can't log in and message "Invalid username or password. Please try again." appears
        loginPage.checkLoginMessage(Constant.WRONG_ACCOUNT_LOGIN_MESSAGE);

    }
}

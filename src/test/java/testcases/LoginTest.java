package testcases;

import common.Constant;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import pageObjects.GeneralPage;
import pageObjects.LoginPage;

public class LoginTest extends BaseTest {

    // Locator
    private final String BLANK_USERNAME = "";
    private final String NONEXISTED_USERNAME = "abc@gmail.com";
    private final String INVALID_PASSWORD = "123";
    private final String WRONG_PASSWORD = "123456789";
    private final By errorMessage  = By.xpath("//*[@id='content']/p");

    @Test(description = "TC1: User can log into Railway with valid username and password")
    public void loginWithValidAccount() {

        // Locator
        final By welcomeMessage = By.xpath("//div[@class='account']/strong");

        // 1. Navigate to Railway site
        // 2. Click on "Login" tab
        GeneralPage generalPage = new GeneralPage(driver);
        LoginPage loginPage = generalPage.goToLoginPage();

        // 3. Enter valid email and password
        // 4. Click on "Login" button
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        // 5. Check Welcome message
        String ACTUAL_MESSAGE = generalPage.getMessage(welcomeMessage);
        generalPage.checkMessage(ACTUAL_MESSAGE, Constant.WELCOME_MESSAGE);
    }

    @Test(description = "TC2: User cannot login with blank Username textbox")
    public void tc02_loginWithBlankUsername() {

        // 1. Navigate to Railway site
        // 2. Click on "Login" tab
        GeneralPage homePage = new GeneralPage(driver);
        LoginPage loginPage = homePage.goToLoginPage();

        // 3. Enter username and password of account hasn't been activated
        // 4. Click on "Login" button
        loginPage.login(BLANK_USERNAME, Constant.PASSWORD);

        // 5. Check Error Message
        String ACTUAL_MESSAGE = homePage.getMessage(errorMessage);
        homePage.checkMessage(ACTUAL_MESSAGE, Constant.INVALID_LOGIN_VALUE_MESSAGE);
    }

    @Test(description = "TC3: User cannot log into Railway with invalid password")
    public void tc03_loginWithInvalidPassword() {

        // 1. Navigate to Railway site
        // 2. Click on "Login" tab
        GeneralPage generalPage = new GeneralPage(driver);
        LoginPage loginPage = generalPage.goToLoginPage();

        // 3. Enter username and password of account hasn't been activated
        // 4. Click on "Login" button
        loginPage.login(Constant.USERNAME, INVALID_PASSWORD);

        // 5. Check Error Message
        String ACTUAL_MESSAGE = generalPage.getMessage(errorMessage);
        generalPage.checkMessage(ACTUAL_MESSAGE, Constant.INVALID_LOGIN_VALUE_MESSAGE);
    }

    @Test(description = "TC04: System shows message when user enters wrong password many times")
    public void tc04_loginWithWrongPasswordManyTimes() {

        // Locator
        final int loginAttempt = 4;

        // 1. Navigate to Railway site
        // 2. Click on "Login" tab
        GeneralPage generalPage = new GeneralPage(driver);
        LoginPage loginPage = generalPage.goToLoginPage();

        // 3. Enter username and password of account hasn't been activated
        // 4. Click on "Login" button
        for (int i = 0; i < loginAttempt; i++) {
            loginPage.login(Constant.USERNAME, WRONG_PASSWORD);

            // Check error message
            if (i == 0) {
                String ACTUAL_MESSAGE = generalPage.getMessage(errorMessage);
                generalPage.checkMessage(ACTUAL_MESSAGE, Constant.INVALID_LOGIN_VALUE_MESSAGE);

            // Check warning message
            } else if (i == (loginAttempt - 1)) {
                String ACTUAL_MESSAGE = generalPage.getMessage(errorMessage);
                generalPage.checkMessage(ACTUAL_MESSAGE, Constant.DISABLED_LOGIN_WARNING);
            }
        }
    }

    @Test(description = "TC05: User can't login with an account hasn't been activated")
    public void tc05_loginWithInactivatedAccount() {

        // 1. Navigate to Railway site
        // 2. Click on "Login" tab
        GeneralPage generalPage = new GeneralPage(driver);
        LoginPage loginPage = generalPage.goToLoginPage();

        // 3. Enter username and password of account hasn't been activated
        // 4. Click on "Login" button
        loginPage.login(NONEXISTED_USERNAME, Constant.PASSWORD);

        // 5. Check Error Message
        String ACTUAL_MESSAGE = generalPage.getMessage(errorMessage);
        generalPage.checkMessage(ACTUAL_MESSAGE, Constant.WRONG_ACCOUNT_LOGIN_MESSAGE);
    }
}

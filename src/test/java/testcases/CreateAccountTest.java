package testcases;

import common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WindowType;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.GeneralPage;
import pageObjects.GuerrillaMailPage;
import pageObjects.RegisterPage;

public class CreateAccountTest extends BaseTest {

    //Locator
    final By errorMessage  = By.xpath("//*[@id='content']/p[2]");
    
    @Test(description = "User can't create account with an already in-use email")
    public void registerAccountWithInuseEmail() {

        // 1. Navigate to Railway
        // 2. Click on "Register" tab
        GeneralPage homePage = new GeneralPage(driver);
        RegisterPage registerPage = homePage.goToRegisterPage();

        // 3. Enter information of the created account in Pre-condition
        // 4. Click on "Register" button
        registerPage.register(
                Constant.USERNAME,
                Constant.REGISTER_PASSWORD,
                Constant.REGISTER_PASSWORD,
                Constant.REGISTER_PID);

        // Check message
        String ACTUAL_MESSAGE = homePage.getMessage(errorMessage);
        homePage.checkMessage(ACTUAL_MESSAGE, Constant.INUSE_EMAIL_WARNING);
    }

    @Test(description = "User can't create account while password and PID fields are empty")
    public void registerWithBlankPasswordAndPID() {

        // 1. Navigate to Railway
        // 2. Click on "Register" tab
        GeneralPage generalPage = new GeneralPage(driver);
        RegisterPage registerPage = generalPage.goToRegisterPage();

        // 3. Enter information of the created account in Pre-condition
        // 4. Click on "Register" button
        registerPage.register(Constant.USERNAME, "", "", "");

        // Check message
        String ACTUAL_MESSAGE = generalPage.getMessage(errorMessage);
        generalPage.checkMessage(ACTUAL_MESSAGE, Constant.INVALID_REGISTER_VALUE_MESSAGE);
    }

    @Test(description = "User create and activate account")
    public void registerAccountAndCheckConfirmationEmail() {

        //Locator
        String ACTIVATIONLINK = "http://www.saferailway.somee.com/Account/" +
                "Confirm?confirmationCode=acIJ0uTl0YFkgog%2fjTQ4Mw%3d%3d";
        By REGISTERCONFIRMATION = By.xpath("//*[@id='content']/p");

        // 1. Navigate to Guerrilla Mail
        driver.get(Constant.MAILBOX_URL);
        String guerrillaTab = driver.getWindowHandle();

        // 2. Get a free email
        GuerrillaMailPage mailPage = new GuerrillaMailPage(driver);
        String email = mailPage.getEmail();

        // 3. Navigate to Railway
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(Constant.RAILWAY_URL);

        // 4. Register a valid account
        GeneralPage homePage = new GeneralPage(driver);
        RegisterPage registerPage = homePage.goToRegisterPage();
        registerPage.register(email,
                Constant.REGISTER_PASSWORD,
                Constant.REGISTER_PASSWORD,
                Constant.REGISTER_PID);

        // 5. Go back to Guerrilla Mail
        driver.switchTo().window(guerrillaTab);

        // 6. Click on Activation Link
        mailPage.clickActivationLink();

        // 7. Redirected to Railway and confirmation message displays

        Assert.assertTrue(registerPage.isAtRegisterPage(), "Không ở trang Register");

        String ACTUAL_MESSAGE = homePage.getMessage(REGISTERCONFIRMATION);
        homePage.checkMessage(ACTUAL_MESSAGE, Constant.REGISTER_CONFIRM_MESSAGE);
    }
}
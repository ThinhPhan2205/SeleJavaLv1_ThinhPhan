package testcases;

import common.Constant;
import org.openqa.selenium.WindowType;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.GeneralPage;
import pageObjects.GuerrillaMailPage;
import pageObjects.RegisterPage;

public class CreateAccountTest extends BaseTest {

    @Test(description = "TC07: User can't create account with an already in-use email")
    public void tc07_registerAccountWithInuseEmail() {

        // 1. Navigate to Railway
        // 2. Click on "Register" tab
        GeneralPage homePage = new GeneralPage(driver);
        RegisterPage registerPage = homePage.clickOnRegisterTab();

        // 3. Enter information of the created account in Pre-condition
        // 4. Click on "Register" button
        registerPage.register(
                Constant.AccountToLogin.USERNAME.getValue(),
                Constant.AccountToRegister.PASSWORD.getValue(),
                Constant.AccountToRegister.PASSWORD.getValue(),
                Constant.AccountToRegister.PID.getValue()
        );

        // Check message
        registerPage.checkErrorMessage(Constant.INUSE_EMAIL_WARNING);
    }

    @Test(description = "TC08: User can't create account while password and PID fields are empty")
    public void tc08_registerWithBlankPasswordAndPID() {

        // 1. Navigate to Railway
        // 2. Click on "Register" tab
        GeneralPage generalPage = new GeneralPage(driver);
        RegisterPage registerPage = generalPage.clickOnRegisterTab();

        // 3. Enter information of the created account in Pre-condition
        // 4. Click on "Register" button
        registerPage.register(
                Constant.AccountToLogin.USERNAME.getValue(),
                Constant.InvalidInputs.BLANK_INPUT.getValue(),
                Constant.InvalidInputs.BLANK_INPUT.getValue(),
                Constant.InvalidInputs.BLANK_INPUT.getValue()
        );

        // Check message
        registerPage.checkErrorMessage(Constant.INVALID_REGISTER_VALUE_MESSAGE);
    }

    @Test(description = "TC09: User create and activate account")
    public void tc09_registerAccountAndCheckConfirmationEmail() {

        // 1. Navigate to Guerrilla Mail
        driver.get(Constant.MAILBOX_URL);
        String guerrillaTab = driver.getWindowHandle();

        // 2. Get a free email
        GuerrillaMailPage mailPage = new GuerrillaMailPage(driver);
        String email = mailPage.getEmail();

        // 3. Navigate to Railway
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(Constant.RAILWAY_URL);
        String railwayTab = driver.getWindowHandle();

        // 4. Register a valid account
        GeneralPage homePage = new GeneralPage(driver);
        RegisterPage registerPage = homePage.clickOnRegisterTab();
        registerPage.register(email,
                Constant.AccountToRegister.PASSWORD.getValue(),
                Constant.AccountToRegister.PASSWORD.getValue(),
                Constant.AccountToRegister.PID.getValue());

        // 5. Go back to Guerrilla Site
        driver.switchTo().window(guerrillaTab);
        homePage.refreshPage();

        // 6. Open confirmation email
        mailPage.openEmail(Constant.CONFIRM_EMAIL_KEYWORD);

        // 7. Click on Activation Link
        String activationUrl = mailPage.getLinkInMailBox();

        // 8. Redirected to Railway and confirmation message displays
        driver.switchTo().window(railwayTab);
        driver.get(activationUrl);

        Assert.assertTrue(homePage.isAtRailwayPage(), "Not at Railway site");
        registerPage.checkConfirmRegisterMessage(Constant.REGISTER_CONFIRM_MESSAGE);
    }
}
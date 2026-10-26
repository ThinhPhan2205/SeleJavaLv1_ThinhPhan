package testcases;

import common.Constant;
import org.openqa.selenium.WindowType;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.BasePage;
import pageObjects.GuerrillaMailPage;
import pageObjects.RegisterPage;

public class CreateAccountTest extends BaseTest {

    @Test(description = "TC07: User can't create account with an already in-use email")
    public void tc07_registerAccountWithInuseEmail() {

        BasePage basePage = new BasePage(driver);
        RegisterPage registerPage = basePage.clickOnRegisterTab();

        registerPage.register(
                Constant.AccountToLogin.USERNAME.getValue(),
                Constant.AccountToRegister.PASSWORD.getValue(),
                Constant.AccountToRegister.PASSWORD.getValue(),
                Constant.AccountToRegister.PID.getValue()
        );

        Assert.assertEquals(registerPage.getErrorRegisterMessage(),
                Constant.INUSE_EMAIL_WARNING,
                "Error register message is wrong");


    }

    @Test(description = "TC08: User can't create account while password and PID fields are empty")
    public void tc08_registerWithBlankPasswordAndPID() {

        BasePage basePage = new BasePage(driver);
        RegisterPage registerPage = basePage.clickOnRegisterTab();

        registerPage.register(
                Constant.AccountToLogin.USERNAME.getValue(),
                Constant.InvalidInputs.BLANK_INPUT.getValue(),
                Constant.InvalidInputs.BLANK_INPUT.getValue(),
                Constant.InvalidInputs.BLANK_INPUT.getValue()
        );

        Assert.assertEquals(registerPage.getErrorRegisterMessage(),
                Constant.INVALID_REGISTER_VALUE_MESSAGE,
                "Error register message is wrong");
    }

    @Test(description = "TC09: User create and activate account")
    public void tc09_registerAccountAndCheckConfirmationEmail() {

        // Constructor
        BasePage basePage = new BasePage(driver);
        GuerrillaMailPage mailPage = new GuerrillaMailPage(driver);
        RegisterPage registerPage = new RegisterPage(driver);

        // Navigate to Guerrilla Mail
        driver.get(Constant.MAILBOX_URL);
        String guerrillaTab = driver.getWindowHandle();

        // Get a free email
        String email = mailPage.getEmail();

        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(Constant.RAILWAY_URL);
        String railwayTab = driver.getWindowHandle();
        basePage.clickOnRegisterTab();

        registerPage.register(email,
                Constant.AccountToRegister.PASSWORD.getValue(),
                Constant.AccountToRegister.PASSWORD.getValue(),
                Constant.AccountToRegister.PID.getValue());

        driver.switchTo().window(guerrillaTab);
        basePage.refreshPage();

        mailPage.openEmail(Constant.CONFIRM_EMAIL_KEYWORD);

        String activationUrl = mailPage.getLinkInMailBox();
        driver.switchTo().window(railwayTab);
        driver.get(activationUrl);

        Assert.assertEquals(registerPage.getConfirmRegisterMessage(),
                Constant.REGISTER_CONFIRM_MESSAGE,
                "Register confirmation message is wrong");
    }
}
package testcases;

import common.Constant;
import common.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.BasePage;
import pageObjects.GuerrillaMailPage;
import pageObjects.HomePage;
import pageObjects.RegisterPage;

public class CreateAccountTest extends BaseTest {

    @Test(description = "TC07: User can't create account with an already in-use email")
    public void tc07_registerAccountWithInuseEmail() {

        BasePage basePage = new BasePage(DriverManager.getDriver());
        RegisterPage registerPage = basePage.clickOnRegisterTab();

        registerPage.register(
                Constant.LOGIN_USERNAME,
                Constant.REGISTER_PASSWORD,
                Constant.REGISTER_PASSWORD,
                Constant.REGISTER_PID
        );

        Assert.assertEquals(registerPage.getErrorRegisterMessage(),
                Constant.INUSE_EMAIL_WARNING,
                "Error register message is wrong");
    }

    @Test(description = "TC08: User can't create account while password and PID fields are empty")
    public void tc08_registerWithBlankPasswordAndPID() {

        WebDriver driver = DriverManager.getDriver();
        BasePage basePage = new BasePage(driver);
        RegisterPage registerPage = basePage.clickOnRegisterTab();
        GuerrillaMailPage mailPage = new GuerrillaMailPage(driver);

        // Navigate to Guerrilla Mail
        mailPage.goToGuerrillaSite();

        String email = mailPage.getEmail();

        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(Constant.RAILWAY_URL);
        String railwayTab = driver.getWindowHandle();

        basePage.clickOnRegisterTab();

        registerPage.register(
                email,
                Constant.BLANK_INPUT,
                Constant.BLANK_INPUT,
                Constant.BLANK_INPUT
        );

        Assert.assertEquals(registerPage.getInvalidPasswordWarning(),
                Constant.INVALID_PASSWORD_WARNING,
                "Invalid password length message is wrong");

        Assert.assertEquals(registerPage.getInvalidPidWarning(),
                Constant.INVALID_PID_WARNING,
                "Invalid ID length message is wrong");

        Assert.assertEquals(registerPage.getErrorRegisterMessage(),
                Constant.INVALID_REGISTER_VALUE_MESSAGE,
                "Error register message is wrong");
    }

    @Test(description = "TC09: User create and activate account")
    public void tc09_registerAccountAndCheckConfirmationEmail() {

        // Constructor
        WebDriver driver = DriverManager.getDriver();
        BasePage basePage = new BasePage(driver);
        GuerrillaMailPage mailPage = new GuerrillaMailPage(driver);
        RegisterPage registerPage = new RegisterPage(driver);
        HomePage homePage = new HomePage(driver);

        // Navigate to Guerrilla Mail
        mailPage.goToGuerrillaSite();

        // Get a free email
        String email = mailPage.getEmail();

        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(Constant.RAILWAY_URL);
        String railwayTab = driver.getWindowHandle();

        // Check message on Home page before logging in
        Assert.assertTrue(
                homePage.isRegisterLinkPresent(),
                "Create an account link does not display"
        );

        // Click "Create an account link" on Home page
        homePage.clickRegisterLink();

        registerPage.register(
                email,
                Constant.REGISTER_PASSWORD,
                Constant.REGISTER_PASSWORD,
                Constant.REGISTER_PID);

        basePage.switchToMailboxPage();
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
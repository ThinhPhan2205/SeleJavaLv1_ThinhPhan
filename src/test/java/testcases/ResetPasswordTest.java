package testcases;

import dataObjects.Account;
import common.Constant;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObjects.*;
import helpers.AccountHelper;

public class ResetPasswordTest extends BaseTest {

    private WebDriver driver;
    private ResetPasswordPage resetPasswordPage;
    private GuerrillaMailPage guerrillaMailPage;
    private BasePage basePage;
    private AccountHelper accountHelper;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        resetPasswordPage = new ResetPasswordPage(driver);
        guerrillaMailPage = new GuerrillaMailPage(driver);
        accountHelper = new AccountHelper(driver);
        basePage = new BasePage(driver);

    }

    @Test(description = "TC10: Reset password shows error if the new password is same as current")
    public void tc10_resetPasswordWithCurrentPasswordTest() {

        // Pre-condition: an active account is existing
        Account account = accountHelper.createAndActivateAccount();

        LoginPage loginPage = basePage.clickOnLoginTab();
        loginPage.clickResetPasswordLink();

        resetPasswordPage.getInstructionEmail(account.getEmail());

        basePage.switchToMailboxPage();

        // 7. Open email with subject containing "Please reset your password" and the email of the account at step 3
        guerrillaMailPage.goBackToInbox();
        guerrillaMailPage.openEmail(Constant.RESET_MAIL_KEYWORD);

        // 8. Click on reset link
        String resetToken = guerrillaMailPage.getResetToken();
        String resetPasswordLink = guerrillaMailPage.getLinkInMailBox();
        driver.get(resetPasswordLink);
        String resetPasswordWindow = driver.getWindowHandle();
        driver.switchTo().window(resetPasswordWindow);

        Assert.assertEquals(resetPasswordPage.getResetToken(),
                resetToken,
                "Reset tokens in instruction mail and reset password form do not match");



        resetPasswordPage.resetPassword(account.getPassword(), account.getPassword());

        // Expectation: Message "The new password cannot be the same with the current password" is shown
        String ACTUAL_MESSAGE = resetPasswordPage.getResetMessage();
        Assert.assertEquals(ACTUAL_MESSAGE, Constant.ERROR_RESET_PASSWORD_MESSAGE,
                "Reset password error message is wrong");
    }

    @Test(description = "TC11: Reset password shows error if the new password and confirm password doesn't match")
    public void tc11_resetPasswordWithUnmatchedConfirmPasswordTest() {

        // Test Data
        final String newPassword = "123456789";
        final String confirmPassword = "123123123123";

        // Pre-condition: an active account is existing
        Account account = accountHelper.createAndActivateAccount();

        LoginPage loginPage = basePage.clickOnLoginTab();
        loginPage.clickResetPasswordLink();

        resetPasswordPage.getInstructionEmail(account.getEmail());

        basePage.switchToMailboxPage();

        guerrillaMailPage.goBackToInbox();
        guerrillaMailPage.openEmail(Constant.RESET_MAIL_KEYWORD);

        // 8. Click on reset link
        String resetToken = guerrillaMailPage.getResetToken();
        String resetPassword = guerrillaMailPage.getLinkInMailBox();
        guerrillaMailPage.clickLinkInMail();

        // Switch driver
        driver.switchTo().window(resetPassword);

        Assert.assertEquals(resetPasswordPage.getResetToken(),
                resetToken,
                "Reset tokens in instruction mail and reset password form do not match");

        resetPasswordPage.resetPassword(newPassword, confirmPassword);

        String actualResetMessage = resetPasswordPage.getResetMessage();
        Assert.assertEquals(actualResetMessage, Constant.ERROR_RESET_PASSWORD_MESSAGE,
                "Reset password error message is wrong");

        String actualPasswordWarning = resetPasswordPage.getPasswordWarning();
        Assert.assertEquals(actualPasswordWarning, Constant.UNMATCH_PASSWORD_MESSAGE,
                "unmatching confirm password warning is wrong");
    }
}
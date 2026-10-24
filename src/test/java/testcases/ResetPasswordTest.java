package testcases;

import common.Account;
import common.Constant;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.*;

public class ResetPasswordTest extends BaseTest {

    @Test(description = "TC10: Reset password shows error if the new password is same as current")
    public void tc10_resetPasswordWithCurrentPasswordTest() {

        // Constructor
        ResetPasswordPage resetPwPage = new  ResetPasswordPage(driver);
        GuerrillaMailPage  guerrillaMailPage = new GuerrillaMailPage(driver);
        GeneralPage generalPage = new GeneralPage(driver);
        ResetPasswordPage resetPasswordPage = new ResetPasswordPage(driver);

        // Pre-condition: an active account is existing
        Account account = generalPage.createAndActivateAccount();

        // 1. Navigate to Railway site
        // 2. Click on "Login" tab
        // 3. Click on "Forgot Password page" link
        LoginPage loginPage = generalPage.clickOnLoginTab();
        loginPage.clickResetPasswordLink();

        // 4. Enter the email address of the activated account
        // 5. Click on "Send Instructions" button
        resetPwPage.getInstructionToEmail(account.getEmail());

        // 6. Login to the mailbox (the same mailbox when creating account)
        generalPage.switchToMailboxPage();

        // 7. Open email with subject containing "Please reset your password" and the email of the account at step 3
        guerrillaMailPage.goBackToInbox();
        guerrillaMailPage.openEmail(Constant.RESET_MAIL_KEYWORD);

        // 8. Click on reset link
        String resetToken = guerrillaMailPage.getResetToken();
        String resetPasswordLink = guerrillaMailPage.getLinkInMailBox();
        driver.get(resetPasswordLink);
        String resetPasswordWindow = driver.getWindowHandle();
        driver.switchTo().window(resetPasswordWindow);

        // 9. Input same password into 2 fields  "new password" and "confirm password"
        // 10. Click Reset Password

        resetPwPage.checkResetToken(resetToken);
        resetPwPage.resetPassword(account.getPassword(), account.getPassword());

        // Expectation: Message "The new password cannot be the same with the current password" is shown
        String ACTUAL_MESSAGE = resetPwPage.getResetMessage();
        Assert.assertEquals(ACTUAL_MESSAGE, Constant.ERROR_RESET_PASSWORD_MESSAGE,
                "Thông báo lỗi reset password không đúng");
    }

    @Test(description = "TC11: Reset password shows error if the new password and confirm password doesn't match")
    public void tc11_resetPasswordWithUnmatchedConfirmPasswordTest() {

        // Constructor
        ResetPasswordPage resetPwPage = new  ResetPasswordPage(driver);
        GuerrillaMailPage  guerrillaMailPage = new GuerrillaMailPage(driver);
        GeneralPage generalPage = new GeneralPage(driver);
        ResetPasswordPage resetPasswordPage = new ResetPasswordPage(driver);

        // Test Data
        final String newPassword = "123456789";
        final String confirmPassword = "123123123123";

        // Pre-condition: an active account is existing
        Account account = generalPage.createAndActivateAccount();

        // 1. Navigate to Railway site
        // 2. Click on "Login" tab
        // 3. Click on "Forgot Password page" link
        LoginPage loginPage = generalPage.clickOnLoginTab();
        loginPage.clickResetPasswordLink();

        // 4. Enter the email address of the activated account
        // 5. Click on "Send Instructions" button
        resetPwPage.getInstructionToEmail(account.getEmail());

        // 6. Login to the mailbox (the same mailbox when creating account)
        generalPage.switchToMailboxPage();

        // 7. Open email with subject containing "Please reset your password" and the email of the account at step 3
        guerrillaMailPage.goBackToInbox();
        guerrillaMailPage.openEmail(Constant.RESET_MAIL_KEYWORD);

        // 8. Click on reset link
        String resetToken = guerrillaMailPage.getResetToken();
        String resetPassword = guerrillaMailPage.getLinkInMailBox();
        guerrillaMailPage.clickLinkInMail();

        // Switch driver
        driver.switchTo().window(resetPassword);

        // 9. Input same password into 2 fields  "new password" and "confirm password"
        // 10. Click Reset Password
        resetPwPage.checkResetToken(resetToken);
        resetPwPage.resetPassword(newPassword, confirmPassword);

        // Expectations:
        // Error message "Could not reset password. Please correct the errors and try again." displays above the form.
        // Error message "The password confirmation did not match the new password." displays next to the confirm password field.
        String actualResetMessage = resetPwPage.getResetMessage();
        Assert.assertEquals(actualResetMessage, Constant.ERROR_RESET_PASSWORD_MESSAGE,
                "Thông báo lỗi reset password không đúng");

        String actualPasswordWarning = resetPwPage.getPasswordWarning();
        Assert.assertEquals(actualPasswordWarning, Constant.UNMATCH_PASSWORD_MESSAGE,
                "Thông báo lỗi unmatching confirm password không đúng");
    }
}
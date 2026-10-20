package testcases;

import common.Constant;
import org.openqa.selenium.WindowType;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.GeneralPage;
import pageObjects.GuerrillaMailPage;
import pageObjects.RegisterPage;

public class RegisterTest extends BaseTest {

    @Test
    public void registerAccountAndCheckConfirmationEmail() {

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

        // 6. Check the confirmation email
        Assert.assertTrue(mailPage.isEmailDisplayed(Constant.CONFIRM_EMAIL_SUBJECT),
                "Không nhận được email xác nhận");

        String content = mailPage.openEmailAndGetContent(Constant.CONFIRM_EMAIL_SUBJECT);
        Assert.assertTrue(content.toLowerCase().contains(Constant.CONFIRM_EMAIL_KEYWORD),
                "Nội dung email xác nhận không đúng: " + content);
    }
}
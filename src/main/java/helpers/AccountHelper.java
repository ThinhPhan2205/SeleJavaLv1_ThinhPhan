package helpers;

import common.Constant;
import dataObjects.Account;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import pageObjects.BasePage;
import pageObjects.GuerrillaMailPage;
import pageObjects.RegisterPage;

public class AccountHelper {

    private final WebDriver driver;
    public AccountHelper(WebDriver driver) {
        this.driver = driver;
    }

    // Create and activate a new account
    public Account createAndActivateAccount() {
        String subjectKeyword = "confirm";

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
        BasePage basePage = new BasePage(driver);
        RegisterPage registerPage = basePage.clickOnRegisterTab();
        registerPage.register(email,
                Constant.AccountToRegister.PASSWORD.getValue(),
                Constant.AccountToRegister.PASSWORD.getValue(),
                Constant.AccountToRegister.PID.getValue());

        // 5. Go back to Guerrilla Mail
        driver.switchTo().window(guerrillaTab);
        basePage.refreshPage();

        // 6. Open confirmation email
        mailPage.openEmail(subjectKeyword);

        // 7. Click on Activation Link
        String activationUrl = mailPage.getLinkInMailBox();

        // 8. Redirected to Railway and confirmation message displays
        driver.switchTo().window(railwayTab);
        driver.get(activationUrl);

        if (basePage.isAtRailwayPage()){
            System.out.print("Browser is at Railway site");
        } else {
            System.out.print("Browser is not at Railway site");
        }


        return new Account(email, Constant.AccountToRegister.PASSWORD.getValue());
    }
}
package common;

import org.openqa.selenium.WebDriver;

public class DriverManager {
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    public static void setDriver(WebDriver driver) {
        DRIVER.set(driver);
    }

    public static void removeDriver() {
        DRIVER.remove();
    }
}
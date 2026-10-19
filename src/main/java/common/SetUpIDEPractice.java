package common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SetUpIDEPractice {

    private static final String URL = "https://www.selenium.dev/documentation/";
    private static final String EXPECTED_TITLE = "The Selenium Browser Automation Project | Selenium";

    public static void main(String[] args) {
        // Test 1: Chrome
        runTest(new ChromeDriver(), "Chrome");

        // Test 2: Firefox (lặp lại cùng test)
        runTest(new FirefoxDriver(), "Firefox");
    }

    private static void runTest(WebDriver driver, String browserName) {
        try {
            // 1. Mở trình duyệt và truy cập trang
            driver.manage().window().maximize();
            driver.get(URL);

            // 2. Lấy title thực tế và so sánh với title mong đợi
            String actualTitle = driver.getTitle();
            System.out.println("[" + browserName + "] Actual title  : " + actualTitle);
            System.out.println("[" + browserName + "] Expected title: " + EXPECTED_TITLE);

            if (EXPECTED_TITLE.equals(actualTitle)) {
                System.out.println("[" + browserName + "] RESULT: PASSED");
            } else {
                System.out.println("[" + browserName + "] RESULT: FAILED");
            }
        } finally {
            // 3. Luôn đóng trình duyệt dù test pass hay fail
            driver.quit();
        }
    }
}

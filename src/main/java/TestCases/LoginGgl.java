package TestCases;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import Page_Objects.Login_Page;
import Utilities.Take_Screenshot;

public class LoginGgl {
	
    WebDriver driver;
    // Removed ExcelWBook and ExcelWSheet as they're not used in this test

    @BeforeClass
    void setup() {
        // Set Chrome preferences
        ChromeOptions options = new ChromeOptions();
        // Optional: Add arguments to reduce detection (not foolproof)
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--no-sandbox");
        
        // Initialize WebDriver with ChromeOptions
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @BeforeMethod
    void navigateToSignInPage() {
        // Navigate to the app login page
        driver.get("https://meet2.synesisit.info/sign-in");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // Add the SID cookie (from your manual session)
        Cookie cookie = new Cookie("SID", "g.a0002AiebWaTg-RFduMjyT9KoeCRlYSTk2qKNuzLUSqWIFMq0vhPMbtiLEw77LQHP8zqGN-A-gACgYKATYSARESFQHGX2MictmX_xUYqjZIbV05LAyutRoVAUF8yKohwohK0O5QnbrNsmGANV0R0076");
        driver.manage().addCookie(cookie);
        
        // Reload the page to apply the cookie
        driver.navigate().refresh();
        System.out.println("Navigated to sign-in page with SID cookie: " + driver.getCurrentUrl());
        
        // Wait for page to load
        wait.until(ExpectedConditions.urlContains("meet2.synesisit.info"));
    }
	
    @Test(priority = 1)
    void GoogleLogin() throws IOException {
        SoftAssert soft = new SoftAssert();
        Login_Page lP = new Login_Page(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
           

            // Click Continue with Google (opens popup)
            lP.ContinuewWithGoogle();
            System.out.println("Clicked Continue with Google");

            // Wait for Google login page
            wait.until(ExpectedConditions.urlContains("accounts.google.com"));
            System.out.println("Google login page URL: " + driver.getCurrentUrl());

            // Try UI login (though likely to fail due to security detection)
            lP.setMail("test.quality.100percent@gmail.com");
            System.out.println("Entered Google email");
            Thread.sleep(1000); // Minimal wait
            lP.clicknext();
            System.out.println("Clicked Next (email)");
            Thread.sleep(1000);
            lP.setPass("test@100%");
            System.out.println("Entered Google password");
            Thread.sleep(1000);
            lP.clicknext2();
            System.out.println("Clicked Next (password)");

            
            wait.until(ExpectedConditions.urlContains("home"));
            String currentUrl = driver.getCurrentUrl();
            System.out.println("Current URL after login: " + currentUrl);

            // Verify URL (expecting app home page after login)
            String expectedUrl = "https://meet2.synesisit.info/home";
            soft.assertEquals(currentUrl, expectedUrl, "URL did not match the expected URL: " + currentUrl);

            // Take screenshot after login
            Take_Screenshot.TakeScreenshot(driver, "after_login");

        } catch (Exception e) {
            e.printStackTrace();
            Take_Screenshot.TakeScreenshot(driver, "GoogleLogin_failure");
            soft.assertAll(); // Ensure assertions are checked even on failure
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }

    @AfterMethod
    public void Aftermethod6(ITestResult result) throws IOException {
        if (ITestResult.FAILURE == result.getStatus()) {
            Take_Screenshot.TakeScreenshot(driver, result.getName());
        }

        // Close any popup windows
        try {
            String originalWindow = driver.getWindowHandle();
            for (String windowHandle : driver.getWindowHandles()) {
                if (!windowHandle.equals(originalWindow)) {
                    driver.switchTo().window(windowHandle);
                    driver.close();
                }
            }
            driver.switchTo().window(originalWindow);
        } catch (Exception e) {
            System.out.println("Failed to close popup windows: " + e.getMessage());
        }
    }

    @AfterClass
    void teardown() throws IOException {
        if (driver != null) {
            driver.quit();
        }
    }
}
package TestCases;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.Duration;
import java.util.Comparator;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import Page_Objects.Login_Page;
import Utilities.Take_Screenshot;

public class LoginEdge {

    WebDriver driver;
    Path tempUserDataDir = null;

    @BeforeClass
    void setup() throws IOException {
        EdgeOptions options = new EdgeOptions();

        // Add this for any version Edge
        options.addArguments("--remote-allow-origins=*");

        // Optional: original Edge profile
        String origUserDataDir = "C:\\Users\\SIL_Laptop\\AppData\\Local\\Microsoft\\Edge\\User Data";
        String profileDirectory = "Default";

        tempUserDataDir = Files.createTempDirectory("edge-user-data-copy-");
        System.out.println("Temporary profile dir: " + tempUserDataDir.toString());

        copyDirectory(Paths.get(origUserDataDir), tempUserDataDir);

        options.addArguments("user-data-dir=" + tempUserDataDir.toString());
        options.addArguments("--profile-directory=" + profileDirectory);
        options.addArguments("--disable-session-crashed-bubble");
        options.addArguments("--no-first-run");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--start-maximized");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);

        // ✅ Use local EdgeDriver path instead of WebDriverManager
        System.setProperty("webdriver.edge.driver",
                "C:\\Users\\SIL_Laptop\\Downloads\\edgedriver_win64\\msedgedriver.exe");

        driver = new EdgeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @BeforeMethod
    void navigateToSignInPage() {
        driver.get("https://meet2.synesisit.info/sign-in");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("meet2.synesisit.info"));
    }

    @Test(priority = 1)
    void GoogleLogin() throws IOException {
        SoftAssert soft = new SoftAssert();
        Login_Page lP = new Login_Page(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            lP.ContinuewWithGoogle();
            System.out.println("Clicked Continue with Google");
            lP.clickUseAnother();
            Thread.sleep(1000);

            try {
                lP.setMail("test.quality.100percent@gmail.com");
                Thread.sleep(1000);
                lP.clicknext();
                Thread.sleep(1000);
                lP.setPass("test@100%");
                Thread.sleep(1000);
                lP.clicknext2();
                Thread.sleep(1000);
            } catch (Exception inner) {
                System.out.println("Login fields not present / already logged-in: " + inner.getMessage());
            }

            wait.until(ExpectedConditions.urlContains("home"));
            String currentUrl = driver.getCurrentUrl();
            System.out.println("Current URL after login: " + currentUrl);

            String expectedUrl = "https://meet2.synesisit.info/home";
            soft.assertEquals(currentUrl, expectedUrl, "URL did not match: " + currentUrl);

            Take_Screenshot.TakeScreenshot(driver, "after_login");
            soft.assertAll();

        } catch (Exception e) {
            e.printStackTrace();
            Take_Screenshot.TakeScreenshot(driver, "GoogleLogin_failure");
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }

    @AfterMethod
    public void Aftermethod6(ITestResult result) throws IOException {
        if (ITestResult.FAILURE == result.getStatus()) {
            Take_Screenshot.TakeScreenshot(driver, result.getName());
        }

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

        if (tempUserDataDir != null) {
            try {
                deleteDirectory(tempUserDataDir);
                System.out.println("Deleted temporary profile: " + tempUserDataDir.toString());
            } catch (IOException e) {
                System.out.println("Failed to delete temp profile: " + e.getMessage());
            }
        }
    }

    private void copyDirectory(Path source, Path target) throws IOException {
        if (!Files.exists(source)) return;
        Files.walk(source).forEach(path -> {
            try {
                Path relative = source.relativize(path);
                Path dest = target.resolve(relative);
                if (Files.isDirectory(path)) {
                    if (!Files.exists(dest)) Files.createDirectories(dest);
                } else {
                    Files.copy(path, dest, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
                }
            } catch (IOException e) {
                throw new RuntimeException("Error copying profile: " + e.getMessage(), e);
            }
        });
    }

    private void deleteDirectory(Path dir) throws IOException {
        if (!Files.exists(dir)) return;
        Files.walk(dir)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }
}

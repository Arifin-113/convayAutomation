package TestCases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
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

import Page_Objects.CM_OrganizationCreateNew_Page;

//import com.sun.org.apache.bcel.internal.classfile.Utility;

import Page_Objects.Login_Page;
import Utilities.ConfigReader;
import Utilities.Take_Screenshot;

public class LoginGogle {
	
	WebDriver driver;
	XSSFWorkbook ExcelWBook;
	XSSFSheet ExcelWSheet;

	@BeforeClass
	void setup() throws IOException {
		// Set Chrome preferences to access globally
        ChromeOptions options = new ChromeOptions();

        // Initialize WebDriver with ChromeOptions
        driver = new ChromeDriver(options);
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(
                Integer.parseInt(ConfigReader.getProperty("implicit.wait.seconds"))));
        
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();

		// Setup Excel file
		File excelFile = new File("TestData\\TestDataFile.xlsx");
		FileInputStream inputStream = new FileInputStream(excelFile);

		// Load the workbook and sheet
		ExcelWBook = new XSSFWorkbook(inputStream);
		ExcelWSheet = ExcelWBook.getSheetAt(3);
	}

	@BeforeMethod
	void navigateToSignInPage() throws InterruptedException {
		
		
		// Login 
    	driver.get(ConfigReader.getProperty("login.url")); // Use config.properties
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(
                Integer.parseInt(ConfigReader.getProperty("webdriver.wait.seconds"))));
     /*// Example: Add SID cookie
        Cookie cookie = new Cookie("SID", "g.a0002AiebWaTg-RFduMjyT9KoeCRlYSTk2qKNuzLUSqWIFMq0vhPMbtiLEw77LQHP8zqGN-A-gACgYKATYSARESFQHGX2MictmX_xUYqjZIbV05LAyutRoVAUF8yKohwohK0O5QnbrNsmGANV0R0076");
        driver.manage().addCookie(cookie);
        */
        driver.get("https://accounts.google.com"); // Reload to apply
        
//		driver.manage().addCookie(new Cookie("session_token", "your_saved_token_here"));
//		driver.navigate().refresh();
		
		Thread.sleep(2000);
		
		
		
	}
	
	@Test(priority = 1) // Test case for blank credential
	void GoogleLogin() throws InterruptedException {
		 SoftAssert soft = new SoftAssert();

		Login_Page lP = new Login_Page(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        ExcelWSheet = ExcelWBook.getSheetAt(0); 
		
        JavascriptExecutor js = (JavascriptExecutor) driver;

		
		lP.setMail("test.quality.100percent@gmail.com");
		Thread.sleep(1000);
		lP.clicknext();
		Thread.sleep(1000);
		lP.setPass("test@100%");
		Thread.sleep(1000);
		lP.clicknext2();
		Thread.sleep(1000);
		
		lP.ContinuewWithGoogle();
		
		
		// Get and print the current URL
				String currentUrl = driver.getCurrentUrl();
				System.out.println("Current URL: " + currentUrl);

				// To check page validation
				String expectedUrl = "https://accounts.google.com/v3/signin/identifier?continue=https%3A%2F%2Fwww.google.com%2F&dsh=S-1841672662%3A1759906310547571&ec=futura_exp_og_so_72776762_e&hl=bn&ifkv=AfYwgwUmZ_GvfExkXJHa2PAN_44mFTx4HPRwceM1w_MqH0-jk21AS4UlAxIw25H48STTvX5RKkNAFg&passive=true&flowName=GlifWebSignIn&flowEntry=ServiceLogin";

				soft.assertEquals(currentUrl, expectedUrl, "URL did not match the expected URL.");
			}
		

	@AfterMethod
	public void Aftermethod6(ITestResult result) throws IOException {
		if (ITestResult.FAILURE == result.getStatus()) {
			Take_Screenshot.TakeScreenshot(driver, result.getName());
		}

	}
	@AfterClass
	void teardown() throws IOException {
		ExcelWBook.close();
		driver.quit();
	}

}

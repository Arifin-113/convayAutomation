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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
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
import Utilities.Take_Screenshot;

public class LoginEdge2 {

	WebDriver driver;
	XSSFWorkbook ExcelWBook;
	XSSFSheet ExcelWSheet;

	@BeforeClass
	void setup() throws IOException {
		EdgeOptions options = new EdgeOptions();

        // Add this for any version Edge
        options.addArguments("--remote-allow-origins=*");
        driver = new EdgeDriver(options);
        
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
	void navigateToSignInPage() {
		driver.get("https://meet2.synesisit.info/sign-in");
	}

	@Test(priority = 6) // Test case for blank credential
	void emailLogin() throws InterruptedException {
		
		SoftAssert soft = new SoftAssert();
		
		Login_Page lP = new Login_Page(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        ExcelWSheet = ExcelWBook.getSheetAt(0); 
		
        JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("document.body.style.zoom='75%'");
		Thread.sleep(3000);
		
		lP.clickContinueWithEmail();
		

		js.executeScript("document.body.style.zoom='100%'");
		Thread.sleep(3000);
		 
     	ExcelWSheet = ExcelWBook.getSheetAt(0); 
		// Reading the 5th row's first and second cells for username and password
		String No_username = ExcelWSheet.getRow(5).getCell(0).toString();
		String No_password = ExcelWSheet.getRow(5).getCell(1).toString();

		// Perform Login
		Login_Page lp = new Login_Page(driver);
		lp.setUserName(No_username);
		lp.setPassword(No_password);
		lp.clickLogin();
		Thread.sleep(2000);
		
		// Get and print the current URL
				String currentUrl = driver.getCurrentUrl();
				System.out.println("Current URL: " + currentUrl);

				// To check page validation
				String expectedUrl = "https://meet2.synesisit.info/home";
				Thread.sleep(2000);

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
		driver.close();
	}

}
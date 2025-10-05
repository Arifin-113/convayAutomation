package TestCases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

//import com.sun.org.apache.bcel.internal.classfile.Utility;

import Utilities.Take_Screenshot;
import Page_Objects.JoinMeetingFromLandingPage_Page;
import Page_Objects.JoinWithMeetingLink_Page;

public class JoinWithMeetingLink {

	WebDriver driver;
	XSSFWorkbook ExcelWBook;
	XSSFSheet ExcelWSheet;

	@BeforeClass
	void setup() throws IOException {
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();

		// Setup Excel file
		File excelFile = new File("TestData\\TestDataFile.xlsx");
		FileInputStream inputStream = new FileInputStream(excelFile);

		// Load the workbook and sheet
		ExcelWBook = new XSSFWorkbook(inputStream);
		ExcelWSheet = ExcelWBook.getSheet("JoinFromLanding");
	}

	@BeforeMethod
	void navigateToSignInPage() {
	}

	@Test(priority = 1) // Test case to join a meeting with valid meeting Link
	void JoinWithMeetingLink() throws InterruptedException {
		
		JoinWithMeetingLink_Page JoinWithLink = new JoinWithMeetingLink_Page(driver);

		driver.get("https://meet2.synesisit.info/m/j/647712840966/kakonpaulavi");
		Thread.sleep(2000);

		// Reading the first row's first and second cells for meeting link and name
		String Name = ExcelWSheet.getRow(0).getCell(1).toString();

		// Perform Actions

		// Input Name
		JoinWithLink.setUserName(Name);
		Thread.sleep(2000);

		// Click on continue
		JoinWithLink.clickContinue();
		Thread.sleep(10000);
	}

	@AfterMethod
	public void Aftermethod1(ITestResult result) throws IOException {
		if (ITestResult.FAILURE == result.getStatus()) {
			Take_Screenshot.TakeScreenshot(driver, result.getName());
		}
	}
	
	@Test(priority = 2) // Test case to join a meeting with invalid meeting Link
	void JoinWithInvalidMeetingLink() throws InterruptedException {
		
		JoinWithMeetingLink_Page JoinWithLink = new JoinWithMeetingLink_Page(driver);

		driver.get("https://meet2.synesisit.info/m/j/647712840967/kakonpaulavi");
		Thread.sleep(2000);

		// Reading the first row's first and second cells for meeting link and name
		String Name = ExcelWSheet.getRow(0).getCell(1).toString();

		// Perform Actions

		// Input Name
		JoinWithLink.setUserName(Name);
		Thread.sleep(2000);

		// Click on continue
		JoinWithLink.clickContinue();
		Thread.sleep(3000);
		
		String getTooltipValue = JoinWithLink.getToltipValue();

		// Verify the tooltip message text
		Assert.assertEquals(getTooltipValue, "Please enter a valid URL or meeting id");
	}

	@AfterMethod
	public void Aftermethod2(ITestResult result) throws IOException {
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
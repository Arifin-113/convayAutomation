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

import Page_Objects.JoinMeetingFromLandingPage_Page;
import Utilities.Take_Screenshot;

public class JoinMeetingFromLandingPage {

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
		driver.get("https://meet2.synesisit.info/");
	}

	@Test(priority = 1) // Test case to join a meeting with meeting ID from landing page
	void JoinWithMeetingID() throws InterruptedException {
		
		JoinMeetingFromLandingPage_Page JoinfromLanding = new JoinMeetingFromLandingPage_Page(driver);

		// Reading the first row's first and second cells for meeting link and name
		String MeetingLink = ExcelWSheet.getRow(0).getCell(0).toString();
		String Name = ExcelWSheet.getRow(0).getCell(1).toString();

		// Perform Actions
		//Click jOin a ,meeting from landing page
		JoinfromLanding.clickJoinMeeting();
		Thread.sleep(2000);
		
		//Input meeting link
		JoinfromLanding.setMeetingLink(MeetingLink);
		Thread.sleep(2000);
		
		//Input Name
		JoinfromLanding.setUserName(Name);
		Thread.sleep(2000);
		
		//Click on continue
		JoinfromLanding.clickContinue();
		Thread.sleep(10000);
	}

	@AfterMethod
	public void Aftermethod1(ITestResult result) throws IOException {
		if (ITestResult.FAILURE == result.getStatus()) {
			Take_Screenshot.TakeScreenshot(driver, result.getName());
		}
	}
	
	@Test(priority = 2) // Test case to join a meeting with meeting Link from landing page
	void JoinWithMeetingLink() throws InterruptedException {
		
		JoinMeetingFromLandingPage_Page JoinfromLanding = new JoinMeetingFromLandingPage_Page(driver);

		// Reading the first row's first and second cells for meeting link and name
		String MeetingLink = ExcelWSheet.getRow(1).getCell(0).toString();
		String Name = ExcelWSheet.getRow(1).getCell(1).toString();

		// Perform Actions
		//Click jOin a ,meeting from landing page
		JoinfromLanding.clickJoinMeeting();
		Thread.sleep(2000);
		
		//Input meeting link
		JoinfromLanding.setMeetingLink(MeetingLink);
		Thread.sleep(2000);
		
		//Input Name
		JoinfromLanding.setUserName(Name);
		Thread.sleep(2000);
		
		//Click on continue
		JoinfromLanding.clickContinue();
		Thread.sleep(10000);
	}

	@AfterMethod
	public void Aftermethod2(ITestResult result) throws IOException {
		if (ITestResult.FAILURE == result.getStatus()) {
			Take_Screenshot.TakeScreenshot(driver, result.getName());
		}
	}
	
	@Test(priority = 3) // Test case to join a meeting without meeting ID & Link from landing page
	void JoinWithoutIDLink() throws InterruptedException {
		
		JoinMeetingFromLandingPage_Page JoinfromLanding = new JoinMeetingFromLandingPage_Page(driver);

		// Reading the first row's first and second cells for meeting link and name
		String MeetingLink = ExcelWSheet.getRow(2).getCell(0).toString();
		String Name = ExcelWSheet.getRow(2).getCell(1).toString();

		// Perform Actions
		//Click jOin a ,meeting from landing page
		JoinfromLanding.clickJoinMeeting();
		Thread.sleep(2000);
		
		//Input meeting link
		JoinfromLanding.setMeetingLink(MeetingLink);
		Thread.sleep(2000);
		
		//Input Name
		JoinfromLanding.setUserName(Name);
		Thread.sleep(2000);
		
		//Click on continue
		JoinfromLanding.clickContinue();
		Thread.sleep(10000);

		// Get and print the current URL
		String currentUrl = driver.getCurrentUrl();
		System.out.println("Current URL: " + currentUrl);

		// User redirection validation after successful sign in
		String expectedUrl = "https://meet2.synesisit.info/m/j";

		Assert.assertEquals(currentUrl, expectedUrl, "URL after login did not match the expected URL.");
	}

	@AfterMethod
	public void Aftermethod3(ITestResult result) throws IOException {
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
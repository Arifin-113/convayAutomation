package TestCases;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Page_Objects.Login_Page;
import Page_Objects.CopyInvitationInstant_Page;
import Utilities.Take_Screenshot;

public class CopyInvitationInstant {

    WebDriver driver;
    XSSFWorkbook ExcelWBook;
    XSSFSheet ExcelWSheet;
    XSSFSheet AddSingleUserSheet;

    @BeforeClass
    void setup() throws IOException {
        // Initialize WebDriver and Excel workbook
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        // Setup Excel file
        File excelFile = new File("TestData\\TestDataFile.xlsx");
        FileInputStream inputStream = new FileInputStream(excelFile);

        // Load the workbook and sheet
        ExcelWBook = new XSSFWorkbook(inputStream);
        AddSingleUserSheet = ExcelWBook.getSheetAt(3);
    }

    @BeforeMethod
    void navigateToHomePage2() throws InterruptedException {
        // Login before add users
        driver.get("https://meet2.synesisit.info/sign-in");

        AddSingleUserSheet = ExcelWBook.getSheetAt(0);

        // Reading the first row's first and second cells for username and password
        String username = AddSingleUserSheet.getRow(0).getCell(0).toString();
        String password = AddSingleUserSheet.getRow(0).getCell(1).toString();

        // Perform Login (using your Login_Page)
        Login_Page lp = new Login_Page(driver);
        lp.setUserName(username);
        lp.setPassword(password);
        lp.clickLogin();
        Thread.sleep(10000); // Wait for login to complete
    }

    @Test(priority = 1) // Test case to copy invitation from home page
    void copyInvitation() throws InterruptedException {
        // Navigate to the copy invitation button after login
        CopyInvitationInstant_Page copyInvitation = new CopyInvitationInstant_Page(driver);

        // Click on Accept All
        copyInvitation.clickAccept();
        Thread.sleep(2000);

        // Click on Copy Invitation
        copyInvitation.clickCopy();
        Thread.sleep(2000); // Wait for toaster to appear

        String getToasterValue = copyInvitation.getToasterValue();
        System.out.println("Toaster: [" + getToasterValue + "]"); // Debug print

        // Expected toaster message with exact formatting
        String expectedToaster = "Invitation copied to clipboard\n" +
                                "Kakon Paul Avi is inviting you to a meeting on Convay.\n" +
                                "Meeting ID :\n" +
                                "6928 8107 9044\n" +
                                "Meeting Link: https://meet2.synesisit.info/m/j/692881079044/kakonpaulavi";

        // Normalize strings to handle hidden characters or line break differences
        String normalizedActual = getToasterValue.replaceAll("[\\r\\n]+", "\n").trim();
        String normalizedExpected = expectedToaster.replaceAll("[\\r\\n]+", "\n").trim();

        // Verify the toaster message text
        Assert.assertEquals(normalizedActual, normalizedExpected, "Toaster message does not match expected format!");
    }

    @AfterMethod
    public void captureFailureScreenshot1(ITestResult result) throws IOException {
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
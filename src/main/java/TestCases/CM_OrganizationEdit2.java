package TestCases;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

import Page_Objects.Login_Page;
import Page_Objects.CM_OrganizationEdit_Page;
import Utilities.Take_Screenshot;

public class CM_OrganizationEdit2 {

    WebDriver driver;
    XSSFWorkbook ExcelWBook;
    XSSFSheet ExcelWSheet;

    @BeforeClass
    void setup() throws IOException {
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.manage().window().maximize();
        File excelFile = new File("TestData\\TestDataFile.xlsx");
        FileInputStream inputStream = new FileInputStream(excelFile);
        ExcelWBook = new XSSFWorkbook(inputStream);
        ExcelWSheet = ExcelWBook.getSheetAt(0); 
    }

    @BeforeMethod
    void navigateToHomePage() throws InterruptedException {
        driver.get("https://meet2.synesisit.info/sign-in");
        ExcelWSheet = ExcelWBook.getSheetAt(0); 
        String username = ExcelWSheet.getRow(5).getCell(0).toString();
        String password = ExcelWSheet.getRow(5).getCell(1).toString();
        Login_Page lp = new Login_Page(driver);
        lp.setUserName(username);
        lp.setPassword(password);
        lp.clickLogin();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("home")); // Replaced Thread.sleep
    }

    @Test(priority = 1)
    void CM_OrganizationEdit1() throws InterruptedException {
        CM_OrganizationEdit_Page managePlans = new CM_OrganizationEdit_Page(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            driver.get("https://meet2.synesisit.info/home");
            String originalWindow = driver.getWindowHandle();
            managePlans.clickManageOrg();
            wait.until(ExpectedConditions.numberOfWindowsToBe(2));

            String newWindowHandle = null;
            Set<String> windowHandles = driver.getWindowHandles();
            for (String windowHandle : windowHandles) {
                if (!originalWindow.equals(windowHandle)) {
                    driver.switchTo().window(windowHandle);
                    newWindowHandle = windowHandle; // Save new handle for closing
                    break;
                }
            }

            Assert.assertTrue(driver.getCurrentUrl().contains("https://meet2.synesisit.info:85/"));

            managePlans.clickOrg(); // Replaced duplicate clickManageOrg()
            managePlans.orgButton();
            managePlans.editButton();
            managePlans.typeChooseOrg();
            managePlans.clicksetOrgName();
            managePlans.clearsetOrgName();
            ExcelWSheet = ExcelWBook.getSheet("CM_OrganizationEdit");
            String setOrgName2 = ExcelWSheet.getRow(0).getCell(0).toString();
            managePlans.setOrgName(setOrgName2);
            managePlans.clickUpdateButton();
            managePlans.clickOkAfterUpdate();

            // Close the new tab
            driver.close();
            driver.switchTo().window(originalWindow);

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(priority = 2)
    void CM_OrganizationEdit2() throws InterruptedException {
        CM_OrganizationEdit_Page managePlans = new CM_OrganizationEdit_Page(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            driver.get("https://meet2.synesisit.info/home");
            String originalWindow = driver.getWindowHandle();
            managePlans.clickManageOrg();
            wait.until(ExpectedConditions.numberOfWindowsToBe(2));

            String newWindowHandle = null;
            Set<String> windowHandles = driver.getWindowHandles();
            for (String windowHandle : windowHandles) {
                if (!originalWindow.equals(windowHandle)) {
                    driver.switchTo().window(windowHandle);
                    newWindowHandle = windowHandle; // Save new handle for closing
                    break;
                }
            }

            Assert.assertTrue(driver.getCurrentUrl().contains("https://meet2.synesisit.info:85/"));

            managePlans.clickOrg(); // Replaced duplicate clickManageOrg()
            managePlans.orgButton();
            managePlans.editButton2();
            managePlans.typeChooseOrg();
            managePlans.clicksetOrgName();
            managePlans.clearsetOrgName();
            ExcelWSheet = ExcelWBook.getSheet("CM_OrganizationEdit");
            String setOrgName2 = ExcelWSheet.getRow(0).getCell(0).toString();
            managePlans.setOrgName(setOrgName2);
            managePlans.clickUpdateButton();
            managePlans.clickOkAfterUpdate();

            // Close the new tab
            driver.close();
            driver.switchTo().window(originalWindow);

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @AfterMethod
    public void captureFailureScreenshot2(ITestResult result) throws IOException {
        if (ITestResult.FAILURE == result.getStatus()) {
            Take_Screenshot.TakeScreenshot(driver, result.getName());
        }
    }

    @AfterClass
    void teardown() throws IOException {
        if (ExcelWBook != null) {
            ExcelWBook.close();
        }
        if (driver != null) {
            driver.quit();
        }
    }
}
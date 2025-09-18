package Page_Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CM_ManagePlansFilter_Page {
    WebDriver driver;
    WebDriverWait wait;

    // Define locators
    By manage_org = By.xpath("//a[contains(@href, 'organization') or contains(., 'Manage Organization')]");
    By manage_plans = By.xpath("//span[normalize-space()='Manage Plans']");
    By btn_filters = By.xpath("//button[normalize-space()='Filters']");
    By btn_filters2 = By.xpath("//h4[normalize-space()='Status']//span//*[name()='svg']");
    By header_status = By.xpath("//h4[normalize-space()='Status']");
    By header_status2 = By.xpath("//h4[normalize-space()='Status']//span//*[name()='svg']");
    By label_draft = By.xpath("//label[normalize-space()='Draft']");
    By label_inactive = By.xpath("//label[normalize-space()='Inactive']");
    By label_active = By.xpath("//label[normalize-space()='Active']");

    // Constructor
    public CM_ManagePlansFilter_Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Method to click Manage Organization link
    public void clickManageOrg() {
        try {
            WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement manageOrg = longWait.until(ExpectedConditions.elementToBeClickable(manage_org));
            System.out.println("Manage Org found: " + manageOrg.isDisplayed());
            try {
                manageOrg.click();
            } catch (Exception e) {
                System.out.println("Standard click failed for Manage Org, trying JavaScript click: " + e.getMessage());
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", manageOrg);
            }
        } catch (Exception e) {
            System.out.println("Failed to click Manage Org: " + e.getMessage());
            throw e;
        }
    }

    // Method to click Manage Plans
    public void clickManagePlans() {
        wait.until(ExpectedConditions.elementToBeClickable(manage_plans)).click();
    }

    // Method to click Filters button
    public void clickFiltersButton() {
        wait.until(ExpectedConditions.elementToBeClickable(btn_filters)).click();
    }

    // Method to click Filters button
    public void clickFiltersButton2() {
        for (int i = 0; i < 5; i++) {
            wait.until(ExpectedConditions.elementToBeClickable(btn_filters2)).click();
        }
    }

    // Method to verify Status header
    public boolean isStatusHeaderDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(header_status)).isDisplayed();
    }

    // Method to verify Status header
    public boolean isStatusHeaderDisplayed2() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(header_status2)).isDisplayed();
    }

    // Method to select Draft status
    public void selectDraftStatus() {
        wait.until(ExpectedConditions.elementToBeClickable(label_draft)).click();
    }

    // Method to select Inactive status
    public void selectInactiveStatus() {
        wait.until(ExpectedConditions.elementToBeClickable(label_inactive)).click();
    }

    // Method to select Active status
    public void selectActiveStatus() {
        wait.until(ExpectedConditions.elementToBeClickable(label_active)).click();
    }
}
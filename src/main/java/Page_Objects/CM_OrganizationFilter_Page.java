package Page_Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CM_OrganizationFilter_Page {
    WebDriver driver;
    public WebDriverWait wait;

    // Define locators
    public By manage_org = By.xpath("//a[contains(@href, 'organization') or contains(., 'Manage Organization')]//span");
    public By org = By.xpath("//span[normalize-space()='Organizations']"); 
    public By more_filters = By.xpath("//button[normalize-space()='More Filters']");
    public By header_status = By.xpath("//h4[normalize-space()='Status']");
    public By label_active = By.xpath("//label[normalize-space()='Active']");
    public By label_inactive = By.xpath("//label[normalize-space()='Inactive']");
    public By label_draft = By.xpath("//label[normalize-space()='Draft']");
    public By header_subscription = By.xpath("//h4[normalize-space()='Subscription']");
    public By label_premium = By.xpath("//label[normalize-space()='Premium']");
    public By label_free = By.xpath("//label[normalize-space()='Free']");
    public By label_custom = By.xpath("//label[normalize-space()='Custom']");
    public By header_organization_type = By.xpath("//h4[normalize-space()='Organization Type']");
    public By label_software_company = By.xpath("//label[normalize-space()='Software Company']");
    public By header_recently_added = By.xpath("//h4[normalize-space()='Recently Added']");

    // Constructor
    public CM_OrganizationFilter_Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // Default timeout
    }

    // Method to click Manage Organization link
    public void clickManageOrg() {
        wait.until(ExpectedConditions.elementToBeClickable(manage_org)).click();
    }
    
    // Method to click Manage organization
    public void clickOrg() {
        wait.until(ExpectedConditions.elementToBeClickable(org)).click();
    }

    public void clickMoreFilters() {
        wait.until(ExpectedConditions.elementToBeClickable(more_filters)).click();
    }

    public void clickStatusHeader() {
        wait.until(ExpectedConditions.elementToBeClickable(header_status)).click();
    }

    public void selectActiveStatus() {
        wait.until(ExpectedConditions.elementToBeClickable(label_active)).click();
    }

    public void selectInactiveStatus() {
        wait.until(ExpectedConditions.elementToBeClickable(label_inactive)).click();
    }

    public void selectDraftStatus() {
        wait.until(ExpectedConditions.elementToBeClickable(label_draft)).click();
    }

    public void clickSubscriptionHeader() {
        wait.until(ExpectedConditions.elementToBeClickable(header_subscription)).click();
    }

    public void selectPremium() {
        wait.until(ExpectedConditions.elementToBeClickable(label_premium)).click();
    }

    public void selectFree() {
        wait.until(ExpectedConditions.elementToBeClickable(label_free)).click();
    }

    public void selectCustom() {
        wait.until(ExpectedConditions.elementToBeClickable(label_custom)).click();
    }

    public void clickOrganizationTypeHeader() {
        wait.until(ExpectedConditions.elementToBeClickable(header_organization_type)).click();
    }

    public void selectSoftwareCompany() {
        wait.until(ExpectedConditions.elementToBeClickable(label_software_company)).click();
    }

    public void clickRecentlyAddedHeader() {
        wait.until(ExpectedConditions.elementToBeClickable(header_recently_added)).click();
    }

    public boolean isStatusHeaderDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(header_status)).isDisplayed();
        } catch (Exception e) {
            System.out.println("Status header not displayed: " + e.getMessage());
            return false;
        }
    }
}

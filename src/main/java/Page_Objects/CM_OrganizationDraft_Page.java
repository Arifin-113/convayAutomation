package Page_Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CM_OrganizationDraft_Page {

    WebDriver driver;
    WebDriverWait wait;

    // Define locators
    By manage_org = By.xpath("//a[contains(@href, 'organization') or contains(., 'Manage Organization')]//span");
    By org = By.xpath("//span[normalize-space()='Organizations']"); 
    By draft_org = By.xpath("//a[@class='btnPrimary']"); 
    By setorgName_text = By.xpath("//input[@id='name']");
    By adminDraft_org = By.xpath("//div[contains(text(),'Super Admin')]");
    By setorgMail_text = By.xpath("//input[contains(@placeholder,'Enter Email Address')]");
    By saveDraft_org = By.xpath("//button[normalize-space()='Save as Draft']");

    // Constructor
    public CM_OrganizationDraft_Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 
    }

    // Method to click Manage Organization link
    public void clickManageOrg() {
        wait.until(ExpectedConditions.elementToBeClickable(manage_org)).click();
    }
    
    // Method to click Manage organization
    public void clickOrg() {
        wait.until(ExpectedConditions.elementToBeClickable(org)).click();
    }

    // Method to click draft organization
    public void clickDraftOrg() {
        wait.until(ExpectedConditions.elementToBeClickable(draft_org)).click();
    }

    // Method to click for org
  	public void clicksetOrgName() {
  		wait.until(ExpectedConditions.elementToBeClickable(setorgName_text)).click();
  	}
  	
  	//Method to set name
	public void setOrgName(String pn) {
		driver.findElement(setorgName_text).sendKeys(pn);
	}
    
	// Method to click draft organization
    public void clickadminDraftOrg() {
        wait.until(ExpectedConditions.elementToBeClickable(adminDraft_org)).click();
    }
    
    // Method to click for mail org
   	public void clicksetOrgMail() {
   		wait.until(ExpectedConditions.elementToBeClickable(setorgMail_text)).click();
   	}

	//Method to set mail
	public void setOrgMail(String pn) {
		driver.findElement(setorgMail_text).sendKeys(pn);
	}
    
    // Method to click draft organization
    public void clicksaveDraftOrg() {
        wait.until(ExpectedConditions.elementToBeClickable(saveDraft_org)).click();
    }
     
 
    public boolean isOrgDisplayed(String orgName) {
        try {
            By orgResult = By.xpath(String.format("//table//td[contains(normalize-space(), '%s')]", orgName));
            WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(orgResult));
            return result.isDisplayed() && result.getText().contains(orgName);
        } catch (Exception e) {
            System.out.println("Failed to find org '" + orgName + "': " + e.getMessage());
            return false;
        }
    }
   
}
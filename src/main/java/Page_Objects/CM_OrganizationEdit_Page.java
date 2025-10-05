package Page_Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CM_OrganizationEdit_Page {

    WebDriver driver;
    public WebDriverWait wait;

    // Define locators
    By manage_org = By.xpath("//a[contains(@href, 'organization') or contains(., 'Manage Organization')]//span");
    By org = By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/nav/ul/li[2]/a/span[2]"); 
    By orgn = By.xpath("By org = By.xpath(\"//span[normalize-space()='Organizations']\");");
    By edit_btn2 = By.xpath("//tbody/tr[1]/td[6]/div[1]/button[1]//*[name()='svg']");
    By setOrgName_text2 = By.xpath("//input[@id='name']");
//    By org_btn = By.xpath("\"//table//td[contains(normalize-space(), '%s')]\", orgName");
    By org_btn = By.xpath("//*[@id=\"root\"]/div/div[3]/div[2]/div[3]/div/table/tbody/tr[1]/td[1]/div/div[1]");
    By edit_btn = By.xpath("//button[contains(@type,'button')]");
    By ChooseOrg = By.xpath("//*[@id=\"organizationForm\"]/div/div[1]/div/div[2]/div[2]/select/option[5]");
    By ChooseOrg2 = By.xpath("//*[@id=\"organizationForm\"]/div/div[1]/div/div[2]/div[2]/select/option[2]");
    By btn_update = By.xpath("//button[normalize-space()='Update']");
    By btn_ok2 = By.xpath("//button[contains(text(),'Ok')]");
	
    
    

    // Constructor
    public CM_OrganizationEdit_Page(WebDriver driver) {
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
    
 

    // Method to click organization edit form
    public void orgButton() {
            WebElement orgButton = wait.until(ExpectedConditions.elementToBeClickable(org_btn));
            orgButton.click();
    	}
        
 
    // Method to Edit Org to open edit form
    public void editButton() {
        WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(edit_btn));
        editButton.click();
        // Add delay to ensure edit form loads
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    // Method to click for org
 	public void clicksetOrgName() {
 		wait.until(ExpectedConditions.elementToBeClickable(setOrgName_text2)).click();
 	}
 	
 	//Method to clear name
 	public void clearsetOrgName() {
 		WebElement OrgName = driver.findElement(setOrgName_text2);

 		// Use JavaScript to clear the field
 		JavascriptExecutor fn = (JavascriptExecutor) driver;
 		fn.executeScript("arguments[0].value = '';", OrgName);
 	}

 	//Method to set name
 		public void setOrgName(String pn) {
 			driver.findElement(setOrgName_text2).sendKeys(pn);
 		}
    
    // Method to Edit Org to open edit form
    public void editButton2() {
     
    		WebElement editButton2 = wait.until(ExpectedConditions.elementToBeClickable(edit_btn2));
        editButton2.click();
        
        try {// Add delay to ensure edit form loads
        
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
  
    // Method to choose org type
    public void typeChooseOrg() {
        WebElement typeChooseOrg = wait.until(ExpectedConditions.elementToBeClickable(ChooseOrg));
        typeChooseOrg.click();
        // Add delay to ensure edit form loads
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public void typeChooseOrg2() {
        WebElement typeChooseOrg = wait.until(ExpectedConditions.elementToBeClickable(ChooseOrg2));
        typeChooseOrg.click();
        // Add delay to ensure edit form loads
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    
    // Method to click Update button
    public void clickUpdateButton() {
        wait.until(ExpectedConditions.elementToBeClickable(btn_update)).click();
    }

    // Method to click second Ok button after Update
    public void clickOkAfterUpdate() {
        wait.until(ExpectedConditions.elementToBeClickable(btn_ok2)).click();
    }

    

    
}
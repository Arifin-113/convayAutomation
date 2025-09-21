package Page_Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class CM_OrganizationEdit_Page {

    WebDriver driver;
    WebDriverWait wait;

    // Define locators
    By manage_org = By.xpath("//a[contains(@href, 'organization') or contains(., 'Manage Organization')]//span");
    By org = By.xpath("//span[normalize-space()='Organizations']");
//    By link_create = By.xpath("//a[@class='btnPrimary']");
//    By superadmin = By.cssSelector("//div[contains(text(),'Super Admin')]");
    
    By org_btn = By.xpath("//div[normalize-space()='Notre Dame Math Club']");
    By edit_btn = By.xpath("//button[contains(@type,'button')]");
    By select_plan_typeChooseOrg = By.xpath("//*[@id=\"organizationForm\"]/div/div[1]/div/div[2]/div[2]/select/option[5]");
    By btn_update = By.xpath("//button[normalize-space()='Update']");
    By btn_ok2 = By.xpath("//button[contains(text(),'Ok')]");
//    By edit_btn2 = By.xpath("//*[@id=\"root\"]/div/div[3]/div[2]/div[3]/div/table/tbody/tr[2]/td[6]/div/button[1]");
    

    // Constructor
    public CM_OrganizationEdit_Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // Default timeout
    }

    // Method to click Manage Organization link
    public void clickManageOrg() {
        wait.until(ExpectedConditions.elementToBeClickable(manage_org)).click();
    }

    // Method to click Manage Plans
    public void clickOrg() {
        wait.until(ExpectedConditions.elementToBeClickable(org)).click();
    }

 // Method to Edit Plan A to open edit form
    public void orgButton() {
        WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(org_btn));
        editButton.click();
        // Add delay to ensure edit form loads
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
 // Method to Edit Plan A to open edit form
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
  
 // Method to choose org
    public void typeChooseOrg() {
        WebElement typeChooseOrg = wait.until(ExpectedConditions.elementToBeClickable(select_plan_typeChooseOrg));
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

    // Method to click second Ok button (after Update)
    public void clickOkAfterUpdate() {
        wait.until(ExpectedConditions.elementToBeClickable(btn_ok2)).click();
    }

    

    
}
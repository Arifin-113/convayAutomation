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

public class CM_ManagePlansDeleteDraft_Page {

    WebDriver driver;
    WebDriverWait wait;

    // Define locators
    By manage_org = By.xpath("//a[contains(@href, 'organization') or contains(., 'Manage Organization')]//span");
    By manage_plans = By.xpath("//span[normalize-space()='Manage Plans']");
    By link_create = By.xpath("//a[contains(text(),'Create')]");
    By input_plan_name = By.cssSelector("input[placeholder='Enter Plan Name']");
    By select_plan_typeDropdown = By.xpath("//select[@name='planType']");
    By select_plan_typeChoose = By.xpath("//*[@id='root']/div/div[3]/div/form/div/div/div/div[2]/div[2]/select/option[3]");
    
    By btn_draft = By.xpath("//button[normalize-space()='Save as Draft']");
    By btn_ok1 = By.xpath("//button[contains(text(),'Ok')]");
    By btn_filters = By.xpath("//button[normalize-space()='Filters']");
    By btn_filters2 = By.xpath("//h4[normalize-space()='Status']//span//*[name()='svg']");
    By header_status = By.xpath("//h4[normalize-space()='Status']");
    By header_status2 = By.xpath("//h4[normalize-space()='Status']//span//*[name()='svg']");
    By label_draft = By.xpath("//label[normalize-space()='Draft']");
    
    By manage_plans2 = By.xpath("//h1[normalize-space()='Manage Plans']");
    
    By btn_draftdel = By.xpath("//tbody//tr//button[2]//*[name()='svg']");
    By btn_draftdelok = By.xpath("//button[@class='Plan_deleteButton__obfxh']");
  
    
    

    // Constructor
    public CM_ManagePlansDeleteDraft_Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // Default timeout
    }

    // Method to click Manage Organization link
    public void clickManageOrg() {
        wait.until(ExpectedConditions.elementToBeClickable(manage_org)).click();
    }

    // Method to click Manage Plans
    public void clickManagePlans() {
        wait.until(ExpectedConditions.elementToBeClickable(manage_plans)).click();
    }

    // Method to click Create link
    public void clickCreateLink() {
        wait.until(ExpectedConditions.elementToBeClickable(link_create)).click();
        
    }

    // Method to enter Plan Name
    public void enterPlanName(String planName) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(input_plan_name));
        input.clear();
        input.sendKeys(planName);
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
    
    // Method to clickmanage_plans2
    public void clickmanage_plans2() {
        wait.until(ExpectedConditions.elementToBeClickable(manage_plans2)).click();
    }
    
    // Method to click del button (after Create)
    public void clickDelDraft() {
        wait.until(ExpectedConditions.elementToBeClickable(btn_draftdel)).click();
        
    }
    
    // Method to click del button (after Create)
    public void clickDelDraftok() {
        wait.until(ExpectedConditions.elementToBeClickable(btn_draftdelok)).click();
        
    }

    
}
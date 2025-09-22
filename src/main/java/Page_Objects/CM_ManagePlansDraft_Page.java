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

public class CM_ManagePlansDraft_Page {

    WebDriver driver;
    WebDriverWait wait;

    // Define locators
    By manage_org = By.xpath("//a[contains(@href, 'organization') or contains(., 'Manage Organization')]//span");
    By manage_plans = By.xpath("//span[normalize-space()='Manage Plans']");
    By link_create = By.xpath("//a[contains(text(),'Create')]");

    By setPlanName_text = By.cssSelector("input[placeholder='Enter Plan Name']");
    By select_plan_typeDropdown = By.xpath("//select[@name='planType']");
    By select_plan_typeChoose = By.xpath("//*[@id='root']/div/div[3]/div/form/div/div/div/div[2]/div[2]/select/option[3]");
    By btn_draft = By.xpath("//button[normalize-space()='Save as Draft']");
    By btn_ok1 = By.xpath("//button[contains(text(),'Ok')]");
    

    // Constructor
    public CM_ManagePlansDraft_Page(WebDriver driver) {
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
        // Add small delay to ensure page loads
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public void setPlanName(String user) {
		driver.findElement(setPlanName_text).sendKeys(user);
	}

    // Method to click Plan Type dropdown
    public void clickPlanTypeDropdown() {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(select_plan_typeDropdown));
        try {
            dropdown.click();
        } catch (Exception e) {
            System.out.println("Standard click failed, trying JavaScript click: " + e.getMessage());
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", dropdown);
        }
    }

    // Method to select Plan Type (User based or Organization based)
    public void selectPlanType(String planType) {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(select_plan_typeDropdown));
        try {
            // Try Select class for <select> dropdown
            Select select = new Select(dropdown);
            select.selectByVisibleText(planType); // e.g., "User based"
        } catch (Exception e) {
            // Fallback: Click the specific option (User based)
            System.out.println("Select class failed, trying direct option click: " + e.getMessage());
            wait.until(ExpectedConditions.elementToBeClickable(select_plan_typeChoose)).click();
        }
    }

    // Method to get available dropdown options for debugging
    public List<String> getPlanTypeOptions() {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(select_plan_typeDropdown));
        try {
            Select select = new Select(dropdown);
            return select.getOptions().stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println("Unable to get options with Select, trying custom dropdown: " + e.getMessage());
            List<WebElement> options = driver.findElements(By.xpath("//*[@id='root']/div/div[3]/div/form/div/div/div/div[2]/div[2]/select/option"));
            return options.stream().map(WebElement::getText).collect(Collectors.toList());
        }
    }

    // Method to click Create button
    public void clickDraftButton() {
        wait.until(ExpectedConditions.elementToBeClickable(btn_draft)).click();
    }

    // Method to click first Ok button (after Create)
    public void clickOkAfterDraft() {
        wait.until(ExpectedConditions.elementToBeClickable(btn_ok1)).click();
    }

    
}
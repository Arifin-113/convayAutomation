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

public class CM_ManagePlansEditPlan_Page {

    WebDriver driver;
    WebDriverWait wait;

    // Define locators
    By manage_org = By.xpath("//li[5]//a[1]//span[1]");
    By manage_plans = By.xpath("//span[normalize-space()='Manage Plans']");
    By input_plan_name = By.cssSelector("input[placeholder='Enter Plan Name']");
    By div_plan_a = By.xpath("//div[contains(text(),'Plan A')]");
    By btn_update = By.xpath("//button[normalize-space()='Update']");
    By btn_ok2 = By.xpath("//button[contains(text(),'Ok')]");
    By btn_filters = By.xpath("//button[normalize-space()='Filters']");
    By header_status = By.xpath("//h4[normalize-space()='Status']");
    By label_draft = By.xpath("//label[normalize-space()='Draft']");
    By div_content_area = By.xpath("//div[@class='contentArea']");
    By img_avatar = By.xpath("//div[@class='avatar']//img[@alt='Avatar']");

    // Constructor
    public CM_ManagePlansEditPlan_Page(WebDriver driver) {
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

    

    // Method to verify Plan A exists
    public boolean isPlanADisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(div_plan_a)).isDisplayed();
    }

    // Method to update Plan Name
    public void updatePlanName(String updatedPlanName) {
        WebElement planNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(input_plan_name));
        planNameInput.clear();
        planNameInput.sendKeys(updatedPlanName);
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
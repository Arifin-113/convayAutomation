package Utilities;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Extent_Reports implements ITestListener {

	public ExtentSparkReporter sparkReporter; // UI of the report
	public ExtentReports extent; // Populate common info on the report
	public ExtentTest test; // Creating test case entries in the report and update status of the test

	public void onStart(ITestContext context) {

		// Get the current timestamp using java.time
		String timestamp = DateTimeFormatter.ofPattern("yyyy.MM.dd.HH.mm.ss").format(LocalDateTime.now());

		// Define the report file with a timestamp
		String reportName = "ExtentReport_" + timestamp + ".html";
		String reportPath = System.getProperty("user.dir") + "/Reports/" + reportName;

		// Initialize ExtentSparkReporter with the timestamped file path
		sparkReporter = new ExtentSparkReporter(reportPath);

		sparkReporter.config().setDocumentTitle("Automation Report"); // Title of the report
		sparkReporter.config().setReportName("Functional Testing"); // Name of the report
		sparkReporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);

		// Get system information dynamically
	    try {
	        String computerName = InetAddress.getLocalHost().getHostName();
	        extent.setSystemInfo("Computer Name", computerName);
	    } catch (UnknownHostException e) {
	        extent.setSystemInfo("Computer Name", "Unknown");
	    }

	    // Dynamically setting environment, OS, tester name, and browser
	    extent.setSystemInfo("Environment", System.getProperty("env", "QA")); // default to "QA" if not set
	    extent.setSystemInfo("Tester Name", System.getProperty("user.name"));
	    extent.setSystemInfo("OS", System.getProperty("os.name"));
	    extent.setSystemInfo("Browser Name", System.getProperty("browser", "Chrome")); // default to "Chrome"
	
	}

	public void onTestSuccess(ITestResult result) {

		test = extent.createTest(result.getName()); // Create a new entry in the report
		test.log(Status.PASS, "Test case PASSED is:" + result.getName());

	}

	 public void onTestFailure(ITestResult result) {
		 
	        test = extent.createTest(result.getName());
	        test.log(Status.FAIL, "Test case FAILED is: " + result.getName());
	        test.log(Status.FAIL, "Test case FAILED cause is: " + result.getThrowable());

	
	    }

	public void onTestSkipped(ITestResult result) {

		test = extent.createTest(result.getName());
		test.log(Status.SKIP, "Test case SKIPPED is:" + result.getName());

	}

	public void onFinish(ITestContext context) {

		extent.flush();
	}

}

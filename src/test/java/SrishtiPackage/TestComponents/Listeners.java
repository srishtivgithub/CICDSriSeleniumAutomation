package SrishtiPackage.TestComponents;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import sripackage.resources.ExtentReporterNG;

public class Listeners extends BaseTest implements ITestListener {
	
	ExtentReports extent = ExtentReporterNG.getReportObject();
	ExtentTest test;
	//for thread safety in parallel execution
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
	//for logs
	Logger log=LogManager.getLogger(Listeners.class);

	WebDriver driver;

	public void onTestStart(ITestResult result) {

		test = extent.createTest(result.getMethod().getMethodName());
		extentTest.set(test);
	}

	public void onTestSuccess(ITestResult result) {
		test.log(Status.PASS, "Test Passed");
	}

	public void onTestFailure(ITestResult result) {
		// test.fail(result.getThrowable());
		// to avoid issue in parallel execution use below instead of above
		extentTest.get().fail(result.getThrowable());
		
		//Log Selenium exception/errors
		if(result.getThrowable()!=null) {
			log.error("Test Failed: "+result.getMethod().getMethodName(),"Failure Reason:" +result.getThrowable());
		}

		// getting driver specific to test method and giving life to driver here
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e1) {

			log.error("Driver fetch failed", e1);
		}
		
		//Log Console errors
		try {
		LogEntries logs=driver.manage().logs().get(LogType.BROWSER);
		List<LogEntry> logList=logs.getAll();
		
		for(LogEntry logEntry : logList) {
			String logMsg=logEntry.getMessage();
			log.error("BROWSER LOG:"+logMsg);
		  }
		} catch(Exception e2) {
			log.error("Unable to capture browser logs:"+e2);
		}
		// capture screenshot to attach in report
		String screenshotFilePath = null;
		try {
			screenshotFilePath = getScreenshot(result.getMethod().getMethodName(), driver);
		} catch (IOException e3) {
		  log.error("Screenshot filepath is null"+e3);
		}

		// test.addScreenCaptureFromPath(screenshotFilePath,result.getMethod().getMethodName());
		// to avoid issue in parallel execution use below instead of above
		extentTest.get().addScreenCaptureFromPath(screenshotFilePath, result.getMethod().getMethodName());
	}

	
	public void onTestSkipped(ITestResult result) {
		// not implemented
	}

	
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// not implemented
	}

	/**
	 * Invoked each time a test fails due to a timeout.
	 *
	 * @param result <code>ITestResult</code> containing information about the run
	 *               test
	 */
	public void onTestFailedWithTimeout(ITestResult result) {
		onTestFailure(result);
	}

	/**
	 * Invoked before running all the test methods belonging to the classes inside
	 * the &lt;test&gt; tag and calling all their Configuration methods.
	 *
	 * @param context The test context
	 */
	public void onStart(ITestContext context) {
		// not implemented
	}

	/**
	 * Invoked after all the test methods belonging to the classes inside the
	 * &lt;test&gt; tag have run and all their Configuration methods have been
	 * called.
	 *
	 * @param context The test context
	 */
	public void onFinish(ITestContext context) {
		// not implemented
		extent.flush();
	}
}

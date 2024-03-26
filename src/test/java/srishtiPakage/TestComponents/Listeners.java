package srishtiPakage.TestComponents;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import sripackage.resources.ExtentReporterNG;

public class Listeners extends BaseTest implements ITestListener {

	ExtentReports extent=ExtentReporterNG.getReportObject();
	ExtentTest test;
	ThreadLocal<ExtentTest> extentTest=new ThreadLocal<ExtentTest>();
	
	WebDriver driver;
	 public void onTestStart(ITestResult result) {
		    
		test=extent.createTest(result.getMethod().getMethodName());
		extentTest.set(test);
		  }

		  /**
		   * Invoked each time a test succeeds.
		  
		   */
	 public void onTestSuccess(ITestResult result) {
		    test.log(Status.PASS, "Test Passed");
		  }

		  /**
		   * Invoked each time a test fails.
		   */
	 public void onTestFailure(ITestResult result) {
		    //test.fail(result.getThrowable()); 
		 //to avoid issue in parallel execution use below instead of above
		 extentTest.get().fail(result.getThrowable());
		 
		    
		    //getting driver specific to test method and giving life to driver here
		    try {
				driver=(WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
			} catch (Exception e1) {
				
				e1.printStackTrace();
			}
		    
		    //screenshot to attach in report
		    String screenshotFilePath=null;
		     try {
				screenshotFilePath = getScreenshot(result.getMethod().getMethodName(), driver);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		     //test.addScreenCaptureFromPath(screenshotFilePath, result.getMethod().getMethodName());
		   //to avoid issue in parallel execution use below instead of above
		     extentTest.get().addScreenCaptureFromPath(screenshotFilePath, result.getMethod().getMethodName());
		  }
	 

		  /**
		   * Invoked each time a test is skipped.
		   */
	 public void onTestSkipped(ITestResult result) {
		    // not implemented
		  }

		  /**
		   * Invoked each time a method fails but has been annotated with successPercentage and this failure
		  
		   */
	 public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		    // not implemented
		  }

		  /**
		   * Invoked each time a test fails due to a timeout.
		   *
		   * @param result <code>ITestResult</code> containing information about the run test
		   */
	 public void onTestFailedWithTimeout(ITestResult result) {
		    onTestFailure(result);
		  }

		  /**
		   * Invoked before running all the test methods belonging to the classes inside the &lt;test&gt;
		   * tag and calling all their Configuration methods.
		   *
		   * @param context The test context
		   */
	 public void onStart(ITestContext context) {
		    // not implemented
		  }

		  /**
		   * Invoked after all the test methods belonging to the classes inside the &lt;test&gt; tag have
		   * run and all their Configuration methods have been called.
		   *
		   * @param context The test context
		   */
	 public void onFinish(ITestContext context) {
		    // not implemented
		 extent.flush();
		  }
}

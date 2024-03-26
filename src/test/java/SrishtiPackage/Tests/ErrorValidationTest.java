package SrishtiPackage.Tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.sun.net.httpserver.Authenticator.Retry;

import srishtiPakage.TestComponents.BaseTest;

public class ErrorValidationTest extends BaseTest {

	@Test(groups= {"errorHandlingCases"})//, retryAnalyzer=Retry.class)
	public void errorValidationLoginPage() throws IOException, InterruptedException {

		

		// drive object creation within page object classes encapsulating from test
		landingPage.loginApplication("incorrect@gmail.com", "email2@");
		String errorMsg=landingPage.getErrorMessage();
		Assert.assertEquals("Incorrect email or password.", errorMsg);

	}

}

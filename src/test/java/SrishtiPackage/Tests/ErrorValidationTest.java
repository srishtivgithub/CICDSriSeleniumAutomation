package SrishtiPackage.Tests;

import java.io.IOException;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.sun.net.httpserver.Authenticator.Retry;

import SrishtiPackage.data.DataProviderUtility;
import srishtiPakage.TestComponents.BaseTest;

public class ErrorValidationTest extends BaseTest {

	@Test(dataProvider="getDataFromExternalFileForInvalidCredential",
			dataProviderClass = DataProviderUtility.class,
			groups= {"errorHandlingCases"})//, retryAnalyzer=Retry.class)
	public void errorValidationLoginPage(HashMap<String, String> value) throws IOException, InterruptedException {
		
       // drive object creation within page object classes encapsulating from test
		landingPage.loginApplication(value.get("username"), value.get("password"));
		String errorMsg=landingPage.getErrorMessage();
		Assert.assertEquals("Incorrect email or password.", errorMsg);

	}

}

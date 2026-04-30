package SrishtiPackage.Tests;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import SrishtiPackage.TestComponents.BaseTest;
import SrishtiPackage.data.DataProviderUtility;
import sripackage.AbstractComponents.AbstractComponents;
import sripackage.pageobjects.LandingPage;
import sripackage.pageobjects.LoginPage;
import sripackage.pageobjects.RegistrationPage;

public class LoginNegativeTest extends BaseTest{
	
	@Test(priority=1, dataProvider = "getLoginToastErrorData", dataProviderClass = DataProviderUtility.class,enabled=false)
	public void loginWithInValidCredential(HashMap<String, String> map) {
		LandingPage landingPage=new LandingPage(driver);
		LoginPage loginPage=new LoginPage(driver);
		landingPage.loginApplication(map.get("Email"), map.get("Password"));
		Assert.assertEquals(loginPage.getIncorrectCredentialToastText(), map.get("ExpectedMsg"));
		
		
	}
	
	@Test(priority=2, dataProvider = "getLoginInlineErrorData", dataProviderClass = DataProviderUtility.class,enabled=true)
	public void loginWithPartialCredential(HashMap<String, String> map) {
		LandingPage landingPage=new LandingPage(driver);
		LoginPage loginPage=new LoginPage(driver);
		landingPage.loginApplication(map.get("Email"), map.get("Password"));
		Assert.assertEquals(loginPage.getInlineValidationError(), map.get("ExpectedMsg"));	
	}


	@Test(priority=3, dataProvider = "getLoginInlineErrorEmailPassword", dataProviderClass = DataProviderUtility.class)
	public void loginWithImValidCredential(HashMap<String, String> map) {
		LandingPage landingPage=new LandingPage(driver);
		LoginPage loginPage=new LoginPage(driver);
		landingPage.loginApplication(map.get("Email"), map.get("Password"));
		Assert.assertEquals(loginPage.getRequiredEmailError(), map.get("ExpectedMsg1"));	
		Assert.assertEquals(loginPage.getRequiredPasswordError(), map.get("ExpectedMsg2"));	
	}
	

}

package SrishtiPackage.Tests;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import SrishtiPackage.data.DataProviderUtility;
import sripackage.AbstractComponents.AbstractComponents;
import sripackage.pageobjects.LandingPage;
import sripackage.pageobjects.LoginPage;
import sripackage.pageobjects.RegistrationPage;
import srishtiPakage.TestComponents.BaseTest;

public class LoginPositiveTest extends BaseTest{
	LandingPage landingPage;
	RegistrationPage registrationPage;
	LoginPage loginPage;
	AbstractComponents ac;
	@Test(priority=1)
	public void loginWithValidCredential() {
		
	    landingPage=new LandingPage(driver);
		landingPage.loginApplication(prop.getProperty("userEmail"), prop.getProperty("userPassword"));
		ac=new AbstractComponents(driver);
		
		Assert.assertTrue(ac.isUserLoggedIn(), "Login was not successful and Signout header not visible");
		Assert.assertTrue(ac.isHomeHeaderVisible(), "Login was not successful and Home header not visible");
		Assert.assertTrue(ac.isOrdersHeaderVisible(), "Login was not successful and Orders header not visible");
		Assert.assertTrue(ac.isCartHeaderVisible(), "Login was not successful and Cart header not visible");
		
	}
	//LOG_TC_010
	@Test(priority=2)
	public void verifyPasswordMasking() {
		registrationPage=new RegistrationPage(driver);
		loginPage=new LoginPage(driver);
		loginPage.enterEmailPasswordinLogin(prop.getProperty("userEmail"), prop.getProperty("userPassword"));
		Assert.assertTrue(registrationPage.isPasswordMasked(),"Password is not masked");
		
		
	}
	//LOG_TC_012
	@Test(priority=3 )
	public void verifyForgotPasswordNavigation() {
		
		loginPage=new LoginPage(driver);
		loginPage.clickForgotPasswordLink();
		Assert.assertEquals(loginPage.validateForgotPasswordPageUrl(), "https://rahulshettyacademy.com/client/#/auth/password-new");
	
		
	}
	//LOG_TC_013
	@Test(priority=4 )
	public void verifyRegisterNavigation() {
		
		loginPage=new LoginPage(driver);
		loginPage.clickRegisterHereLink();
		Assert.assertEquals(loginPage.validateRegisterPageUrl(), "https://rahulshettyacademy.com/client/#/auth/register");
	
		
	}
	
	@Test(priority=5, dataProvider = "getLoginCredentialFromDataGenerator", dataProviderClass = DataProviderUtility.class
			)
	public void verifyPasswordReset(HashMap<String, String> map) {
		registrationPage=new RegistrationPage(driver);
		landingPage=new LandingPage(driver);
		landingPage.loginApplication(map.get("email"), map.get("password"));
		Assert.assertTrue(registrationPage.isPasswordMasked(),"Password is not masked");
		
		
	}

}

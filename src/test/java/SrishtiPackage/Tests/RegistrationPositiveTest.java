package SrishtiPackage.Tests;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import SrishtiPackage.TestComponents.BaseTest;
import SrishtiPackage.data.DataProviderUtility;
import sripackage.AbstractComponents.AbstractComponents;
import sripackage.pageobjects.LandingPage;
import sripackage.pageobjects.RegistrationPage;

public class RegistrationPositiveTest extends BaseTest{

	
	@Test(priority=1, dataProvider = "getRegistrationData" , dataProviderClass = DataProviderUtility.class 
			)
	public void registerUserWithAllDetails(HashMap<String,String> map) {
		RegistrationPage registrationPage=new RegistrationPage(driver);
		registrationPage.clickRegisterLinkFromLoginPage();
		registrationPage.userRegisterWithAllDetails(map);
		String msg=registrationPage.getregistrationSuccessMsg();
		Assert.assertEquals(msg, "Account Created Successfully");
		
		
		
	}
	@Test(priority=2, dataProvider = "getRegistrationData" , dataProviderClass = DataProviderUtility.class
			)
	public void registerUserWithMandatoryDetails(HashMap<String,String> map) {
		RegistrationPage registrationPage=new RegistrationPage(driver);
		registrationPage.clickRegisterLinkFromLoginPage();
		registrationPage.userRegisterWithMandatoryDetails(map);
		String msg=registrationPage.getregistrationSuccessMsg();
		Assert.assertEquals(msg, "Account Created Successfully");
		
		
		
		
	}
	@Test(priority=3, dataProvider = "getRegistrationData" , dataProviderClass = DataProviderUtility.class)
	public void loginAfterRegistration(HashMap<String,String> map) {
		RegistrationPage registrationPage=new RegistrationPage(driver);
		registrationPage.clickRegisterLinkFromLoginPage();
		registrationPage.userRegisterWithMandatoryDetails(map);
		LandingPage landingPage=registrationPage.nagivateToLoginPageAfterUserRegister();
		landingPage.loginApplication(map.get("email"),map.get("password"));
		AbstractComponents ac=new AbstractComponents(driver);
		Boolean flag=ac.isUserLoggedIn();
		Assert.assertTrue(flag, "Login was not successful");
	}
	

}

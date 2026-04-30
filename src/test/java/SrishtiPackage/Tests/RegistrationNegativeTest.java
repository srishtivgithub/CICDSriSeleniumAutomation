package SrishtiPackage.Tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import SrishtiPackage.TestComponents.BaseTest;
import SrishtiPackage.data.DataProviderUtility;
import sripackage.pageobjects.RegistrationPage;

public class RegistrationNegativeTest extends BaseTest {

	@Test(priority=1, dataProvider = "getRegistrationMandatoryValidationFieldData", dataProviderClass = DataProviderUtility.class
			,enabled=false)
	public void registerWithAllEmptyFields(HashMap<String,String> expectedMap) {
		RegistrationPage registrationPage=new RegistrationPage(driver);
		registrationPage.clickRegisterLinkFromLoginPage();
		registrationPage.clickSubmit();
		//verify red border
		LinkedHashMap<String, Boolean> map=registrationPage.redBordersPresentForMandatoryFields();
		ArrayList<String> list=registrationPage.fieldsWithNoRedBorderPresent(map);
		Assert.assertTrue(list.isEmpty(), "These field do not contain red border:"+list);
		
		//verify required text
		LinkedHashMap actualMap=registrationPage.getAllRequiredTextValidationFields();
		ArrayList resultList=registrationPage.getListOfFieldsWithTextValidation(expectedMap, actualMap );
		Assert.assertTrue(resultList.isEmpty(),"validation msg not showing for field:"+resultList);
		
		}
	@Test(priority=2, dataProvider = "getInvalidRegistrationData", dataProviderClass = DataProviderUtility.class
			,enabled=true)
	public void registerWithInvalidInputsShouldShowInlineErrors(HashMap<String,String> invalidRegisterMap) {
		RegistrationPage registrationPage=new RegistrationPage(driver);
		registrationPage.clickRegisterLinkFromLoginPage();
		registrationPage.fillRegistrationWithMandatoryDetails(invalidRegisterMap);
		registrationPage.clickSubmit();
		// Step 3 — collect all inline error messages from page
	    HashMap<String, String> actualInlineMsg = registrationPage.inLineMsgForEmailPhonePassword();

	    // Step 4 — get expected error from JSON
	    String expectedError = invalidRegisterMap.get("expectedError");
	    String scenario      = invalidRegisterMap.get("scenario");
	    
	 // Step 5 — assert expected error appears somewhere in actual errors
	    Assert.assertTrue(
	    		actualInlineMsg.containsValue(expectedError),
	        "Scenario [" + scenario + "] FAILED"
	        + "\nExpected error : " + expectedError
	        + "\nActual errors  : " + actualInlineMsg
	    );
		//HashMap<String,String> actualInlineMsg=registrationPage.inLineMsgForEmailPhonePassword();
		//Assert.assertTrue(actualInlineMsg.containsValue(invalidRegisterMap.get("expectedError")), "Inline error msg not display:");
		
	}
	
	@Test(priority=3, dataProvider = "getRegistrationData", dataProviderClass = DataProviderUtility.class
			,enabled=false)
	public void registerWithalreadyRegisteredEmail(HashMap<String,String> map) {
		RegistrationPage registrationPage=new RegistrationPage(driver);
		 // ── PREREQUISITE — register user for first time ──────────────────
		registrationPage.clickRegisterLinkFromLoginPage();
		registrationPage.userRegisterWithMandatoryDetails(map);
		String msg=registrationPage.getregistrationSuccessMsg();
		// verify first registration succeeded — prerequisite assertion
		Assert.assertEquals(msg, "Account Created Successfully");
		// ── ACTUAL TEST — attempt duplicate registration ──────────────────
		registrationPage.nagivateToLoginPageAfterUserRegister();
		registrationPage.clickRegisterLinkFromLoginPage();
		// register again with SAME map — same email triggers duplicate error
		registrationPage.userRegisterWithMandatoryDetails(map);
		String text=registrationPage.getTextForEmailExistToast();
		Assert.assertEquals(text, "User already exisits with this Email Id!", "Duplicate email toast message mismatch"
		        + " | Email used: " + map.get("email"));
	}
	
	@Test(priority=4,enabled=false)
	public void verifyPasswordMasking(HashMap<String,String> map) {
		RegistrationPage registrationPage=new RegistrationPage(driver);
		registrationPage.clickRegisterLinkFromLoginPage();
		registrationPage.fillRegistrationWithMandatoryDetails(map);
		Assert.assertTrue(registrationPage.isPasswordMasked(),"Password is not masked");
		Assert.assertTrue(registrationPage.isConfirmPasswordMasked(),"ConfirmPassword is not masked");
		
	}
	@Test(priority=5)
	public void verifyOptionsPresentInOccupation() {
		RegistrationPage registrationPage=new RegistrationPage(driver);
		registrationPage.clickRegisterLinkFromLoginPage();
		Assert.assertTrue(registrationPage.isAllOccupationOptionsPreset(), "Occupation options are incorrect");
		
	}
	
	
	

}

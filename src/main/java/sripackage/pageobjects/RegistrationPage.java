package sripackage.pageobjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import sripackage.AbstractComponents.AbstractComponents;
import sripackage.resources.DataGeneratorUtility;

public class RegistrationPage extends AbstractComponents {

	WebDriver driver;
	

	public RegistrationPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(linkText = "Register")
	WebElement registerLink;

	@FindBy(id = "firstName")
	WebElement firstNameField;

	@FindBy(id = "lastName")
	WebElement lastNameField;

	@FindBy(id = "userEmail")
	WebElement userEmailField;

	@FindBy(id = "userMobile")
	WebElement userMobileField;

	@FindBy(xpath = "//select[@formcontrolname='occupation']")
	WebElement occupationDropdown;
	
	@FindBy(xpath = "//select[@formcontrolname='occupation']/option")
	List<WebElement> occupationDropdownValues;
	

	@FindBy(id = "userPassword")
	WebElement userPasswordField;

	@FindBy(id = "confirmPassword")
	WebElement confirmPasswordField;

	@FindBy(css = "input[type='checkbox']")
	WebElement ageCheckbox;

	@FindBy(xpath = "//input[@type='submit' and @value='Register']")
	WebElement submitButton;

	@FindBy(xpath = "//h1[@class='headcolor']")
	WebElement registerSuccessMsg;

	@FindBy(css = "button[class*='btn-primary']")
	WebElement loginButtonAfterRegistration;
	
	
	@FindBy(xpath = "//div[@class='ng-star-inserted' and contains(text(),'required') or contains(text(),'checkbox') ]")
	List<WebElement> requiredMandatoryTexts;
	
	@FindBy(xpath = "//div[text()='*First Name is required']")
	WebElement requiredMandatoryTextForFirstName;
	
	@FindBy(xpath = "//div[text()='*Email is required']")
	WebElement requiredMandatoryTextForEmail;
	
	@FindBy(xpath = "//div[text()='*Phone Number is required']")
	WebElement requiredMandatoryTextForPhone;
	
	@FindBy(xpath = "//div[text()='*Password is required']")
	WebElement requiredMandatoryTextForPassword;
	
	@FindBy(xpath = "//div[text()='Confirm Password is required']")
	WebElement requiredMandatoryTextForConfirmPassword;
	
	@FindBy(xpath = "//div[text()='*Please check above checkbox']")
	WebElement requiredMandatoryTextForAgeCheckBox;
	

	@FindBy(xpath = "	//input[contains(@class,'ng-invalid') and not(@type='checkbox')]")
	List<WebElement> redBorderOnMandatoryFields;
	
	@FindBy(xpath = "//div[contains(@class,'form-group')]/label[@for='firstName']//following-sibling::input[contains(@class,'ng-invalid') ]")
	List<WebElement> redBorderFirstName;
	
	@FindBy(xpath = "//div[contains(@class,'form-group')]/label[@for='email']//following-sibling::input[contains(@class,'ng-invalid') ]")
	List<WebElement> redBorderEmail;
	
	@FindBy(xpath = "//div[contains(@class,'form-group')]/label[@for='userPhone']//following-sibling::input[contains(@class,'ng-invalid') ]")
	List<WebElement> redBorderPhone;
	
	@FindBy(xpath = "//div[contains(@class,'form-group')]/label[@for='password']//following-sibling::input[contains(@class,'ng-invalid') ]")
	List<WebElement> redBorderPassword;
	
	@FindBy(xpath = "//div[contains(@class,'form-group')]/label[@for='confirmPassword']//following-sibling::input[contains(@class,'ng-invalid') ]")
	List<WebElement> redBorderConfirmPassword;
	
	@FindBy(xpath = "//div[text()='*Enter Valid Email']")
	WebElement inlineEmailErrorMsg;
	
	@FindBy(xpath = "//div[text()='*Phone Number must be 10 digit']")
	WebElement inlinePhoneErrorMsg;
	
	@FindBy(xpath = "//div[text()='Password and Confirm Password must match with each other.']")
	WebElement inlinePasswordMismatchErrorMsg;
	
	@FindBy(css="div[class*='toast-error']")
	WebElement emailExistToast;
	
	
	
	
	
	
    public void clickRegisterLinkFromLoginPage() {
		waitForWebElementToAppear(registerLink);
		registerLink.click();
	}

	// Receives HashMap, extracts each value, fills the form
	public void fillRegistrationWithAllDetails(HashMap<String, String> map) {
		// extract from map — keys must match exactly what generator put in
		firstNameField.sendKeys(map.get("firstname"));
		lastNameField.sendKeys(map.get("lastname"));
		userEmailField.sendKeys(map.get("email"));
		userMobileField.sendKeys(map.get("phone"));
		userPasswordField.sendKeys(map.get("password"));
		confirmPasswordField.sendKeys(map.get("password")); // confirm = same password
		ageCheckbox.click();
		selectGender(map.get("gender"));
		selectFromDropdownByVisibleText(occupationDropdown, map.get("occupation"));
	
	}

	public void selectAgeCheckBox() {
		waitForWebElementToAppear(ageCheckbox);
		ageCheckbox.click();
	}

	public void selectGender(String gender) {
		String xpath = "//input[@value='" + gender + "']";
		driver.findElement(By.xpath(xpath)).click();
	}

	public void clickSubmit() {
		waitForWebElementToAppear(submitButton);
		submitButton.click();
		// return new LandingPage(driver); // returns login page after registration
	}

	public void userRegisterWithAllDetails(HashMap<String, String> map) {
		fillRegistrationWithAllDetails(map);
		clickSubmit();
		// return clickRegisterButton();
		
	}

	public String getregistrationSuccessMsg() {
		waitForWebElementToAppear(registerSuccessMsg);
		String successMsg = registerSuccessMsg.getText();
		return successMsg;

	}

	public LandingPage nagivateToLoginPageAfterUserRegister() {
		waitForWebElementToAppear(loginButtonAfterRegistration);
		loginButtonAfterRegistration.click();
		//landingPage = new LandingPage(driver);
		return new LandingPage(driver);
	}

	public void fillRegistrationWithMandatoryDetails(HashMap<String, String> map) {
		
		firstNameField.sendKeys(map.get("firstname"));
		 // lastname guard — may be empty string in negative JSON
	    if (map.get("lastname") != null && !map.get("lastname").isEmpty()) {
	        lastNameField.sendKeys(map.get("lastname"));
	    }


		userEmailField.sendKeys(map.get("email"));
		userMobileField.sendKeys(map.get("phone"));
		userPasswordField.sendKeys(map.get("password"));
		
		//this will run for registerWithInvalidInputsShouldShowInlineErrors() in RegistrationNegativeTest class
		//since we created json file and in that there is a key for confirmpassword to check password mismatch
		if(map.containsKey("confirmpassword")){
			  confirmPasswordField.sendKeys(map.get("confirmpassword"));
			}
		//this will run for other normal data registration methods
		else {
			  confirmPasswordField.sendKeys(map.get("password"));
			}
		
		selectAgeCheckBox();
		
		

	}
	public boolean isPasswordMasked() {
		String passwordType=userPasswordField.getAttribute("type");
		
		return passwordType.equals("password");
	}
	public boolean isConfirmPasswordMasked() {
		String confirmPasswordType=userPasswordField.getAttribute("type");
		
		return confirmPasswordType.equals("password");
	}

	public void userRegisterWithMandatoryDetails(HashMap<String, String> map) {
		fillRegistrationWithMandatoryDetails(map);
		clickSubmit();
		// return clickRegisterButton();
		
	}
	
	public LinkedHashMap<String, Boolean> redBordersPresentForMandatoryFields() {
		LinkedHashMap<String, Boolean> map=new LinkedHashMap<String, Boolean>();
		map.put("firstname", !redBorderFirstName.isEmpty());
		map.put("email", !redBorderEmail.isEmpty());
		map.put("phone", !redBorderPhone.isEmpty());
		map.put("password", !redBorderPassword.isEmpty());
		map.put("confirmPassword", !redBorderConfirmPassword.isEmpty());
		
		return map;
		
		}
	
	public ArrayList<String> fieldsWithNoRedBorderPresent(LinkedHashMap<String, Boolean> map){
		ArrayList<String> list=new ArrayList<String>();
		
		for(Map.Entry<String, Boolean> m : map.entrySet()) {
			String key=m.getKey();
			Boolean value=m.getValue();
			if(value==false) {
				list.add(key); //adding elements to list for which red border didnt show despite validation
			}
		}
		
		return list;
		
	}
	public LinkedHashMap<String, String> getAllRequiredTextValidationFields() {
		LinkedHashMap<String, String> map= new LinkedHashMap<String, String>();
		map.put("firstname", isElementPresent(requiredMandatoryTextForFirstName) ? requiredMandatoryTextForFirstName.getText() : "Not Found" );
		map.put("email", isElementPresent(requiredMandatoryTextForEmail) ? requiredMandatoryTextForEmail.getText() : "Not Found" );
		map.put("phone", isElementPresent(requiredMandatoryTextForPhone) ? requiredMandatoryTextForPhone.getText() : "Not Found" );
		map.put("password", isElementPresent(requiredMandatoryTextForPassword) ? requiredMandatoryTextForPassword.getText() : "Not Found");
		map.put("confirmpassword", isElementPresent(requiredMandatoryTextForConfirmPassword) ? requiredMandatoryTextForConfirmPassword.getText() : "Not Found" );
		map.put("agecheckbox",isElementPresent(requiredMandatoryTextForAgeCheckBox) ? requiredMandatoryTextForAgeCheckBox.getText() : "Not Found" );
		
		return map;
	}
	
	public ArrayList<String> getListOfFieldsWithTextValidation(HashMap<String, String> expectedMap,LinkedHashMap<String, String> actualMap ) {
		ArrayList<String> list=new ArrayList<String>();
		for(Map.Entry<String, String> m : expectedMap.entrySet()) {
			String key=m.getKey();
			String value=m.getValue();
			
			if(!value.equals(actualMap.get(key))) {
				list.add("Expected:"+value+" but found:"+actualMap.get(key));
			}
			
			
		}
		return list;
		
	}
	
	public HashMap<String, String> inLineMsgForEmailPhonePassword() {
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("inlineEmailMsg", isElementPresent(inlineEmailErrorMsg) ? inlineEmailErrorMsg.getText() : "");
		map.put("inlinePhoneMsg", isElementPresent(inlinePhoneErrorMsg) ? inlinePhoneErrorMsg.getText() : "");
		map.put("inlinePasswordMismatchMsg", isElementPresent(inlinePasswordMismatchErrorMsg) ? inlinePasswordMismatchErrorMsg.getText() : "");
		
	
		
		System.out.println("the map for inline msg is:"+map);
		return map;
		
	}
	
	public String getTextForEmailExistToast() {
		waitForWebElementToAppear(emailExistToast);
		String text=emailExistToast.getText();
		return text;
	}
	public void clickOnOccupationDropdown() {
		occupationDropdown.click();
	}
	public boolean isAllOccupationOptionsPreset() {
		ArrayList<String> list=new ArrayList<String> ();
		list.add("Choose your occupation");
		list.add("Doctor");
		list.add("Student");
		list.add("Engineer");
		list.add("Scientist");
		
		//clickOnOccupationDropdown();->since getOptions() internally reads Seclect class options, thus no need to click on dropdown
		
		List<WebElement> options=getDropdownOptions(occupationDropdown);
		int size=options.size();
		System.out.println("size of occupation dropdown"+size);
		boolean flag=true;
		 for(int i=1;i<size;i++) { //starting by 1 since 0 index->no value
			 String value=options.get(i).getText().trim();

			 
			 if(!list.get(i).equals(value)) {
				 flag=false;
				 break;
			 }
		 }
		 return flag;
		
	}
	
}

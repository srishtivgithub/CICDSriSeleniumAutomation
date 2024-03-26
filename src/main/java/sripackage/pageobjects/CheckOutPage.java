package sripackage.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import sripackage.AbstractComponents.AbstractComponents;

public class CheckOutPage extends AbstractComponents {

	WebDriver driver;

	public CheckOutPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		// initialization
		this.driver = driver; // giving life to current class driver, driver coming from base class

		PageFactory.initElements(driver, this);// initializing and defining all @findBy with driver
	}

	@FindBy(xpath = "//input[@placeholder='Select Country']")
	WebElement countryDropdown;

	@FindBy(css = "button[class*='list-group-item'] span")
	List<WebElement> dropdownOptionsList;
	
	@FindBy(css = "a[class*='action__submit']")
	WebElement placeOrderButton;
	
	By dropdownOptionsListContainer=By.cssSelector("section[class*='list-group']");
	
	public void selectCountry(String countryName) {
		// selecting country from auto suggest drop down
		//driver.findElement(By.xpath("//input[@placeholder='Select Country']")).sendKeys("India");
		countryDropdown.sendKeys(countryName);

		//w.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("section[class*='list-group']")));
		waitForElementToAppear(dropdownOptionsListContainer);
		
		//List<WebElement> dropdownOptions = driver.findElements(By.cssSelector("button[class*='list-group-item'] span"));

		WebElement option = dropdownOptionsList.stream()
				.filter(dropdownOption -> dropdownOption.getText().contains("India")).findFirst().orElse(null);
		option.click();
	}
	
	public ConfirmationPage clickPlaceOrderButton() {
		
		//click on placeorder button
		//driver.findElement(By.cssSelector("a[class*='action__submit']")).click();
		placeOrderButton.click();
		
		return new ConfirmationPage(driver);
	}
}

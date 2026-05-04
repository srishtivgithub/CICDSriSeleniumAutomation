package SrishtiPackage.Tests;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import SrishtiPackage.TestComponents.BaseTest;
import SrishtiPackage.data.DataProviderUtility;
import io.github.bonigarcia.wdm.WebDriverManager;
import sripackage.pageobjects.CartPage;
import sripackage.pageobjects.CheckOutPage;
import sripackage.pageobjects.ConfirmationPage;
import sripackage.pageobjects.LandingPage;
import sripackage.pageobjects.OrdersPage;
import sripackage.pageobjects.ProductCataloguePage;

public class SubmitOrderTest extends BaseTest {
	// ✅ private static final = it is a CONSTANT, not a variable
    // clearly communicates — this value is FIXED and belongs to THIS test only
    private static final String PROD_NAME = "ZARA COAT 3";
    //String prodName = "ADIDAS ORIGINAL";

	@Test(dataProvider="getData",dataProviderClass = DataProviderUtility.class , groups= {"Purchase"})
	public void submitOrder(String username, String password, String prodName, String country) throws IOException, InterruptedException {

		

		// drive object creation within page object classes encapsulating from test
		ProductCataloguePage productCatalogue = landingPage.loginApplication(username, password);
		List<WebElement> products = productCatalogue.getProductList();

		

		// 2. using stream to retrive webelement of one product tile and adding it to
		// cart

		// in filter we are finding the product text
		// there may be many products with same name=ADIDAS ORIGINAL, therefore
		// gave findFirst()->which will select 1st selection else will return null if
		// nothing found

		// as text contains inside cssSelector("div.mb-3") thus we smartly used product
		// instead of driver , so it will search for p tag within the block product
		// p tag only contains the product text

		
		WebElement productElement = productCatalogue.getProductByName(prodName);

		// click on add to cart button for addidas item
		// checking green banner saying:product added to cart
		// loading spinner wait to disappear so that we can open Cart in header
		productCatalogue.addProducttoCart(prodName);
		// click on Cart button in header
		CartPage cartPage = productCatalogue.goToCartPage();// due to inhertance we can use child object to call parent
															// class method

		// check if the productname in cart is same as product which was added to cart
		// using Streams
		// CartPage cartPage=new CartPage(driver);
		Boolean match = cartPage.verifyProductDisplay(prodName);
		Assert.assertTrue(match);

		// click on checkout button
		CheckOutPage checkoutPage = cartPage.clickCheckOut();

		// selecting country from auto suggest drop down
		//String countryName = "India";
		// CheckOutPage checkoutPage=new CheckOutPage(driver);
		checkoutPage.selectCountry(country);

		// click on placeorder button
		ConfirmationPage confirmPage = checkoutPage.clickPlaceOrderButton();

		// verify confirmation message in confirmation page
		// ConfirmationPage confirmPage=new ConfirmationPage(driver);
		String confirmMsg = confirmPage.getConfirmationMessage();
		Assert.assertTrue(confirmMsg.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

		

	}
	
	//@Test(dependsOnMethods= {"submitOrder"})
	@Test
	public void checkOrderInOrders() {
		ProductCataloguePage productCatalogue = landingPage.loginApplication(prop.getProperty("userEmail"), prop.getProperty("userPassword"));
		OrdersPage orderPage=productCatalogue.goToOrders();
		
		Boolean match=orderPage.verifyProductInOrdersPage(PROD_NAME);
		Assert.assertTrue(match);
	}
	
	/*
	 * @DataProvider public Object[][] getData() {
	 * 
	 * return new Object[][] {{"dummyemailsrishti@gmail.com", "Dummyemail2@",
	 * "ADIDAS ORIGINAL"}, {"anshika@gmail.com","Iamking@000","ZARA COAT 3"}}; }
	 */

}

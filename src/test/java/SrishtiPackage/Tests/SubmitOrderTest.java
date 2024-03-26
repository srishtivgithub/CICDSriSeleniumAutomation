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

import io.github.bonigarcia.wdm.WebDriverManager;
import sripackage.pageobjects.CartPage;
import sripackage.pageobjects.CheckOutPage;
import sripackage.pageobjects.ConfirmationPage;
import sripackage.pageobjects.LandingPage;
import sripackage.pageobjects.OrdersPage;
import sripackage.pageobjects.ProductCataloguePage;
import srishtiPakage.TestComponents.BaseTest;

public class SubmitOrderTest extends BaseTest {
	
	//by using dataprovider
	String prodName = "ADIDAS ORIGINAL";

	@Test(dataProvider="getData" , groups= {"Purchase"})
	public void submitOrder(String username, String password, String prodName) throws IOException, InterruptedException {

		

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
		String countryName = "India";
		// CheckOutPage checkoutPage=new CheckOutPage(driver);
		checkoutPage.selectCountry(countryName);

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
		ProductCataloguePage productCatalogue = landingPage.loginApplication("dummyemailsrishti@gmail.com", "Dummyemail2@");
		OrdersPage orderPage=productCatalogue.goToOrders();
		
		Boolean match=orderPage.verifyProductInOrdersPage(prodName);
		Assert.assertTrue(match);
	}
	
	@DataProvider
	public Object[][] getData() {
		
		return new Object[][] {{"dummyemailsrishti@gmail.com", "Dummyemail2@", "ADIDAS ORIGINAL"}, {"anshika@gmail.com","Iamking@000","ZARA COAT 3"}};
	}

}

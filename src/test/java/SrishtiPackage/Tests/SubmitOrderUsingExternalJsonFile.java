package SrishtiPackage.Tests;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
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

public class SubmitOrderUsingExternalJsonFile extends BaseTest {

	// by using dataprovider
	String prodName = "ADIDAS ORIGINAL";

	@Test(dataProvider = "getData", groups = { "Purchase" })
	public void submitOrder(HashMap<String, String> value) throws IOException, InterruptedException {

		// drive object creation within page object classes encapsulating from test
		ProductCataloguePage productCatalogue = landingPage.loginApplication(value.get("email"), value.get("password"));
		List<WebElement> products = productCatalogue.getProductList();

		WebElement productElement = productCatalogue.getProductByName(value.get("prodName"));

		productCatalogue.addProducttoCart(value.get("prodName"));
		CartPage cartPage = productCatalogue.goToCartPage();// due to inhertance we can use child object to call parent
															// class method

		Boolean match = cartPage.verifyProductDisplay(value.get("prodName"));
		Assert.assertTrue(match);

		CheckOutPage checkoutPage = cartPage.clickCheckOut();

		String countryName = "India";

		checkoutPage.selectCountry(countryName);

		ConfirmationPage confirmPage = checkoutPage.clickPlaceOrderButton();

		String confirmMsg = confirmPage.getConfirmationMessage();
		Assert.assertTrue(confirmMsg.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

	}

	// @Test(dependsOnMethods= {"submitOrder"})
	@Test
	public void checkOrderInOrders() {
		ProductCataloguePage productCatalogue = landingPage.loginApplication("dummyemailsrishti@gmail.com",
				"Dummyemail2@");
		OrdersPage orderPage = productCatalogue.goToOrders();

		Boolean match = orderPage.verifyProductInOrdersPage(prodName);
		Assert.assertTrue(match);
	}

	@DataProvider
	public Object[][] getData() throws IOException {
		List<HashMap<String,String>> data=getDataJsonToMap(System.getProperty("user.dir")+"\\src\\test\\java\\SrishtiPackage\\data\\PurchaseOrder.json");

		return new Object[][] { { data.get(0) }, { data.get(1) } };

	}
	
	
}

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

import SrishtiPackage.TestComponents.BaseTest;
import SrishtiPackage.data.DataProviderUtility;
import SrishtiPackage.data.DataReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import sripackage.pageobjects.CartPage;
import sripackage.pageobjects.CheckOutPage;
import sripackage.pageobjects.ConfirmationPage;
import sripackage.pageobjects.LandingPage;
import sripackage.pageobjects.OrdersPage;
import sripackage.pageobjects.ProductCataloguePage;

public class SubmitOrderUsingExternalJsonFile extends BaseTest {

	// ✅ private static final = it is a CONSTANT, not a variable
    // clearly communicates — this value is FIXED and belongs to THIS test only
    private static final String PROD_NAME = "ADIDAS ORIGINAL";

	//dataProvider = "getData"->What method to call? ->method name= "getData"
	//dataProviderClass = DataProviderUtility.class->Where to find that method?-> in DataProviderUtility.class
	//when dataProvider is in external class, then must use dataProviderClass
	@Test(dataProvider = "getDataFromExternalFile",dataProviderClass = DataProviderUtility.class, groups = { "Purchase" })
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

		//String countryName = "India";

		checkoutPage.selectCountry(value.get("country"));

		ConfirmationPage confirmPage = checkoutPage.clickPlaceOrderButton();

		String confirmMsg = confirmPage.getConfirmationMessage();
		Assert.assertTrue(confirmMsg.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

	}

	// @Test(dependsOnMethods= {"submitOrder"})
	@Test
	public void checkOrderInOrders() {
		ProductCataloguePage productCatalogue = landingPage.loginApplication(prop.getProperty("userEmail"),
				prop.getProperty("userPassword"));
		OrdersPage orderPage = productCatalogue.goToOrders();

		Boolean match = orderPage.verifyProductInOrdersPage(PROD_NAME);
		Assert.assertTrue(match);
	}
	/*
	 * //this dataProvider takes value from json file->stores data in list<Hashmap>
	 * and returns the list of hashmaps
	 * 
	 * @DataProvider public Object[][] getData() throws IOException {
	 * List<HashMap<String,String>>
	 * data=DataReader.getDataJsonToMap(System.getProperty("user.dir")+
	 * "\\src\\test\\java\\SrishtiPackage\\data\\PurchaseOrder.json");
	 * 
	 * return new Object[][] { { data.get(0) }, { data.get(1) } };
	 * 
	 * }
	 */
	
	
}

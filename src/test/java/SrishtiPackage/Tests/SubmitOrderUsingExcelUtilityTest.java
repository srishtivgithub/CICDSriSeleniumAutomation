package SrishtiPackage.Tests;

import java.io.IOException;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;
import srishtiPakage.TestComponents.BaseTest;
import SrishtiPackage.data.DataProviderUtility;
import sripackage.pageobjects.CartPage;
import sripackage.pageobjects.CheckOutPage;
import sripackage.pageobjects.ConfirmationPage;
import sripackage.pageobjects.ProductCataloguePage;

public class SubmitOrderUsingExcelUtilityTest extends BaseTest{

	// ✅ @Test is completely unaware of WHERE data comes from
	// Same HashMap structure whether data is from JSON, DB or Excel
	@Test(dataProvider = "getExcelData",
	      dataProviderClass = DataProviderUtility.class,
	      groups = {"Purchase"})
	public void submitOrderFromExcel(HashMap<String, String> value)
	        throws IOException, InterruptedException {

	    ProductCataloguePage productCatalogue = landingPage.loginApplication(
	        value.get("email"), value.get("password")
	    );
	    productCatalogue.addProducttoCart(value.get("prodName"));
	    CartPage cartPage = productCatalogue.goToCartPage();
	    Assert.assertTrue(cartPage.verifyProductDisplay(value.get("prodName")));

	    CheckOutPage checkoutPage = cartPage.clickCheckOut();
	    checkoutPage.selectCountry(value.get("country"));

	    ConfirmationPage confirmPage = checkoutPage.clickPlaceOrderButton();
	    Assert.assertTrue(
	        confirmPage.getConfirmationMessage()
	                   .equalsIgnoreCase("THANKYOU FOR THE ORDER.")
	    );
	}
}

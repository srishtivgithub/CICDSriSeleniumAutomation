package SrishtiPackage.StepDefinitons;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import sripackage.pageobjects.CartPage;
import sripackage.pageobjects.CheckOutPage;
import sripackage.pageobjects.ConfirmationPage;
import sripackage.pageobjects.LandingPage;
import sripackage.pageobjects.ProductCataloguePage;
import srishtiPakage.TestComponents.BaseTest;

public class StepDefinitionImplementation extends BaseTest {

	LandingPage landingPage;
	ProductCataloguePage productCataloguePage;
	ConfirmationPage confirmationPage;

	@Given("I landed on Ecommerce page")
	public void I_landed_on_Ecommerce_page() throws IOException {
		landingPage = launchApplication();
	}

	// mention(.+) meaning regular expn ie takes arugument of any type
	// ^ mention at start $ mention at last
	// ^ $ means, @Given has some regular expression
	@Given("^Logged in with username (.+) and password (.+)$")
	public void logged_in_username_and_password(String username, String password) {
		productCataloguePage = landingPage.loginApplication(username, password);
	}

	@When("^I add product (.+) to cart$")
	public void add_product_to_cart(String prodName) {

		List<WebElement> products = productCataloguePage.getProductList();
		productCataloguePage.addProducttoCart(prodName);
	}

	@When("^checkout (.+) and submit the order$")
	public void checkout_and_submit_order(String prodName) {

		CartPage cartPage = productCataloguePage.goToCartPage();// due to inhertance we can use child object to call parent

		Boolean match = cartPage.verifyProductDisplay(prodName);
		Assert.assertTrue(match);

		CheckOutPage checkoutPage = cartPage.clickCheckOut();
		checkoutPage.selectCountry("India");
	    confirmationPage = checkoutPage.clickPlaceOrderButton();
	}
	
	@Then("{string} message will be displayed on ConfirmationPage")
	public void message_displayed_on_ConfirmationPage(String msg) throws InterruptedException {
		String confirmMsg = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMsg.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
	}
}

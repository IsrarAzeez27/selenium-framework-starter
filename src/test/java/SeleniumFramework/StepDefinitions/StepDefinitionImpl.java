package SeleniumFramework.StepDefinitions;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import SeleniumFramework.TestComponents.BaseTest;
import SeleniumFramework.pageobjects.CartPage;
import SeleniumFramework.pageobjects.CheckOutPage;
import SeleniumFramework.pageobjects.LandingPage;
import SeleniumFramework.pageobjects.ProductCatalogue;
import SeleniumFramework.pageobjects.confirmationPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitionImpl extends BaseTest{
	
	public LandingPage landingPage;
	public ProductCatalogue productCatalogue;
	public confirmationPage ConfirmationPage;
	
	@Given("I landed on Ecommerce page")
	public void I_landed_on_Ecommerce_page() throws IOException {
		
		landingPage = launchApplication();
	}
	
	@Given("^Logged in with username (.+) and password (.+)$")
	public void logged_in_with_username_and_password(String username, String password) {
		
		productCatalogue = landingPage.loginApplication(username, password);
	}
	
	@When("^I add product (.+) to the Cart$")
	public void I_add_product_to_cart(String productName) throws Exception {
		
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
	}
	
	@When("^Checkout (.+) and Submit the order$")
	public void check_and_submit_the_order(String productName) {
		
		CartPage cartPage = productCatalogue.goToCartPage();
		Boolean match = cartPage.VerifyProductDisplay(productName);
		Assert.assertTrue(match);
		CheckOutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("india");
		ConfirmationPage = checkoutPage.submitOrder();
	}
	
	@Then("{string} message is displayed on ConfirmationPage")
	public void message_displayed_confirmationPage(String string) {
		String ConfirmMessage = ConfirmationPage.getConfirmationMessage();
		Assert.assertTrue(ConfirmMessage.equalsIgnoreCase(string));
		driver.close();
	}
	
	@Then("{string} message is displayed")
	public void message_dislayed(String string) {
		Assert.assertEquals(string, landingPage.getErrorMessage());
		driver.close();
	}

}

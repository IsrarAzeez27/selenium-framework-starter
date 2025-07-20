package SeleniumFramework.Tests;

import org.testng.annotations.Test;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import SeleniumFramework.TestComponents.Retry;


import SeleniumFramework.TestComponents.BaseTest;
import SeleniumFramework.pageobjects.CartPage;
import SeleniumFramework.pageobjects.CheckOutPage;
import SeleniumFramework.pageobjects.ProductCatalogue;
import SeleniumFramework.pageobjects.confirmationPage;



public class ErrorValidationsTest extends BaseTest {

	@Test(groups = {"ErrorHandling"}, retryAnalyzer=Retry.class)
	public void loginErrorValidation() throws IOException {
		
		String ProductName = "ZARA COAT 3";
		landingPage.loginApplication("LuciferMorningStar@gmail.com", "MorningStar666");
		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());
	}
	
	@Test
	public void productErrorValidation() throws IOException {
		
		String ProductName = "ZARA COAT 3";
		ProductCatalogue productCatalogue = landingPage.loginApplication("LuciferMorningStar@gmail.com", "MorningStar@666");
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(ProductName);
		CartPage cartPage = productCatalogue.goToCartPage();
		Boolean match = cartPage.VerifyProductDisplay("ZARA COAT 33");
		Assert.assertFalse(match);
		
	}

}

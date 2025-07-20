package SeleniumFramework.Tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import SeleniumFramework.TestComponents.BaseTest;
import SeleniumFramework.pageobjects.CartPage;
import SeleniumFramework.pageobjects.CheckOutPage;
import SeleniumFramework.pageobjects.LandingPage;
import SeleniumFramework.pageobjects.OrderPage;
import SeleniumFramework.pageobjects.ProductCatalogue;
import SeleniumFramework.pageobjects.confirmationPage;


public class SubmitOrderTest extends BaseTest {
	
	String ProductName = "ZARA COAT 3";

	@Test(dataProvider = "getData", groups = {"Purchase"})
	public void SubmitOrder(HashMap<String, String> input) throws IOException {
		
		
		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
		
		List<WebElement> products = productCatalogue.getProductList();

		productCatalogue.addProductToCart(input.get("Product"));
		CartPage cartPage = productCatalogue.goToCartPage();
		
		Boolean match = cartPage.VerifyProductDisplay(input.get("Product"));
		
		Assert.assertTrue(match);
		
		CheckOutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("india");
		confirmationPage ConfirmationPage = checkoutPage.submitOrder();
		
		driver.findElement(By.cssSelector(".action__submit")).click();
		
		String ConfirmMessage = ConfirmationPage.getConfirmationMessage();
		
		Assert.assertTrue(ConfirmMessage.equalsIgnoreCase("Thankyou for the order."));
		
	}
	
	@Test(dependsOnMethods = {"SubmitOrder"})
	public void orderHistoryTest() {
		ProductCatalogue productCatalogue = landingPage.loginApplication("LuciferMorningStar@gmail.com", "MorningStar@666");
		OrderPage ordersPage = productCatalogue.goToOrdersPage();		
		Assert.assertTrue(ordersPage.VerifyOrderDisplay(ProductName));
	}
	

	@DataProvider
	public Object[][] getData() throws IOException {
		
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("email", "LuciferMorningStar@gmail.com");
//		map.put("password", "MorningStar@666");
//		map.put("Product", "ZARA COAT 3");
//		
//		HashMap<String, String> map1 = new HashMap<String, String>();
//		map1.put("email", "LuciferMorningStar02@gmail.com");
//		map1.put("password", "MorningStar@666");
//		map1.put("Product", "ADIDAS ORIGINAL");
		
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+"//src//test//java//SeleniumFramework//data//PurchaseOrder.json");
		
		return new Object[][] {{data.get(0)} ,{data.get(1)}};
	}
}



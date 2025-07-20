package SeleniumFramework.Tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import SeleniumFramework.pageobjects.LandingPage;


public class StandAloneTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String ProductName = "ZARA COAT 3";
		
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://rahulshettyacademy.com/client");
		driver.manage().window().maximize();
		
		LandingPage landingPage = new LandingPage(driver);
		
		driver.findElement(By.id("userEmail")).sendKeys("LuciferMorningStar@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("MorningStar@666");
		driver.findElement(By.id("login")).click();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		
		WebElement prod = products.stream().filter(product -> product.findElement(By.cssSelector("b")).
		getText().equals(ProductName)).findFirst().orElse(null);
		
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ngx-spinner-overlay")));
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
		
		List<WebElement> CartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
		
		boolean match = CartProducts.stream().anyMatch(CartProduct -> 
		CartProduct.getText().equalsIgnoreCase(ProductName));
		
		Assert.assertTrue(match);
		
		driver.findElement(By.cssSelector(".totalRow button")).click();
		
		Actions a = new Actions(driver);
		
		a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "india").
		build().perform();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
		
		driver.findElement(By.xpath("//div[@class='payment__shipping']//button[2]")).click();
		
		driver.findElement(By.cssSelector(".action__submit")).click();
		
		String ConfirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
		
		Assert.assertTrue(ConfirmMessage.equalsIgnoreCase("Thankyou for the order."));
		
		driver.close();
	}

}

package SeleniumFramework.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import SeleniumFramework.AbstractComponents.AbstractComponent;

public class LandingPage extends AbstractComponent {
	
	WebDriver driver;
	
	public LandingPage(WebDriver driver) {
		
		super(driver);
		//initialization
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	// WebElement userEmail = driver.findElement(By.id("userEmail"));
	
	//PageFactory standards
	
	@FindBy(id = "userEmail")
	WebElement userEmail;
	
	@FindBy(id = "userPassword")
	WebElement PasswordElement;
	
	@FindBy(id = "login")
	WebElement submit;
	
	@FindBy(css = "div[aria-label='Incorrect email or password.']")
	WebElement errorMessage;
	
	public ProductCatalogue loginApplication(String email, String password) {
		
		userEmail.sendKeys(email);
		PasswordElement.sendKeys(password);
		submit.click();
		ProductCatalogue productCatalogue = new ProductCatalogue(driver);
		return productCatalogue;
	}
	
	public String getErrorMessage() {
		waitForWebElementToAppear(errorMessage);
		return errorMessage.getText();
	}
	
	public void goTo() {
		driver.get("https://rahulshettyacademy.com/client");
	}

}

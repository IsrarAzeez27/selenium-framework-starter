package SeleniumFramework.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import SeleniumFramework.AbstractComponents.AbstractComponent;

public class CheckOutPage extends AbstractComponent {
	
	WebDriver driver;
	
	public CheckOutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css = "[placeholder='Select Country']")
	private WebElement country;
	
	@FindBy(css = ".action__submit")
	private WebElement submit;
	
	@FindBy(xpath = "//div[@class='payment__shipping']//button[2]")
	private WebElement selectCountry;
	
	private By results = By.cssSelector(".ta-results");
	
	public void selectCountry(String countryName) {
		Actions a = new Actions(driver);
		a.sendKeys(country, countryName).build().perform();
		waitForElementToAppear(By.cssSelector(".ta-results"));
		selectCountry.click();
	}
	
	public confirmationPage submitOrder() {
		submit.click();
		return new confirmationPage(driver);
	}
	
	

}

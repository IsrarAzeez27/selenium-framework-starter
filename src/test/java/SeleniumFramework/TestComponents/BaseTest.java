package SeleniumFramework.TestComponents;

import org.testng.annotations.AfterMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import SeleniumFramework.pageobjects.LandingPage;

public class BaseTest {
	
	public WebDriver driver;
	public LandingPage landingPage;
	
	public WebDriver intializeDriver() throws IOException {
		
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//src//main//java//SeleniumFramework//Resources//GlobalData.properties");
		prop.load(fis);
		
		
		String browserName = System.getProperty("browser")!=null ? System.getProperty("browser") : prop.getProperty("browser");
		
		// prop.getProperty("browser");
		
		if(browserName.contains("chrome")) {
			ChromeOptions options = new ChromeOptions();
			if(browserName.contains("headless")) {
			options.addArguments("headless");
			}
		driver = new ChromeDriver(options);
		driver.manage().window().setSize(new Dimension(1440,900));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		// driver.get("https://rahulshettyacademy.com/client");
		driver.manage().window().maximize();
		}
		
		if(browserName.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			// driver.get("https://rahulshettyacademy.com/client");
			driver.manage().window().maximize();
			}
		
		if(browserName.equalsIgnoreCase("fireFox")) {
			driver = new FirefoxDriver();
			}
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		// driver.get("https://rahulshettyacademy.com/client");
		driver.manage().window().maximize();
		return driver;
	}
	
	@BeforeMethod(alwaysRun=true)
	public LandingPage launchApplication() throws IOException {
		driver = intializeDriver();
		
		landingPage = new LandingPage(driver);
		landingPage.goTo();
		return landingPage;
	}
	
	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir")+ "//reports//" + testCaseName + ".png");
		FileUtils.copyFile(source, file);
		return System.getProperty("user.dir")+ "//reports//" + testCaseName + ".png";
	}
	
	
	@AfterMethod(alwaysRun=true)
	public void tearDown() {
		driver.close();
	}
	
		public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
			
			// Reading JSON to String
			String jsonContent = FileUtils.readFileToString(new File(filePath) , StandardCharsets.UTF_8);
			
			// String to Map - Jackson Databind
			ObjectMapper mapper = new ObjectMapper();
			List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String,String>>>(){ });
			return data;
		}

} 

package TestCases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateCampaignWithCampaignStatus {
	public static void main(String[] args) throws InterruptedException, Throwable {
		FileInputStream fis = new FileInputStream("./src/test/resources/Commondata.properties");
		Properties prop = new Properties(); //creating file type object
		prop.load(fis); //loading all the keys
		//storing all value of the keys in a variable
		String BROWSER = prop.getProperty("Browser");
		String URL = prop.getProperty("Url");
		String USERNAME = prop.getProperty("Username");
		String PASSWORD = prop.getProperty("Password");
		
		System.out.println(BROWSER); System.out.println(URL);
		System.out.println(USERNAME); System.out.println(PASSWORD);
		
		//WebDriver initialization to avoid hardcoding later
		WebDriver driver = null;
		
		//RUN TIME POLYMORPHISM -> deciding which browser to open at runtime
		//checking properties file to open suitable browser as mentioned in file
		if(BROWSER.equals("Chrome")) {
		ChromeOptions settings = new ChromeOptions(); 
		Map<String, Object> prefs = new HashMap<>(); 
		prefs.put("profile.password_manager_leak_detection", false); 
		settings.setExperimentalOption("prefs", prefs);
		driver = new ChromeDriver(settings);
		}
		
		else if(BROWSER.equals("Edge")) {
			driver = new EdgeDriver();
		}
		else if(BROWSER.equals("Firefox")) {
			driver = new FirefoxDriver();
		}
		
		//Now using the file data instead of hardcoded data
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(URL);
		driver.findElement(By.id("username")).sendKeys(USERNAME);
		driver.findElement(By.id("inputPassword")).sendKeys(PASSWORD);
		driver.findElement(By.xpath("//button[text()='Sign In']")).click();
		
		
		driver.findElement(By.linkText("Campaigns")).click();
		driver.findElement(By.xpath("//span[text() = 'Create Campaign']")).click();
		driver.findElement(By.name("campaignName")).sendKeys("TryingCamp");
		WebElement target_size = driver.findElement(By.name("targetSize"));
		target_size.clear();
		target_size.sendKeys("13");	
		driver.findElement(By.name("campaignStatus")).sendKeys("Active");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//button[text()='Create Campaign']")).click();
		
		//Validation using the toast message	
		WebElement toast_message = driver.findElement(By.xpath("//div[@role = 'alert']"));
		//Explicit Wait for webelement toast message
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(toast_message));//wait till toast message is visible
		//Getting the text of toast for validation
		String msg = toast_message.getText();
		//checking part of toast message for validation
		if(msg.contains("TryingCamp")) {
			System.out.println("Campaign Created");
		}
		else {
			System.out.println("Campaign Not Created");
		}
		driver.findElement(By.xpath("//button[@aria-label='close']")).click();//to close toast message
		
		//Logout
		WebElement logout_icon = driver.findElement(By.xpath("//div[@class='user-icon']"));
		//mouse hovering action
		Actions act = new Actions(driver);
		act.moveToElement(logout_icon).perform();
		driver.findElement(By.xpath("//div[@class='dropdown-item logout']")).click();
		driver.quit();
	}
}




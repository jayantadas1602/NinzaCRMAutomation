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

public class CreateProductsWithMandatoryFields {

	public static void main(String[] args) throws Throwable {
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
		
		//Products Link and creation
		driver.findElement(By.linkText("Products")).click();//clicking Products link to open products page
		driver.findElement(By.xpath("//span[text()='Add Product']")).click();
		driver.findElement(By.name("productName")).sendKeys("CheckTheProduct");//Product Name
		WebElement qty = driver.findElement(By.name("quantity"));//Quantity
		qty.clear(); qty.sendKeys("13");
		WebElement ppu = driver.findElement(By.name("price"));//Price Per Unit
		ppu.clear(); ppu.sendKeys("7.9");
		driver.findElement(By.name("productCategory")).click();//Category
		driver.findElement(By.xpath("//option[@value='Furniture']")).click();
		driver.findElement(By.name("vendorId")).click();//Vendor
		driver.findElement(By.xpath("//option[@value='VID_006']")).click();
		driver.findElement(By.xpath(" //button[text()='Add']")).click();//Add Button
	}

}

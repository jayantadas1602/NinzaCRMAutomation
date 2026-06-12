package BaseTest;
import org.testng.annotations.Test;

import genericUtility.PropertiesFileUtility;
import genericUtility.WebDriverUtility;
import objectRepository.HomePage;
import objectRepository.LoginPage;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.AfterSuite;

public class BaseClass {
	public WebDriver driver = null;
	public PropertiesFileUtility putil = new PropertiesFileUtility();
	public WebDriverUtility wutil = new WebDriverUtility();
	public static WebDriver sdriver = null;//listener purposes
	//cant use driver as not static and requires obj creation and consumes more heap memory

	@BeforeSuite(groups = {"smoke","regression"})//tells to take part in all types of execution
	  public void beforeSuite() {
		Reporter.log("DB open",true);
	  }
	
	//@Parameters("BROWSER")
	@BeforeClass(groups = {"smoke","regression"})
	  public void beforeClass() throws Throwable {
			String BROWSER = putil.toReadDataFromPropertiesFile("Browser");
			//String BROWSER = System.getProperty("Browser");//reading data from command line
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
			sdriver = driver;//for Listener Implementation
			Reporter.log("launched browser", true);
	  }
  
	
  @BeforeMethod(groups = {"smoke","regression"})
  public void beforeMethod() throws Throwable {
	  String URL = putil.toReadDataFromPropertiesFile("Url");
	  String USERNAME = putil.toReadDataFromPropertiesFile("Username");
	  String PASSWORD = putil.toReadDataFromPropertiesFile("Password");
	  driver.manage().window().maximize();
	  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	  driver.get(URL);
	  
	  //login
	  LoginPage lp = new LoginPage(driver);
	  lp.getUsernameTF().sendKeys(USERNAME);
	  lp.getPwdTF().sendKeys(PASSWORD);
	  lp.getSignInBtn().click(); 
	  Reporter.log("login",true);
  }

  @AfterMethod(groups = {"smoke","regression"})
  public void afterMethod() {
	  HomePage hp = new HomePage(driver);
	  WebElement icon = hp.getUserIcon();
	  wutil.mouseHoverOnWebElement(driver, icon);
	  hp.getLogoutBtn().click();
	  Reporter.log("logout",true);
  }

  @AfterClass(groups = {"smoke","regression"})
  public void afterClass() {
	  driver.quit();
	  Reporter.log("closed browser",true);
  }

  @AfterSuite(groups = {"smoke","regression"})
  public void afterSuite() {
	  Reporter.log("DB close",true);
  }
}

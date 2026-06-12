package Campaign;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import BaseTest.BaseClass;
import genericUtility.ExcelUtility;
import genericUtility.JavaUtility;
import genericUtility.PropertiesFileUtility;
import genericUtility.WebDriverUtility;
import objectRepository.CampaignPage;
import objectRepository.HomePage;
import objectRepository.LoginPage;

@Listeners(ListenerUtility.ListenerImplementation.class)//implementation of Listener class
public class CampaignTest extends BaseClass
{
	//class name and method name should end with test
	
	@Test(groups = "smoke")
	public void toCreateCampaignEithExpectedDateTest() throws Throwable
	{
		ExcelUtility eutil = new ExcelUtility();
		JavaUtility jutil = new JavaUtility();
		
		//future date
		String datereq = jutil.togetReqDate(30);
		
		String campaign_name = eutil.toReadDataFromExcel("Campaign", 1, 2);
		String target = eutil.toReadDataFromExcel("Campaign", 1, 3);
		
		//create campaign
		HomePage hp = new HomePage(driver);
		CampaignPage cp = new CampaignPage(driver);
		hp.getCreateCampBtn().click();
		
		cp.getCampaignNameTF().sendKeys(campaign_name);
		WebElement target_size = cp.getTargetTF();
		target_size.clear();
		target_size.sendKeys(target);	
		cp.getDateTF().sendKeys(datereq);
		Thread.sleep(3000);
		cp.getCreateCampSubmitBtn().click();
		
		WebElement toast_message = cp.getToastmsg();
		wutil.waitForVisibilityOfElement(toast_message, driver);
		
		//Getting the text of toast for validation
		String msg = toast_message.getText();
		Assert.assertTrue(msg.contains(campaign_name));//validation using hard assert
		
		//checking part of toast message for validation
		/*if(msg.contains(campaign_name)) {
			System.out.println("Campaign Created");
		}
		else {
			System.out.println("Campaign Not Created");
		}*/
		cp.getClosemsg().click();//to close toast message
		//logout already in BaseClass that is extended
		
	}
	
	@Test(groups = "regression")
	public void toCreateCampaignWithMandatoryFieldsTest() throws Throwable
	{
		ExcelUtility eutil = new ExcelUtility();
		JavaUtility jutil = new JavaUtility();
		String campname = eutil.toReadDataFromExcel("Campaign", 1, 2);
		String target = eutil.toReadDataFromExcel("Campaign", 1, 3);
		// create campaign
		HomePage hp = new HomePage(driver);
		hp.getCreateCampBtn().click();
		CampaignPage cp = new CampaignPage(driver);
		cp.getCampaignNameTF().sendKeys(campname);
		cp.getTargetTF().sendKeys(target);
		cp.getCreateCampSubmitBtn().click();
		// validation
		WebElement toast_message = cp.getToastmsg();
		wutil.waitForVisibilityOfElement(toast_message, driver);
		String msg = toast_message.getText();
		
		//Assert.assertTrue(msg.contains(campname));//validation using hard assert
		//checking part of toast message for validation
		if(msg.contains(campname)) {
			System.out.println("Campaign Created");
		}
		else {
			System.out.println("Campaign Not Created");
		}
		cp.getClosemsg().click();//to close toast message
	}

	@Test(groups = "smoke")
	public void toCreateCampaignWithCampaignStatusTest() throws Throwable
	{
		ExcelUtility eutil = new ExcelUtility();
		JavaUtility jutil = new JavaUtility();
		String campname = eutil.toReadDataFromExcel("Campaign", 1, 2);
		String target = eutil.toReadDataFromExcel("Campaign", 1, 3);
		// create campaign
		HomePage hp = new HomePage(driver);
		hp.getCreateCampBtn().click();
		CampaignPage cp = new CampaignPage(driver);
		cp.getCampaignNameTF().sendKeys(campname);
		cp.getStatusTF().sendKeys("pass");
		cp.getTargetTF().sendKeys(target);
		cp.getCreateCampSubmitBtn().click();
		
		// validation
		WebElement toastmsg = cp.getToastmsg();
		wutil.waitForVisibilityOfElement(toastmsg, driver);
		String msg = toastmsg.getText();
		
		Assert.assertTrue(msg.contains(campname));
		
		/*if (msg.contains(campname)) {
		System.out.println("campaign created");
		}
		else {
		System.out.println("campaign not created");
		}*/
		cp.getClosemsg().click();
	}

}

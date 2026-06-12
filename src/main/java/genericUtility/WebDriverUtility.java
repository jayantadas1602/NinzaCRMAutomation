package genericUtility;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverUtility {
	//1. implicit wait 
		public void waitForPageToLoad(WebDriver driver)
		{
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		}
	//2. Explicit Wait
		public void waitForVisibilityOfElement(WebElement element,WebDriver driver)//explicit wait
		{
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(element));
		}
	//3. switching to iframe using index
		public void switchToFrame(WebDriver driver,int index)
		{
			driver.switchTo().frame(index);
		}
	//4. switching to iframe using name or id	
		public void switchToFrame(WebDriver driver,String idorname)
		{
			driver.switchTo().frame(idorname);
		}
	//5. switching to iframe using webElement
		public void switchToFrame(WebDriver driver,WebElement element)
		{
			driver.switchTo().frame(element);
		}
	//6. Switching to alert popup and accepting it	
		public void switchToAlertAndAccept(WebDriver driver)
		{
			driver.switchTo().alert().accept();
		}
	//7. Switching to alert popup and dismissing it		
		public void switchToAlertAndDismiss(WebDriver driver)
		{
			driver.switchTo().alert().dismiss();
		}
	//8. Switching to alert popup and getting it's text		
		public String switchToAlertAndText(WebDriver driver)
		{
			String text1 = driver.switchTo().alert().getText();
			return text1;
		}
	//9. Switching to alert popup and sending text to its textfield	 	
		public void switchToAlertAndSendKeys(WebDriver driver, String text)
		{
			driver.switchTo().alert().sendKeys(text);
		}
	//10. Selecting an option in dropdown using index	
		public void select(WebElement element, int index)
		{
			Select sel = new Select(element);
			sel.selectByIndex(index);
		}
	//11. Selecting an option in dropdown using value	
		public void select(WebElement element, String value)
		{
			Select sel = new Select(element);
			sel.selectByValue(value);
		}
	//12. Selecting an option in dropdown using visible text
		public void select(String text, WebElement element)
		{
			Select sel = new Select(element);
			sel.selectByVisibleText(text);
		}
	//13. mouse hovering action	
		public void mouseHoverOnWebElement(WebDriver driver,WebElement element)
		{
			Actions act = new Actions(driver);
			act.moveToElement(element).perform();
		}
	//14. Double click operation	
		public void doubleClick(WebDriver driver,WebElement element)
		{
			Actions act = new Actions(driver);
			act.doubleClick(element).perform();
		}	
}





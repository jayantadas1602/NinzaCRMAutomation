package objectRepository;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	
	WebDriver driver;
	public LoginPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindAll({@FindBy(id = "username"),@FindBy(name = "username")})//auto healing
	private WebElement usernameTF;
	
	@FindBy(id = "inputPassword")
	private WebElement pwdTF;
	
	@FindBy(xpath = "//button[text() = 'Sign In']")
	private WebElement SignInBtn;

	//generate getters method since above webElements are private
	public WebElement getUsernameTF() {
		return usernameTF;
	}

	public WebElement getPwdTF() {
		return pwdTF;
	}

	public WebElement getSignInBtn() {
		return SignInBtn;
	}
	
	//to achieve code optimization in main scripts
	public void login(String url,String username,String pwd)
	{
		driver.manage().window().maximize();
		driver.get(url);
		usernameTF.sendKeys(username);
		pwdTF.sendKeys(pwd);
	}
	
}





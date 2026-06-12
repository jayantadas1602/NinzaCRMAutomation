package objectRepository;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
	WebDriver driver;
	public HomePage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(linkText = "Campaigns")
	private WebElement campaign;
	
	@FindBy(linkText = "Products")
	private WebElement product;
	
	@FindBy(xpath = "//span[text() = 'Create Campaign']")
	private WebElement createCampBtn;
	
	@FindBy(xpath = "//div[@class = 'user-icon']")
	private WebElement userIcon;
	
	@FindBy(xpath = "//div[@class = 'dropdown-item logout']")
	private WebElement logoutBtn;

	public WebElement getCampaign() {
		return campaign;
	}

	public WebElement getProduct() {
		return product;
	}

	public WebElement getCreateCampBtn() {
		return createCampBtn;
	}

	public WebElement getUserIcon() {
		return userIcon;
	}

	public WebElement getLogoutBtn() {
		return logoutBtn;
	}	
}






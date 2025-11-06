package pranav_automation_project.SeleniumFrameworkDesign.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pranav_automation_project.SeleniumFrameworkDesign.AbstractComponent.AbstractComponent;

public class LandingPage extends AbstractComponent{

	WebDriver driver;
	
	public LandingPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="userEmail")
	WebElement userEmail;
	
	@FindBy(id="userPassword")
	WebElement passwordEle;
	
	@FindBy(id="login")
	WebElement submit;
	
	@FindBy(xpath="/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]")
	WebElement errorMessage;
	
	@FindBy(css="[aria-label='Incorrect email or password.']")
	WebElement loginErrorMessage;
	
	By toastMessage = By.cssSelector("#toast-container");
	
	public void goTo() {
		driver.get("https://rahulshettyacademy.com/client");
	}
	
	public String getErrorMessage() {
		waitForWebElementToAppear(errorMessage);
		return errorMessage.getText();
	}
	
	public ProductCatalogue loginApplication(String email, String password) throws InterruptedException {
		userEmail.sendKeys(email);
		passwordEle.sendKeys(password);
		submit.click();
		return new ProductCatalogue(driver);
	}
	
	public String getLoginErrorMessage() {
		return loginErrorMessage.getText();
	}
}

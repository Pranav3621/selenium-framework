package pranav_automation_project.SeleniumFrameworkDesign.pageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pranav_automation_project.SeleniumFrameworkDesign.AbstractComponent.AbstractComponent;

public class CheckoutPage extends AbstractComponent{
	
	WebDriver driver;
	
	public CheckoutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="input[placeholder='Select Country']")
	WebElement countryDropdown;
	
	@FindBy(css=".list-group-item span")
	List<WebElement> countryOptions;
	
	@FindBy(className="action__submit")
	WebElement submitButton;
	
	By submitButtonLocator = By.className("action__submit");
	
	public void selectCountry(String countryName) throws InterruptedException {
		countryDropdown.sendKeys("Ind");
		waitForWebElementsToAppear(countryOptions);
		//Thread.sleep(5000);
		for(WebElement countryOption: countryOptions) {
			System.out.println(countryOption.getText());
			if(countryOption.getText().trim().equalsIgnoreCase("India")) {
				
				countryOption.click();
				break;
			}
		}
	}
	
	public ConfirmationPage submitOrder() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Wait until ta-backdrop disappears
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ta-backdrop")));
		submitButton.click();
		return new ConfirmationPage(driver);
	}
}

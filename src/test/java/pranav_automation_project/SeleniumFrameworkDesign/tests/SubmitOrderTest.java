package pranav_automation_project.SeleniumFrameworkDesign.tests;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import pranav_automation_project.SeleniumFrameworkDesign.TestComponents.BaseTest;
import pranav_automation_project.SeleniumFrameworkDesign.pageObjects.CartPage;
import pranav_automation_project.SeleniumFrameworkDesign.pageObjects.CheckoutPage;
import pranav_automation_project.SeleniumFrameworkDesign.pageObjects.ConfirmationPage;
import pranav_automation_project.SeleniumFrameworkDesign.pageObjects.ProductCatalogue;

public class SubmitOrderTest extends BaseTest{
	
	@Test
	public void submitOrderTest() throws InterruptedException, IOException {
		String productName = "ZARA COAT 3";
		String countryName = "India";
		
		ProductCatalogue productCatalogue = landingPage.loginApplication("pranav@example.com","WhiteDiamond@123");
		List<WebElement> products = productCatalogue.getProductsList();
		productCatalogue.addProductToCart(productName);
		CartPage cartPage = productCatalogue.goToCartPage();
		boolean match = cartPage.verifyProductDisplay(productName);
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckoutPage();
		checkoutPage.selectCountry(countryName);
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		String confirmMessage = confirmationPage.getConfirmationMessageText();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
	}
	
}

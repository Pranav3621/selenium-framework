package pranav_automation_project.SeleniumFrameworkDesign.tests;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pranav_automation_project.SeleniumFrameworkDesign.TestComponents.BaseTest;
import pranav_automation_project.SeleniumFrameworkDesign.pageObjects.CartPage;
import pranav_automation_project.SeleniumFrameworkDesign.pageObjects.CheckoutPage;
import pranav_automation_project.SeleniumFrameworkDesign.pageObjects.ConfirmationPage;
import pranav_automation_project.SeleniumFrameworkDesign.pageObjects.OrdersPage;
import pranav_automation_project.SeleniumFrameworkDesign.pageObjects.ProductCatalogue;

public class SubmitOrderTest extends BaseTest{
	
	String productName = "ZARA COAT 3";
	
	@Test(dataProvider="getData",groups="Purchase")
	public void submitOrderTest(HashMap<String,String> input) throws InterruptedException, IOException {
		
		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"),input.get("password"));
		List<WebElement> products = productCatalogue.getProductsList();
		productCatalogue.addProductToCart(input.get("product"));
		CartPage cartPage = productCatalogue.goToCartPage();
		boolean match = cartPage.verifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckoutPage();
		checkoutPage.selectCountry("India");
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		String confirmMessage = confirmationPage.getConfirmationMessageText();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
	}
	
	@Test(dependsOnMethods= {"submitOrderTest"})
	public void verifyOrderTest() throws InterruptedException {
		ProductCatalogue productCatalogue = landingPage.loginApplication("pranav@example.com","WhiteDiamond@123");
		OrdersPage ordersPage = productCatalogue.goToOrdersPage();
		boolean isOrderPlaced = ordersPage.verifyOrderDisplay(productName);
		Assert.assertTrue(isOrderPlaced);
	}
	
	@DataProvider
	public Object[][] getData() throws IOException{
//		HashMap<String,String> map = new HashMap<String,String>();
//		map.put("email", "pranav@example.com");
//		map.put("password", "WhiteDiamond@123");
//		map.put("product", "ZARA COAT 3");
//		
//		HashMap<String,String> map1 = new HashMap<String,String>();
//		map1.put("email", "pranavsethi2@example.com");
//		map1.put("password", "WhiteDiamond@123");
//		map1.put("product", "ADIDAS ORIGINAL");
		
		List<HashMap<String,String>> data = getJsonDataToMap(System.getProperty("user.dir") + "//src//test//java//pranav_automation_project//SeleniumFrameworkDesign//data//PurchaseOrder.json");
		
		return new Object[][] {{data.get(0)},{data.get(1)}};
	}
}

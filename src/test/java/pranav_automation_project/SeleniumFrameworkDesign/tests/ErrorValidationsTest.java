package pranav_automation_project.SeleniumFrameworkDesign.tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import pranav_automation_project.SeleniumFrameworkDesign.TestComponents.BaseTest;
import pranav_automation_project.SeleniumFrameworkDesign.pageObjects.ProductCatalogue;

public class ErrorValidationsTest extends BaseTest{
	
	@Test(groups= {"ErrorHandling"})
	public void loginErrorValidation() throws InterruptedException {
		landingPage.loginApplication("pranavsethi2@example.com","WhiteDiamond@12");
		Assert.assertEquals("Incorrect email password.", landingPage.getErrorMessage());
	}
	
	@Test
	public void productErrorValidation() throws InterruptedException {
		String productName = "ZARA COAT 4";
		ProductCatalogue productCatalogue = landingPage.loginApplication("pranavsethi2@example.com", "WhiteDiamond@123");
		boolean match = productCatalogue.verifyProductsExistence(productName);
		Assert.assertFalse(match);
	}
	
}

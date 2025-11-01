package pranav_automation_project.SeleniumFrameworkDesign.tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class StandAloneTest {

    @Test
    public void submitOrderTest() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        String productName = "ZARA COAT 3";

        // Step 1: Login
        driver.get("https://rahulshettyacademy.com/client");
        driver.findElement(By.id("userEmail")).sendKeys("pranav@example.com");
        driver.findElement(By.id("userPassword")).sendKeys("WhiteDiamond@123");
        driver.findElement(By.id("login")).click();

        // Step 2: Wait for product list
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));

        // Step 3: Add desired product to cart
        List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
        WebElement prod = products.stream()
                .filter(product -> product.findElement(By.tagName("b")).getText().equals(productName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found: " + productName));

        prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();

        // Step 4: Wait for toast to appear and disappear
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));

        // Step 5: Go to cart
        safeClick(driver, By.cssSelector("button[routerlink*='cart']"));

        // Step 6: Verify product in cart
        List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
        boolean match = cartProducts.stream().anyMatch(p -> p.getText().equalsIgnoreCase(productName));
        Assert.assertTrue(match, "Product not found in cart!");

        // Step 7: Checkout
        safeClick(driver, By.cssSelector(".totalRow button"));

        // Step 8: Select country
        driver.findElement(By.cssSelector("[placeholder='Select Country']")).sendKeys("Ind");

        // Wait for results
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));

        // Remove lingering overlay (if any)
        removeBackdrop(driver);

        // Click “India” safely
        try {
            WebElement india = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class,'ta-item')][normalize-space()='India']")));
            india.click();
        } catch (ElementClickInterceptedException e) {
            WebElement india = driver.findElement(By.xpath("//button[contains(@class,'ta-item')][normalize-space()='India']"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", india);
        }

        // Step 9: Place Order
        removeBackdrop(driver);
        safeClick(driver, By.cssSelector(".action__submit"));

        // Step 10: Confirm order success
        String confirmText = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector(".hero-primary"))).getText();
        Assert.assertTrue(confirmText.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

        System.out.println("✅ Test Passed — Order placed successfully!");
        driver.quit();
    }

    // --- Utility Methods ---

    public void safeClick(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        int attempts = 0;
        while (attempts < 3) {
            try {
                // Remove overlays before each attempt
                removeBackdrop(driver);
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                element.click();
                return;
            } catch (ElementClickInterceptedException e) {
                System.out.println("Click intercepted, retrying... (" + ++attempts + ")");
                try { Thread.sleep(500); } catch (InterruptedException ignored) {}
            }
        }
        throw new RuntimeException("Failed to click element: " + locator);
    }

    public void removeBackdrop(WebDriver driver) {
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "document.querySelectorAll('.ta-backdrop,.ng-animating,.toast-message')" +
                    ".forEach(e => e.remove());");
        } catch (Exception ignored) {}
    }
}

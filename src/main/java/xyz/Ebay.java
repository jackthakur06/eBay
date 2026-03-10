package xyz;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;

/**
 * eBay UI Automation Test [cite: 1, 2]
 * Verifies search functionality and 'Add to Cart' flow.
 */
public class Ebay {
    public static void main(String[] args) {
        // 1. Stealth Setup to avoid bot detection [cite: 13, 17, 18]
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            // 2. Load Homepage [cite: 23, 24]
            driver.get("https://www.ebay.com");
            driver.manage().window().maximize();

            // 3. Perform Search for 'book' [cite: 26, 29, 31]
            WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.id("gh-ac")));
            searchBox.clear();
            searchBox.sendKeys("book");
            String oldUrl = driver.getCurrentUrl();
            searchBox.sendKeys(Keys.ENTER);
            wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(oldUrl)));
            System.out.println("Search submitted successfully.");

            // 4. Find and Click Product Link [cite: 34, 38, 48]
            WebElement firstItem = null;
            for (int i = 0; i < 8; i++) {
                try {
                    firstItem = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@href, '/itm/')]")));
                    if (firstItem.isDisplayed()) break;
                } catch (Exception e) {
                    js.executeScript("window.scrollBy(0, 800);");
                    Thread.sleep(1500);
                }
            }
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", firstItem);
            Thread.sleep(1000);
            js.executeScript("arguments[0].click();", firstItem);

            // 6. Handle New Tab [cite: 49, 52]
            wait.until(ExpectedConditions.numberOfWindowsToBe(2));
            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));
            System.out.println("Switched to Tab: " + driver.getTitle());

            // 7. Add to Cart [cite: 54, 60, 62]
            WebElement addToCartBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//span[contains(text(),'Add to cart')]/ancestor::a | //a[contains(@data-testid, 'atc-button')] | //a[@id='atcBtn_btn']")));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", addToCartBtn);
            Thread.sleep(1000);
            try {
                addToCartBtn.click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", addToCartBtn);
            }
            System.out.println("Add to cart clicked.");

            // 8. Navigate to Cart [cite: 65, 68, 69]
            WebElement goToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[contains(text(),'Go to cart')]/ancestor::a | //a[contains(@data-test-id, 'view-cart-link')] | //span[contains(text(),'See in cart')]/ancestor::a")));
            js.executeScript("arguments[0].click();", goToCartBtn);

            // 9. FINAL VERIFICATION [cite: 70, 72, 75, 76]
            wait.until(ExpectedConditions.urlContains("cart.ebay.com"));
            WebElement cartBucket = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".cart-bucket, .app-cart")));
            if (cartBucket.isDisplayed()) {
                System.out.println("SUCCESS: Verified! You are now on the cart page with your item.");
            }
        } catch (Exception e) {
            System.err.println("Execution failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // driver.quit(); [cite: 82]
        }
    }
}

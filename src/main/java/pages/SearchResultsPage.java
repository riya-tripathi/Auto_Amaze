package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class SearchResultsPage {

    WebDriver driver;
    WebDriverWait wait;

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    By results = By.xpath("//div[contains(@class,'listing') or contains(@class,'result')]");

    public boolean isResultsDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(results));
            System.out.println("✅ Results displayed");
            return true;
        } catch (Exception e) {
            System.out.println("❌ Results not displayed");
            return false;
        }
    }
}
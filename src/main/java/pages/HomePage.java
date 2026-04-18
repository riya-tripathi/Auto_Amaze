package pages;

import org.openqa.selenium.*;
import utils.WaitUtils;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {

    WebDriver driver;
    WaitUtils wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
    }

    // Locators
    By fromCity = By.xpath("//input[@placeholder='Where from?']");
    By toCity = By.xpath("//input[@placeholder='Where to?']");
    By datePicker = By.xpath("//input[contains(@placeholder,'Depart')]");
    By searchBtn = By.xpath("//button[contains(text(),'Search')]");

    // ✅ Popup Handler (FIXED)
    public void handlePopup() {

        try {
            WebDriverWait localWait = new WebDriverWait(driver, Duration.ofSeconds(5));

            By closeBtn = By.xpath(
                    "//button[@aria-label='Close'] | " +
                            "//button//*[name()='svg']/ancestor::button"
            );

            WebElement close = localWait.until(
                    ExpectedConditions.presenceOfElementLocated(closeBtn));

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});", close);

            localWait.until(ExpectedConditions.elementToBeClickable(close));

            try {
                close.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", close);
            }

            System.out.println("✅ Popup closed");

        } catch (Exception e) {
            System.out.println("⚠️ Popup not found");
        }

        wait.waitForPageStability();
    }

    // ✅ FROM CITY
    public void selectFromCity(String city) {

        WebElement from = wait.waitForClickable(fromCity);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", from);

        try {
            from.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", from);
        }

        from.clear();

        for (char c : city.toCharArray()) {
            from.sendKeys(String.valueOf(c));
            wait.waitForMilliseconds(150);
        }

        By suggestion = By.xpath("//p[contains(text(),'" + city + "')]");

        for (int i = 0; i < 3; i++) {
            try {
                WebElement option = wait.waitForClickable(suggestion);

                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block:'center'});", option);

                option.click();
                break;

            } catch (Exception e) {
                System.out.println("Retrying From city...");
            }
        }
    }

    // ✅ TO CITY
    public void selectToCity(String city) {

        WebElement to = wait.waitForClickable(toCity);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", to);

        try {
            to.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", to);
        }

        to.clear();

        for (char c : city.toCharArray()) {
            to.sendKeys(String.valueOf(c));
            wait.waitForMilliseconds(150);
        }

        By suggestion = By.xpath("//p[contains(text(),'" + city + "')]");

        for (int i = 0; i < 3; i++) {
            try {
                WebElement option = wait.waitForClickable(suggestion);

                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block:'center'});", option);

                option.click();
                System.out.println("To city selected");
                break;

            } catch (Exception e) {
                System.out.println("Retrying To city...");
            }
        }
    }

    // ✅ DATE (FINAL STABLE VERSION)
    public void selectDate() {

        // 🔥 wait for page to stabilize after city selection
        wait.waitForPageStability();

        // 🔥 NEW stable locator (Cleartrip updated UI)
        By dateInputLocator = By.xpath("//div[contains(@class,'DatePicker')]//input");

        WebElement dateInput = wait.waitForClickable(dateInputLocator);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", dateInput);

        try {
            dateInput.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dateInput);
        }

        // wait for calendar
        By calendar = By.xpath("//div[@role='grid']");
        wait.waitForVisible(calendar);

        // get all enabled dates
        By allDates = By.xpath("//div[@role='gridcell' and @aria-disabled='false']");
        List<WebElement> dates = wait.waitForAllElements(allDates);

        int randomIndex = new java.util.Random().nextInt(dates.size());
        WebElement randomDate = dates.get(randomIndex);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", randomDate);

        try {
            randomDate.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", randomDate);
        }

        System.out.println("✅ Random future date selected");
    }

    public void clickSearch() {

        WebElement search = wait.waitForClickable(searchBtn);

        // 🔥 bring to center (avoid header overlap)
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", search);

        wait.waitForPageStability();

        try {
            search.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", search);
        }

        System.out.println("✅ Search clicked");
    }
}
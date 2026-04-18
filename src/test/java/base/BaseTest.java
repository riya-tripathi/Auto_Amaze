package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchResultsPage;
import base.BaseTest;

public class FlightSearchTest extends BaseTest {

    @Test
    public void testFlightSearch() {

        driver.get("https://www.cleartrip.com/");

        HomePage home = new HomePage(driver);

        // 🔥 Critical step
        home.handlePopup();

        home.selectFromCity("Delhi");
        home.selectToCity("Mumbai");

        home.selectDate();

        try { Thread.sleep(1500); } catch (Exception e) {}

        home.clickSearch();

        SearchResultsPage results = new SearchResultsPage(driver);

        Assert.assertTrue(results.isResultsDisplayed());
    }
}
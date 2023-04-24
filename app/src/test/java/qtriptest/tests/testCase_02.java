package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HomePage;
import java.net.MalformedURLException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class testCase_02 {
    public static String lastGeneratedUser;
    static RemoteWebDriver driver;

    @BeforeSuite(alwaysRun = true)
    public static void createBrowser() throws MalformedURLException{
        DriverSingleton sbc1 = DriverSingleton.getInstanceOfSingletonBrowserClass();
        driver = sbc1.getDriver();
        System.out.println("Create Browser");
        driver.manage().window().maximize();
    }
    
    @Test(dataProvider = "data-provider", dataProviderClass = DP.class, description = "Verify that Search and filters work fine", priority = 2, groups = {"Search and Filter flow"})
    public void TestCase02(String cityName, String category_filer, String DurationFilter, String ExpectedFilteredResults, String ExpectedUnFilteredResults) throws InterruptedException{
        System.out.println(cityName + " " + category_filer + " " + DurationFilter + " " + ExpectedFilteredResults +" " + ExpectedUnFilteredResults);
        HomePage homePage = new HomePage(driver);
        homePage.navigateToHome();
        Thread.sleep(2000);
        homePage.searchCity(cityName);
        homePage.assertAutoCompleteText(cityName);
        Thread.sleep(2000);
        homePage.selectCity(cityName);
        Thread.sleep(2000);
        Assert.assertTrue(driver.getCurrentUrl().contains(cityName.toLowerCase()), "Redirected to city's page");

        AdventurePage adventurePage = new AdventurePage(driver);
        adventurePage.setFilterValue(DurationFilter);
        adventurePage.setCategoryValue(category_filer);
        int filteredCount = adventurePage.getResultCount();
        Assert.assertEquals(String.valueOf(filteredCount), ExpectedFilteredResults);

        adventurePage.removeFilter();
        int unfilteredCount = adventurePage.getResultCount();
        Assert.assertEquals(String.valueOf(unfilteredCount), ExpectedUnFilteredResults);
    }

    @AfterSuite
    public static void quitDriver(){
        System.out.println("quit()");
        driver.quit();
    }

}

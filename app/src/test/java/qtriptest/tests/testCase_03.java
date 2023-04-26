package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ReportSingleton;
import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HistoryPage;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class testCase_03 {
    public static String lastGeneratedUser;
    static RemoteWebDriver driver;

    @BeforeSuite(alwaysRun = true)
    public static void createBrowser() throws MalformedURLException{
        DriverSingleton sbc1 = DriverSingleton.getInstanceOfSingletonBrowserClass();
        driver = sbc1.getDriver();
        System.out.println("Create Browser");
        driver.manage().window().maximize();
        ReportSingleton rs = ReportSingleton.getInstanceOfSingletonReport();
        rs.createTest("TestCase03");
    }

    @Test(dataProvider = "data-provider", dataProviderClass = DP.class, description = "Verify that adventure booking and cancellation works fine", priority = 3, groups = {"Booking and Cancellation Flow"})
    public void TestCase03(String username, String password, String SearchCity, String AdventureName, String GuestName, String Date, String count) throws InterruptedException{
        HomePage homePage = new HomePage(driver);
        homePage.navigateToHome();
        homePage.clickRegister();
        Assert.assertTrue(driver.getCurrentUrl().contains("/register"), "Redirected to registration page on not");
        
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.registerNewUser(username, password, true);
        boolean status = driver.getCurrentUrl().contains("/login");
        Assert.assertTrue(status, "Registration successful or not");
        lastGeneratedUser = registerPage.lastGeneratedUsername;

        LoginPage loginPage = new LoginPage(driver);
        loginPage.performLogin(lastGeneratedUser, password);
        status = homePage.isUserLoggedIn();
        Assert.assertTrue(status, "User logged in successfully");

        homePage.navigateToHome();
        Thread.sleep(2000);
        homePage.searchCity(SearchCity);
        homePage.assertAutoCompleteText(SearchCity);
        Thread.sleep(2000);
        homePage.selectCity(SearchCity);
        Thread.sleep(2000);
        Assert.assertTrue(driver.getCurrentUrl().contains(SearchCity.toLowerCase()), "Redirected to city's page");

        AdventurePage adventurePage = new AdventurePage(driver);
        adventurePage.selectAdventure(AdventureName);

        AdventureDetailsPage adventureDetailsPage = new AdventureDetailsPage(driver);
        adventureDetailsPage.bookAdventure(GuestName, Date, Integer.parseInt(count));
        status = adventureDetailsPage.isBookingSuccessful();
        Assert.assertTrue(status, "Booking successful");

        HistoryPage historyPage = new HistoryPage(driver);
        historyPage.clickHereButton();
        List<String> list = new ArrayList<String>(historyPage.getReservation());
        String transactionId;
        for(int i=0; i<list.size(); i++){
            transactionId = list.get(i);
            historyPage.cancelReservation(transactionId);
            Thread.sleep(1000);
        }    
        Thread.sleep(3000); 
        driver.navigate().refresh();
        homePage.logOutUser();
        
    }

    @AfterSuite
    public static void quitDriver(){
        System.out.println("quit()");
        ReportSingleton rs = ReportSingleton.getInstanceOfSingletonReport();
        rs.flushReport();
        driver.quit();
    }

}

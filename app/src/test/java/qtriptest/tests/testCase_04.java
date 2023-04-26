package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ReportSingleton;
import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.net.MalformedURLException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class testCase_04 {
    public static String lastGeneratedUser;
    static RemoteWebDriver driver;

    @BeforeSuite(alwaysRun = true)
    public static void createBrowser() throws MalformedURLException {
        DriverSingleton sbc1 = DriverSingleton.getInstanceOfSingletonBrowserClass();
        driver = sbc1.getDriver();
        System.out.println("Create Browser");
        driver.manage().window().maximize();
        ReportSingleton rs = ReportSingleton.getInstanceOfSingletonReport();
        rs.createTest("TestCase03");
    }

    @Test(dataProvider = "data-provider", dataProviderClass = DP.class,
            description = "Verify that Booking history can be viewed", priority = 4, groups = {"Reliability Flow"})
    public void TestCase04(String username, String password, String dataset1, String dataset2,
            String dataset3) throws MalformedURLException, InterruptedException {
        System.out.println(
                username + " " + password + " " + dataset1 + " " + dataset2 + " " + dataset3);

        HomePage homePage = new HomePage(driver);
        homePage.navigateToHome();
        homePage.clickRegister();
        Assert.assertTrue(driver.getCurrentUrl().contains("/register"),
                "Redirected to registration page on not");

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.registerNewUser(username, password, true);
        boolean status = driver.getCurrentUrl().contains("/login");
        Assert.assertTrue(status, "Registration successful or not");
        lastGeneratedUser = registerPage.lastGeneratedUsername;

        LoginPage loginPage = new LoginPage(driver);
        loginPage.performLogin(lastGeneratedUser, password);
        status = homePage.isUserLoggedIn();
        Assert.assertTrue(status, "User logged in successfully");
        homePage.logOutUser();
        

        // for (int i = 1; i <= 3; i++) {
        //     String[] arr = dataset1.split(";");
        //     for (int j = 0; j < arr.length; j++) {
        //         String SearchCity = arr[0];
        //         String AdventureName = arr[1];
        //         String GuestName = arr[2];
        //         String Date = arr[3];
        //         String count = arr[4];

        //         homePage.navigateToHome();
        //         Thread.sleep(2000);
        //         homePage.searchCity(SearchCity);
        //         homePage.assertAutoCompleteText(SearchCity);
        //         Thread.sleep(2000);
        //         homePage.selectCity(SearchCity);
        //         Thread.sleep(2000);
        //         Assert.assertTrue(driver.getCurrentUrl().contains(SearchCity.toLowerCase()),
        //                 "Redirected to city's page");

        //         AdventurePage adventurePage = new AdventurePage(driver);
        //         adventurePage.selectAdventure(AdventureName);

        //         AdventureDetailsPage adventureDetailsPage = new AdventureDetailsPage(driver);
        //         adventureDetailsPage.bookAdventure(GuestName, Date, Integer.parseInt(count));
        //         status = adventureDetailsPage.isBookingSuccessful();
        //         Assert.assertTrue(status, "Booking successful");

        //     }
        // }
    }

    @AfterSuite
    public static void quitDriver(){
        System.out.println("quit()");
        ReportSingleton rs = ReportSingleton.getInstanceOfSingletonReport();
        rs.flushReport();
        driver.quit();
    }

}

package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ExcelUtility;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.net.MalformedURLException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static org.testng.Assert.*;
import qtriptest.pages.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class testCase_01 {
    public static String lastGeneratedUser;
    static RemoteWebDriver driver;

    @BeforeSuite(alwaysRun = true)
    public static void createBrowser() throws MalformedURLException{
        DriverSingleton sbc1 = DriverSingleton.getInstanceOfSingletonBrowserClass();
        driver = sbc1.getDriver();
        System.out.println("Create Browser");
        driver.manage().window().maximize();
    }

    @Test(dataProvider = "data-provider", dataProviderClass = DP.class, description = "Verify user registration - login - logout", priority = 1, groups = {"Login Flow"})
    //@Parameters({ "username", "password" })
    public void TestCase01(String username, String password) throws MalformedURLException, InterruptedException{

        // Don't create another instances of driver in Test. The function of creating a the driver object is taken care by @BeforeSute method.
        // RemoteWebDriver driver = DriverSingleton.getDriverInstance();
        System.out.println(username + "  " + password);
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
        homePage.logOutUser();
    }

    @AfterSuite
    public static void quitDriver(){
        System.out.println("quit()");
        driver.quit();
    }
}


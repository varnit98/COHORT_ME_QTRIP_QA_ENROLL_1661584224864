package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.testng.Assert;

public class LoginPage {

    RemoteWebDriver driver;
    String url = "https://qtripdynamic-qa-frontend.vercel.app/pages/login/";

    public LoginPage(RemoteWebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
    }

    @FindBy(xpath = "//input[@name = 'email']")
    WebElement usernameText;

    @FindBy(xpath = "//input[@name = 'password']")
    WebElement passwordText;

    @FindBy(xpath = "//button[text() = 'Login to QTrip']")
    WebElement loginButton;

    public void performLogin(String username, String password) throws InterruptedException {
        if (!this.driver.getCurrentUrl().equals(this.url)) {
            this.driver.get(this.url);
        }
        Thread.sleep(2000);
        usernameText.clear();
        passwordText.clear();
        usernameText.sendKeys(username);
        passwordText.sendKeys(password);
        //loginButton.click();
        boolean status = SeleniumWrapper.click(loginButton, this.driver);
        Assert.assertTrue(status);
        Thread.sleep(2000);
    }
}

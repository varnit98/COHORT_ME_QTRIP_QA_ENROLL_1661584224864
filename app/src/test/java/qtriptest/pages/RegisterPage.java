package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import java.util.UUID;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.testng.Assert;

public class RegisterPage {

    RemoteWebDriver driver;
    String url = "https://qtripdynamic-qa-frontend.vercel.app/pages/register/";
    public String lastGeneratedUsername = "";

    public RegisterPage(RemoteWebDriver driver){
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
    }

    @FindBy(xpath = "//input[@name = 'email']")
    WebElement usernameText;

    @FindBy(xpath = "//input[@name = 'password']")
    WebElement passwordText;
    
    @FindBy(xpath = "//input[@name = 'confirmpassword']")
    WebElement confirmPasswordText;

    @FindBy(xpath = "//button[text() = 'Register Now']")
    WebElement registerButton;

    public void registerNewUser(String username, String password, boolean generateRandomUsername) throws InterruptedException{
        if(!this.driver.getCurrentUrl().equals(this.url)) {
            this.driver.get(this.url);
        }
            String test_data_username;

            usernameText.clear();
            passwordText.clear();
            confirmPasswordText.clear();

            if(generateRandomUsername){
                test_data_username = username+UUID.randomUUID().toString();
            }else{
                test_data_username = username;
            }
            usernameText.sendKeys(test_data_username);
            passwordText.sendKeys(password);
            confirmPasswordText.sendKeys(password);
            //registerButton.click();
            boolean status = SeleniumWrapper.click(registerButton, this.driver);
            Assert.assertTrue(status);
            Thread.sleep(2000);
            this.lastGeneratedUsername = test_data_username;

            status = this.driver.getCurrentUrl().contains("/login");
            Assert.assertTrue(status, "Registration successful or not");
    }
}

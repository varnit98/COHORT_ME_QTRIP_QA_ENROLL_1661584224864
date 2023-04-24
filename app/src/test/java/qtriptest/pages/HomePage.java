package qtriptest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    RemoteWebDriver driver;
    String url = "https://qtripdynamic-qa-frontend.vercel.app/";

    public HomePage(RemoteWebDriver driver){
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
    }

    @FindBy(xpath = "//a[text()='Register']")
    WebElement registerButton;

    @FindBy(xpath = "//div[text() = 'Logout']")
    WebElement logoutButton;

    @FindBy(id = "autocomplete")
    WebElement searchTextBox;

    @FindBy(xpath = "//*[text() = 'No City found']")
    WebElement noCityFoundText;

    public void navigateToHome(){
        if(!this.driver.getCurrentUrl().equals(this.url)){
            this.driver.get(this.url);
        }
    }
    public void clickRegister() throws InterruptedException{
        
        Thread.sleep(5000);
        registerButton.click();
        Thread.sleep(5000);
        
    }

    public boolean isUserLoggedIn(){
        String userLoggedInText = this.driver.switchTo().alert().getText();
        if(userLoggedInText.equals("You have logged in Succesfully !")){
            this.driver.switchTo().alert().accept();
            return true;
        }
        else return false;        
    }

    public void logOutUser() throws InterruptedException{
        logoutButton.click();
        Thread.sleep(2000);
    }

    public void searchCity(String cityName) throws InterruptedException{
        //navigateToHome();
        String halfCityName;
        if(cityName.length() > 3){
            halfCityName = cityName.substring(0,3);
        }else halfCityName = cityName;
        searchTextBox.clear();
        searchTextBox.sendKeys(halfCityName);
        searchTextBox.sendKeys(Keys.ENTER);
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[text() = ' " + cityName + " ']")));
    }

    public boolean assertAutoCompleteText(String cityName){
        String halfCityName;
        if(cityName.length() > 3){
            halfCityName = cityName.substring(0,6);
        }else halfCityName = cityName;
        WebElement searchCityDropDown = driver.findElement(By.xpath("//li[text() = ' " + cityName + " ']"));
        if(searchCityDropDown.getText().contains(halfCityName)){
            return true;
        }
        System.out.println(noCityFoundText.getText());
        return false;        
    }

    public void selectCity(String cityName) throws InterruptedException{
        if(assertAutoCompleteText(cityName)){
            driver.findElement(By.xpath("//li[text() = ' " + cityName + " ']")).click();
        }
        Thread.sleep(5000);
    }


}

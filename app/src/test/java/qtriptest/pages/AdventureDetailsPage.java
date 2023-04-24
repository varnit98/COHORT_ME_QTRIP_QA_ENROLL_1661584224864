
package qtriptest.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.FindBy;

public class AdventureDetailsPage {
    RemoteWebDriver driver;

    public AdventureDetailsPage(RemoteWebDriver driver){
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
    }

    @FindBy(xpath = "//input[@name = 'name']")
    WebElement nameTextBox;

    @FindBy(xpath = "//input[@name = 'date']")
    WebElement dateTextBox;

    @FindBy(xpath = "//input[@name = 'person']")
    WebElement personCountTextBox;

    @FindBy(xpath = "//button[text() = 'Reserve']")
    WebElement reserveButton;

    @FindBy(xpath = "//div[contains(text(),'Greetings! Reservation for this adventure is successful.')]")
    WebElement successfulBookingMessage;

    public void bookAdventure(String name, String date, int personCount) throws InterruptedException{
        nameTextBox.clear();
        dateTextBox.clear();
        personCountTextBox.clear();
        nameTextBox.sendKeys(name);
        //Thread.sleep(1000);
        dateTextBox.sendKeys(date);
        //Thread.sleep(2000);
        personCountTextBox.sendKeys(String.valueOf(personCount));
        reserveButton.click();
    }

    public boolean isBookingSuccessful()throws InterruptedException{
        Thread.sleep(5000);
        // System.out.println(successfulBookingMessage.getText());
        if(successfulBookingMessage.getText().contains("Greetings! Reservation for this adventure is successful.")){
            return true;
        }else return false;
       
        }
}
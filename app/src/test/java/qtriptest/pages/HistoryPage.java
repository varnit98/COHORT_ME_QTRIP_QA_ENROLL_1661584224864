
package qtriptest.pages;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.testng.Assert;

public class HistoryPage {
    RemoteWebDriver driver;

    public HistoryPage(RemoteWebDriver driver){
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
    }

    @FindBy(xpath = "//div[@id='reserved-banner']/a")
    WebElement historyLinkButton;

    @FindBy(xpath = "//tbody[@id = 'reservation-table']/tr/th")
    List<WebElement> transactionIdElement;

    @FindBy(xpath = "//tbody[@id = 'reservation-table']/tr//td[8]/div/button")
    List<WebElement> cancelButton;

    public void clickHereButton() throws InterruptedException{
        historyLinkButton.click();
        Thread.sleep(2000);
        Assert.assertTrue(this.driver.getCurrentUrl().contains("reservations"), "Redirected to history page");
    }

    public List<String> getReservation(){
        List<String> list = new ArrayList<String>();
        for(int i=0; i<transactionIdElement.size(); i++){
            list.add(transactionIdElement.get(i).getText());
        }
        return list;
    }

    public void cancelReservation(String transacationId){
        for(int i=0; i<transactionIdElement.size(); i++){
            if(transactionIdElement.get(i).getText().equals(transacationId)){
                cancelButton.get(i).click();
            }
        }
    }
}

// class ReservationHistory{
//     List<String> transactionId;

//     public List<String> getTransactionId() {
//         return transactionId;
//     }

//     public void setTransactionId(List<String> transactionId) {
//         this.transactionId = transactionId;
//     }    
// }
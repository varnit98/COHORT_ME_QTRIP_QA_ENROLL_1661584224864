package qtriptest;

import org.apache.poi.hssf.record.BoolErrRecord;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SeleniumWrapper {

    public static boolean click(WebElement elementToClick, RemoteWebDriver driver){
        if(elementToClick.isDisplayed()){
            elementToClick.click();
            return true;
        }
        return false;
    }

    public static boolean sendKeys(WebElement inputBox, String keysToSend){
        inputBox.clear();
        inputBox.sendKeys(keysToSend);
        return true;
    }

    public static boolean navigate(RemoteWebDriver driver, String url){
        if(!(driver.getCurrentUrl().equals(url))){
            driver.get(url);
        }
        if(driver.getCurrentUrl().equals(url)){
            return true;
        }else return false;
    }

    // public static WebElement findElementsWithRetry(RemoteWebDriver driver, By by, int retryCount){
        
    // }
}

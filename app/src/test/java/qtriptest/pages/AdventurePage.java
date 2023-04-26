package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class AdventurePage {
    RemoteWebDriver driver;

    public AdventurePage(RemoteWebDriver driver){
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
    }

    @FindBy(xpath = "//*[@id = 'duration-select']")
    WebElement hoursDropDown;

    @FindBy(xpath = "//*[@id = 'category-select']")
    WebElement categoryDropDown;

    @FindBy(xpath = "//div[@class = 'col-6 col-lg-3 mb-4']")
    List<WebElement> data;

    @FindBy(xpath = "//div[contains(text(), 'Clear')]")
    List<WebElement> clearButton;

    @FindBy(xpath = "//div[@class = 'd-block d-md-flex justify-content-between flex-wrap pl-3 pr-3']")
    List<WebElement> allAdventures;	

    public void setFilterValue(String filterValue){
        Select dropDown = new Select(hoursDropDown);
        dropDown.selectByVisibleText(filterValue);
    }

    public void setCategoryValue(String categoryValue){
        Select dropDown = new Select(categoryDropDown);
        dropDown.selectByVisibleText(categoryValue);
    }

    public int getResultCount(){
        int resultSize = data.size();
        return resultSize;
    }

    public void removeFilter(){
        // clearButton.get(0).click();;
        // clearButton.get(1).click();
        boolean status = SeleniumWrapper.click(clearButton.get(0), this.driver);
        Assert.assertTrue(status);
        status = SeleniumWrapper.click(clearButton.get(1), this.driver);
        Assert.assertTrue(status);
    }

    public void selectAdventure(String adventureName) throws InterruptedException{
        for(int i=0; i<allAdventures.size(); i++){
            if(allAdventures.get(i).getText().contains(adventureName)){
                //allAdventures.get(i).click();
                boolean status = SeleniumWrapper.click(allAdventures.get(i), this.driver);
                Assert.assertTrue(status);
                break;
            }
        }
        Thread.sleep(3000);
        //driver.findElement(By.xpath("//h5[text()='" + adventureName + "']")).click();
    }
}
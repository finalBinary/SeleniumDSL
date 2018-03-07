package SeleniumDSL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;

import java.util.concurrent.TimeUnit;

public class SeleniumHandler{
        private WebDriver driver;

        public SeleniumHandler(WebDriver driver){
                this.driver = driver;
        }

	public SeleniumHandler(){
		final String basePath = new File("").getAbsolutePath();

		System.setProperty("webdriver.chrome.driver", basePath+"/src/main/resources/chromedriver");
		this.driver = new ChromeDriver();

		this.driver.get("https://www.google.com/");
		this.driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	}

        public WebElement getElement(String id){
                try{
                        if(id.length() < 10){
                                return driver.findElement(By.id(id));
                        } else {
                                return driver.findElement(By.xpath(id));
                        }
                } catch(NoSuchElementException ex){
                        ex.printStackTrace();
                }
                return null;
        }
}

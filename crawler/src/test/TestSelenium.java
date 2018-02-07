package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

/**
 * Created by wyz on 2018/2/6.
 */
public class TestSelenium {
    public static WebDriver getWebDriver(String url) {
        //Use IE. u need to set drive path and capabilities
/*          System.setProperty("webdriver.ie.driver", "D:/drive/IEDriverServer.exe");
            DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
            ieCapabilities.setCapability(InternetExplorerDriver
                            .INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
            WebDriver driver = new InternetExplorerDriver();*/

        //Use Firefox.need set property and install path;
/*          System.setProperty("webdriver.gecko.driver", "D:/drive/geckodriver.exe");
            System.setProperty("webdriver.firefox.bin", "C:\\Program Files\\Mozilla Firefox\\firefox.exe");
            WebDriver driver = new FirefoxDriver();*/

        //Use Chrome ,but support 32-bit only
        System.setProperty("webdriver.chrome.driver", "D:/drive/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get(url);
        return driver;
    }

    public static void main(String[] args) throws Exception {
        WebDriver driver = getWebDriver("http://www.baidu.com");
        WebElement keyword = driver.findElement(By.id("kw"));
        //enter keyword
        keyword.sendKeys("selenium");
        //get search button and click it
        WebElement searchBtn = driver.findElement(By.id("su"));
        searchBtn.click();
        Thread.sleep(5000);//waiting for the result
        //get result and print out cyclical--搜索结果是h3标签class=“t”，故以此为条件
        List<WebElement> titles = driver.findElements(By.cssSelector("h3.t"));
        for (WebElement title : titles) {
            WebElement webTitle = title.findElement(By.tagName("a"));
            System.err.println("webTitle:" + webTitle + ":" + webTitle.getText());
        }
    }
}

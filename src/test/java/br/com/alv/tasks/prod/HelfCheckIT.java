package br.com.alv.tasks.prod;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HelfCheckIT {


    public ThreadLocal<RemoteWebDriver> acessarAplicacao() throws MalformedURLException{
        //WebDriver driver = new ChromeDriver();
        //DesiredCapabilities cap = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<RemoteWebDriver>();
        driver.set(new RemoteWebDriver(new URL("http://192.168.50.196:4444/wd/hub"), options));

        //WebDriver driver = new RemoteWebDriver( new URL("http://10.52.226.13:4444/wd/hub"), cap);
        driver.get().get("http://192.168.50.196:9999/tasks");
        WebDriverWait wait = new WebDriverWait(driver.get(), Duration.ofSeconds(10));
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }    
    
    @Test
    public void helfCheck() throws MalformedURLException{
        ThreadLocal<RemoteWebDriver> driver = acessarAplicacao();
        try {
            String version = driver.get().findElement(By.id("version")).getText();
            Assert.assertTrue(version.startsWith("build") );
            System.out.println(version);
        }
        finally{
            driver.get().quit();
        }

    }
    
}

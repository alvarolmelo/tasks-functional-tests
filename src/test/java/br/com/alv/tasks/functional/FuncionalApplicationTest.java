package br.com.alv.tasks.functional;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FuncionalApplicationTest {
    
    //protected static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<RemoteWebDriver>();

    public ThreadLocal<RemoteWebDriver> acessarAplicacao() throws MalformedURLException{
        //WebDriver driver = new ChromeDriver();
        //DesiredCapabilities cap = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<RemoteWebDriver>();
        driver.set(new RemoteWebDriver(new URL("http://192.168.50.196:4444/wd/hub"), options));

        //WebDriver driver = new RemoteWebDriver( new URL("http://10.52.226.13:4444/wd/hub"), cap);
        driver.get().get("http://10.52.226.16:8001/tasks");
        WebDriverWait wait = new WebDriverWait(driver.get(), Duration.ofSeconds(10));
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    @Test
    public void deveSalvarTarefaComSucesso() throws MalformedURLException{
        ThreadLocal<RemoteWebDriver> driver = acessarAplicacao();
        try {
            driver.get().findElement(By.id("addTodo")).click();
            driver.get().findElement(By.id("task")).sendKeys("Teste via Selenium");
            driver.get().findElement(By.id("dueDate")).sendKeys("10/10/2030");
            driver.get().findElement(By.id("saveButton")).click();
            String message = driver.get().findElement(By.id("message")).getText();
            Assert.assertEquals("Success!", message);
        }finally{
            driver.get().quit();
        };
    }

    @Test
    public void naoDeveSalvarTarefaSemDescricao() throws MalformedURLException{
        ThreadLocal<RemoteWebDriver>  driver = acessarAplicacao();
        try {
            driver.get().findElement(By.id("addTodo")).click();
            driver.get().findElement(By.id("dueDate")).sendKeys("10/10/2030");
            driver.get().findElement(By.id("saveButton")).click();
            String message = driver.get().findElement(By.id("message")).getText();
            Assert.assertEquals("Fill the task description", message);
        }finally{
            driver.get().quit();
        };
    }

    @Test
    public void naoDeveSalvarTarefaSemData() throws MalformedURLException{
        ThreadLocal<RemoteWebDriver>  driver = acessarAplicacao();
        try {
            driver.get().findElement(By.id("addTodo")).click();
            driver.get().findElement(By.id("task")).sendKeys("Teste via Selenium");
            driver.get().findElement(By.id("saveButton")).click();
            String message = driver.get().findElement(By.id("message")).getText();
            Assert.assertEquals("Fill the due date", message);
        }finally{
            driver.get().quit();
        };
    }

    @Test
    public void naoDeveSalvarTarefaComDataPassada() throws MalformedURLException{
        ThreadLocal<RemoteWebDriver>  driver = acessarAplicacao();
        try {
            driver.get().findElement(By.id("addTodo")).click();
            driver.get().findElement(By.id("task")).sendKeys("Teste via Selenium");
            driver.get().findElement(By.id("dueDate")).sendKeys("10/10/2000");
            driver.get().findElement(By.id("saveButton")).click();
            String message = driver.get().findElement(By.id("message")).getText();
            Assert.assertEquals("Due date must not be in past", message);
        }finally{
            driver.get().quit();
        };
    }

}

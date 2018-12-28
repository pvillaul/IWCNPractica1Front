package tests;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import environment.EnvironmentManager;
import environment.RunEnvironment;
import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class ProductTests {
	@Before
    public void startBrowser() {
//        EnvironmentManager.initChromeWebDriver();
        EnvironmentManager.initFirefoxWebDriver();
    }

    @Test
    public void addUser() {
        WebDriver driver = RunEnvironment.getWebDriver();
        driver.get("http://localhost:8080");

        // add product
        driver.findElement(By.id("add")).click();
        driver.findElement(By.id("code")).sendKeys("CODE001");
        driver.findElement(By.id("name")).sendKeys("Lata de coca-cola");
        driver.findElement(By.id("description")).sendKeys("Lata de coca-cola de 33cl");
        driver.findElement(By.id("price")).sendKeys("51");
        driver.findElement(By.id("add")).click();

        Assert.assertEquals("CODE001",driver.getTitle());
    }
    
    @Test
    public void modifyUser() {
        WebDriver driver = RunEnvironment.getWebDriver();
        driver.get("http://localhost:8080/listUsers");
        
        List<WebElement> elements = driver.findElements(By.id("modify"));
        elements.get(0).click();
        
        driver.findElement(By.id("code")).sendKeys("CODE001b");
        driver.findElement(By.id("name")).sendKeys("Lata de coca-cola bis");
        driver.findElement(By.id("description")).sendKeys("Lata de coca-cola de 33cl");
        driver.findElement(By.id("price")).sendKeys("55");
        driver.findElement(By.id("add")).click();
        
        List<WebElement> elementsnew = driver.findElements(By.id("modify"));
        
        Assert.assertEquals("http://localhost:8080/listUsers",driver.getCurrentUrl());
        Assert.assertTrue(elements.size() ==  elementsnew.size());
    }
    
    @Test
    public void showUser() {
        WebDriver driver = RunEnvironment.getWebDriver();
        driver.get("http://localhost:8080/listUsers");
        
        List<WebElement> elements = driver.findElements(By.id("prod"));
        String href = elements.get(0).getAttribute("href");
        String[] url = href.split("/");
        String id = url[1];
        
        elements.get(0).click();

        Assert.assertTrue("http://localhost:8080/listUsers" != driver.getCurrentUrl());
        Assert.assertEquals(id,driver.getTitle());
    }
    
    @Test
    public void deleteUser() {
        WebDriver driver = RunEnvironment.getWebDriver();
        driver.get("http://localhost:8080/listUsers");
        
        List<WebElement> elementsold = driver.findElements(By.id("remove"));
        int oldnumProducts = elementsold.size();

        // delete
        elementsold.get(0).click();
        
        List<WebElement> elementsnew = driver.findElements(By.id("remove"));
        int newnumProducts = elementsnew.size();
        
        Assert.assertTrue(oldnumProducts  > newnumProducts );
    }

    @Test
    public void listUsers() {
        WebDriver driver = RunEnvironment.getWebDriver();
        driver.get("http://localhost:8080");

        // product list
        driver.findElement(By.id("list")).click();

        Assert.assertEquals("http://localhost:8080/listUsers",driver.getCurrentUrl());
    }

    @After
    public void tearDown() {
        EnvironmentManager.shutDownDriver();
    }
}

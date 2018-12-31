package tests;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.iw.pract1c.models.User;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import environment.EnvironmentManager;
import environment.RunEnvironment;
import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class AddUser {
    User user = new User();
    
    int oldnumProducts = 0;
    
    @Before
    public void startBrowser() {
//        EnvironmentManager.initChromeWebDriver();
        EnvironmentManager.initFirefoxWebDriver();
    }

    @Given("^I have a user <User>$")
    public void i_have_a_user_User() throws Throwable {
    	user.setName("admin");
    	user.setPassword("1234");
    	user.setRol("VIEWER");
    }

    @When("^Save the user <User>$")
    public void save_the_user_User() throws Throwable {
    	WebDriver driver = RunEnvironment.getWebDriver();
    	driver.get("http://localhost:8080/listUsers");
    	
    	List<WebElement> elementsold = driver.findElements(By.id("remove"));
        oldnumProducts = elementsold.size();

        // add product
        driver.findElement(By.id("add")).click();
        
        driver.findElement(By.id("name")).sendKeys(user.getName());
        driver.findElement(By.id("password")).sendKeys(user.getPassword());
        driver.findElement(By.id("rol")).sendKeys(user.getRol());
        
        driver.findElement(By.id("send")).click();
    }

    @Then("^Returns <User>$")
    public void returns_User() throws Throwable {
    	WebDriver driver = RunEnvironment.getWebDriver();
        List<WebElement> elementsnew = driver.findElements(By.id("remove"));
        int newnumProducts = elementsnew.size();
        
        Assert.assertTrue(oldnumProducts  < newnumProducts );
    }
    
    @After
    public void tearDown() {
        EnvironmentManager.shutDownDriver();
    }
}
package tests;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.iw.pract1c.models.User;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import environment.EnvironmentManager;
import environment.RunEnvironment;
import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class EditUser {
    User user = new User();
    
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

    @When("^Modify the user <User>$")
    public void modify_the_user_User() throws Throwable {
    	WebDriver driver = RunEnvironment.getWebDriver();
    	driver.get("http://localhost:8080/listUsers");

        // add product
        driver.findElement(By.id("edit")).click();
        
        driver.findElement(By.id("name")).sendKeys(user.getName());
        driver.findElement(By.id("password")).sendKeys("4545");
        driver.findElement(By.id("rol")).sendKeys(user.getRol());
        
        driver.findElement(By.id("send")).click();
    }

    @Then("^Returns <User>$")
    public void returns_User() throws Throwable {
        WebDriver driver = RunEnvironment.getWebDriver();
        Assert.assertEquals("4545",driver.findElement(By.id("password")).getText());
    }
    
    @After
    public void tearDown() {
        EnvironmentManager.shutDownDriver();
    }
}
package tests;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import environment.EnvironmentManager;
import environment.RunEnvironment;
import com.iw.pract1c.models.User;

public class UserLogin {
    User user = new User();

    @Given("^I open Firefox web browser and launch the application$")
    public void i_open_Firefox_web_browser_and_launch_the_application() throws Throwable {
        EnvironmentManager.initFirefoxWebDriver();
    }

    @Given("^I have a admin user <UserA>$")
    public void a_admin_user_UserA() throws Throwable {
        user.setName("root");
        user.setPassword("root");
    }

    @When("^User logs in <UserA>$")
    public void user_logs_in_UserA() throws Throwable {
        WebDriver driver = RunEnvironment.getWebDriver();
        driver.get("http://localhost:8080");

        // Input username and password
        driver.findElement(By.name("username")).sendKeys(user.getName());
        driver.findElement(By.name("password")).sendKeys("root");
        driver.findElement(By.tagName("button")).click();
    }


    @Then("^Validate log in <UserA>$")
    public void validate_log_in_UserA() throws Throwable {
        WebDriver driver = RunEnvironment.getWebDriver();
        Assert.assertEquals("SpringFilm - Index - Search",driver.getTitle());
    }
}

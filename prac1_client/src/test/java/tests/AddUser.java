package tests;

import com.iw.pract1c.models.User;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import environment.RunEnvironment;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AddUser {
    String username;
    User user = new User();

    @Given("^I have a new user <User>$")
    public void i_have_a_new_user_User() throws Throwable {
        user.setName("badUser");
        user.setPassword("password");
        user.setRol("VIEW");
    }

    @When("^Save the new user <User>$")
    public void save_the_new_user_User() throws Throwable {
        WebDriver driver = RunEnvironment.getWebDriver();
        driver.findElement(By.name("add")).click();
        driver.findElement(By.id("name")).sendKeys(user.getName());
        driver.findElement(By.id("pass")).sendKeys(user.getPassword());
        driver.findElement(By.id("rol")).sendKeys(user.getRol());
        driver.findElement(By.name("add")).click();
    }

    @Then("^Validate new <User>$")
    public void validate_new_User() throws Throwable {
        WebDriver driver = RunEnvironment.getWebDriver();
        assert driver.findElements(By.name(user.getName())).size() == 1;
    }

}
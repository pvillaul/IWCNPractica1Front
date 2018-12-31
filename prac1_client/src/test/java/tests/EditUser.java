package tests;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import environment.RunEnvironment;
import com.iw.pract1c.models.User;

public class EditUser {
    String username;
    User user = new User();

    @Given("^I have a username <String>$")
    public void i_have_a_username_String() throws Throwable {
        username = "user";
    }

    @Then("^Get to User Management Index$")
    public void get_to_User_Management_Index() throws Throwable {
        WebDriver driver = RunEnvironment.getWebDriver();
        driver.get("http://127.0.0.1:8080/listUsers");
        user.setName("root");
        user.setPassword("root");
        driver.findElement(By.name("username")).sendKeys(user.getName());
        driver.findElement(By.name("password")).sendKeys("root");
        driver.findElement(By.tagName("button")).click();

    }

    @When("^Modify the user <User>$")
    public void modify_the_user_User() throws Throwable {
        WebDriver driver = RunEnvironment.getWebDriver();
        driver.findElement(By.name("modify?id="+username)).click();
        driver.findElement(By.id("rol")).sendKeys("ADMIN");
        driver.findElement(By.name("send")).click();
    }

    @Then("^Validate <User>$")
    public void validate_User() throws Throwable {
        WebDriver driver = RunEnvironment.getWebDriver();
        driver.findElement(By.name("rol")).getText().equals("ADMIN");

    }
}
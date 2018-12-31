package tests;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import environment.RunEnvironment;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DeleteUser {
    String username = "user";

    @When("^Delete the user that matches the username <String>$")
    public void delete_the_user_that_matches_the_username_String() throws Throwable {
        WebDriver driver = RunEnvironment.getWebDriver();
        driver.findElement(By.name("remove?id="+username)).click();
    }
    @Then("^Validate Delete <User>$")
    public void validate_Delete_User() throws Throwable {
        WebDriver driver = RunEnvironment.getWebDriver();
        assert driver.findElements(By.name("remove?id="+username)).size() < 1;
    }
}
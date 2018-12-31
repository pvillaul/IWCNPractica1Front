package tests;

import com.iw.pract1c.models.Pelicula;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import environment.RunEnvironment;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EditPelicula {
    Pelicula movie = new Pelicula();
    String newName = "Bettercop";

    @Given("^I have a film <Pelicula>$")
    public void i_have_a_film_Pelicula() throws Throwable {
        movie.setName("Robocop");
    }

    @When("^Modify the film <Pelicula>$")
    public void modify_the_film_Pelicula() throws Throwable {
        WebDriver driver = RunEnvironment.getWebDriver();
        driver.findElement(By.name("modify?id="+movie.getName())).click();
        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys(newName);
        driver.findElement(By.name("send")).click();
    }

    @Then("^Validate modify <Pelicula>$")
    public void validate_modify_Pelicula() throws Throwable {
        WebDriver driver = RunEnvironment.getWebDriver();
        assert driver.findElements(By.name(movie.getName())).size() >= 1;
    }
}
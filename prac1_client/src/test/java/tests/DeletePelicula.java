package tests;

import com.iw.pract1c.models.Pelicula;
import com.iw.pract1c.models.User;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import environment.RunEnvironment;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DeletePelicula {
    User user = new User();
    Pelicula movie = new Pelicula();

    @When("^Delete the Movie <Pelicula>$")
    public void delete_the_Movie_Pelicula() throws Throwable {
        WebDriver driver = RunEnvironment.getWebDriver();
        movie.setName("Robocop");
        driver.findElement(By.name("remove?id="+movie.getName())).click();

    }

    @Then("^Validate delete <Pelicula>$")
    public void validate_delete_Pelicula() throws Throwable {
        WebDriver driver = RunEnvironment.getWebDriver();
        assert driver.findElements(By.name("remove?id="+movie.getName())).size() < 1;

    }
}
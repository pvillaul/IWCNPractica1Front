package tests;

import com.iw.pract1c.models.Pelicula;
import com.iw.pract1c.models.User;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import environment.RunEnvironment;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AddPelicula {
    User user = new User();
    Pelicula movie = new Pelicula();

    @Given("^I have a movie <Pelicula>$")
    public void i_have_a_movie_Pelicula() throws Throwable {
        movie.setName("Robocop");
    }

    @Then("^Get to Movie Management Index$")
    public void get_to_Movie_Management_Index() throws Throwable {
        WebDriver driver = RunEnvironment.getWebDriver();
        driver.get("http://127.0.0.1:8080/listFilms");
        user.setName("root");
        user.setPassword("root");
        driver.findElement(By.name("username")).sendKeys(user.getName());
        driver.findElement(By.name("password")).sendKeys("root");
        driver.findElement(By.tagName("button")).click();
    }

    @When("^Save the movie <Pelicula>$")
    public void save_the_movie_Pelicula() throws Throwable {
        WebDriver driver = RunEnvironment.getWebDriver();
        driver.findElement(By.name("addFilm")).click();
        driver.findElement(By.id("name")).sendKeys(movie.getName());
        driver.findElement(By.name("add")).click();
    }

    @Then("^Validate <Pelicula>$")
    public void validate_Pelicula() throws Throwable {
        WebDriver driver = RunEnvironment.getWebDriver();
        assert driver.findElements(By.name(movie.getName())).size() == 1;
    }
}
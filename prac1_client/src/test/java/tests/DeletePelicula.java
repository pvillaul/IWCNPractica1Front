package tests;

import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.iw.pract1c.models.Pelicula;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import environment.EnvironmentManager;
import environment.RunEnvironment;
import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class DeletePelicula {
    Pelicula peli = new Pelicula();
    
    int oldnumProducts = 0;
    
    @Before
    public void startBrowser() {
//        EnvironmentManager.initChromeWebDriver();
        EnvironmentManager.initFirefoxWebDriver();
    }

    @Given("^I have a film <Pelicula>$")
    public void i_have_a_film_Pelicula() throws Throwable {
        peli.setCode(99);
        peli.setName("Scarface");
        peli.setTrailer("https://www.youtube.com/watch?v=7pQQHnqBa2E");
        peli.setInfo("In Miami in 1980, a determined Cuban immigrant takes over a drug cartel and succumbs to greed.");
        peli.setYear(1983);
        peli.setDirector("Brian De Palma");
        peli.setReparto("Al Pacino, Steven Bauer, Michelle Pfeiffer, Mary Elizabeth Mastrantonio, Robert Loggia, Miriam Colon, F. Murray Abraham");
        peli.setPortada("https://is5-ssl.mzstatic.com/image/thumb/Video/5d/b3/9a/mzl.pfdbgmnr.jpg/268x0w.jpg");
        peli.setRate(3.9f);
        peli.setGenre("Crime,Drama");
    }

    @When("^Delete the film <Pelicula>$")
    public void delete_the_film_Peliucla() throws Throwable {
    	WebDriver driver = RunEnvironment.getWebDriver();
        driver.get("http://localhost:8080/listFilms");
        
        List<WebElement> elementsold = driver.findElements(By.id("remove"));
        oldnumProducts = elementsold.size();

        // delete
        elementsold.get(0).click();
    }

    @Then("^Returns <Null>$")
    public void returns_Null() throws Throwable {
        WebDriver driver = RunEnvironment.getWebDriver();
        List<WebElement> elementsnew = driver.findElements(By.id("remove"));
        int newnumProducts = elementsnew.size();
        
        Assert.assertTrue(oldnumProducts  > newnumProducts );
    }
    
    @After
    public void tearDown() {
        EnvironmentManager.shutDownDriver();
    }
}
package environment;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class EnvironmentManager {
	public static void initChromeWebDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Meyer\\IdeaProjects\\Practica1\\Practica1Front\\prac1_client\\chromedriver");
        WebDriver driver = new ChromeDriver();
        RunEnvironment.setWebDriver(driver);
    }

    public static void initFirefoxWebDriver() {
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\Meyer\\IdeaProjects\\Practica1\\Practica1Front\\prac1_client\\geckodriver.exe");
        WebDriver driver = new FirefoxDriver();
        RunEnvironment.setWebDriver(driver);
    }

    public static void shutDownDriver() {
        RunEnvironment.getWebDriver().quit();
    }
}

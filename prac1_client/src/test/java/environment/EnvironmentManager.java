package environment;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class EnvironmentManager {
	public static void initChromeWebDriver() {
        System.setProperty("webdriver.chrome.driver", "/home/pablo/Documents/workspace-sts-3.9.6.RELEASE/ClienteREST/chromedriver");
        WebDriver driver = new ChromeDriver();
        RunEnvironment.setWebDriver(driver);
    }

    public static void initFirefoxWebDriver() {
        System.setProperty("webdriver.chrome.driver", "/home/pablo/Documents/workspace-sts-3.9.6.RELEASE/ClienteREST/geckodriver");
        WebDriver driver = new FirefoxDriver();
        RunEnvironment.setWebDriver(driver);
    }

    public static void shutDownDriver() {
        RunEnvironment.getWebDriver().quit();
    }
}

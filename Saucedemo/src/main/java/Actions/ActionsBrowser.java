package Actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import java.time.Duration;

public class ActionsBrowser {
    public static ThreadLocal<WebDriver> drivers = new ThreadLocal<>();

    public enum Browsers {
        chrome, firefox
    }

    public static void windowMaximize() {
        if (drivers.get() != null) {
            drivers.get().manage().window().maximize();
        }
    }

    public static void setDrivers(Browsers browser) {
        try {
            switch (browser) {
                case chrome:
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--incognito"); // Enable incognito mode
                    drivers.set(new ChromeDriver(chromeOptions));
                    break;
                case firefox:
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    drivers.set(new FirefoxDriver(firefoxOptions));
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error initializing browser: " + e.getMessage());
        }
    }

    public static void quitDriver() {
        if (drivers.get() != null) {
            drivers.get().quit();
            drivers.remove();
        }
    }
}
package Actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ActionsBroswer {
    public static  ThreadLocal<WebDriver> drivers = new ThreadLocal<>();

    public enum Browsers {
        chrome, firefox
    }

    public static void setDrivers(Browsers browser) {
        try {
            switch (browser) {
                case chrome:
                    drivers.set(new ChromeDriver());
                    break;
                case firefox:
                    drivers.set(new FirefoxDriver());
                    break;
            }
        } catch (Exception e) {

        }
    }


    public static void quitDriver() {
        if (drivers.get() != null) {
            drivers.get().quit();
            drivers.remove();
        }
    }
}

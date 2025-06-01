package Actions;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ActionUI {
    WebDriver driver;
    int waitTime=500;

    public ActionUI(){
        this.driver = ActionsBrowser.drivers.get();
    }

    public ActionUI nav(String url){
        driver.get(url);
        return this;
    }

    public By byID(String locator){
        By loc = By.id(locator);
        return loc;
    }

    public By byXpath(String locator){
        By loc = By.xpath(locator);
        return loc;
    }

    public ActionUI enterText(By locator, String text){
        driver.findElement(locator).sendKeys(text);
        return this;
    }

    private void doubleClick(By locator){
        Actions actions = new Actions(driver);
        WebElement element = waitForElementToBeClickable(locator);
        actions.doubleClick(element).perform();
    }

    private void hoverClick(By locator){
        Actions actions = new Actions(driver);
        WebElement element = waitForElementToBeClickable(locator);
        actions.moveToElement(element).perform();
        element.click();
    }

    private WebElement waitForElementToBeClickable(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // Default timeout set to 10 seconds
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void click(By locator, By validator) {
        boolean clicked = false;

        try {
            WebElement element = waitForElementToBeClickable(locator);
            element.click();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(waitTime));
            WebElement val = wait.until(ExpectedConditions.presenceOfElementLocated(validator));
            clicked = true;
        } catch (Exception e) {
            // System.out.println("Single click failed");
        }

        if (!clicked) {
            try {
                doubleClick(locator);
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(waitTime));
                WebElement val = wait.until(ExpectedConditions.presenceOfElementLocated(validator));
                clicked = true;
            } catch (Exception e) {
                // System.out.println("Double click failed");
            }
        }

        if (!clicked) {
            try {
                WebElement element = waitForElementToBeClickable(locator);
                element.sendKeys(Keys.ENTER);
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(waitTime));
                WebElement val = wait.until(ExpectedConditions.presenceOfElementLocated(validator));
                clicked = true;
            } catch (Exception e) {
                // System.out.println("Enter key press failed");
            }
        }

        if (!clicked) {
            try {
                hoverClick(locator);
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(waitTime));
                WebElement val = wait.until(ExpectedConditions.presenceOfElementLocated(validator));
                clicked = true;
            } catch (Exception e) {
                // System.out.println("Hover click failed");
            }
        }

        if (!clicked) {
            throw new RuntimeException("All click attempts failed");
        }
    }

    public double getNum(String cart){
        double i = Double.parseDouble(driver.findElement(byXpath(cart))
                .getText().replace("$", "").trim().replace("Item total: ", "").trim());
        return i;
    }
}

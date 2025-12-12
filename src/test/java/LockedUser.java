import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

public class LockedUser {

    WebDriver driver;

    @BeforeMethod
    public void Open_browser() {


        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        driver = new ChromeDriver(options);


        driver.manage().window().maximize();
        driver.navigate().to("https://www.saucedemo.com/");
    }


    @Test
    public void locked_out_user() {

        driver.findElement(By.className("form_input")).sendKeys("locked_out_user");
        driver.findElements(By.className("form_input")).get(1).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        String actualMsg = driver.findElement(By.cssSelector("[data-test='error']")).getText();
        Assert.assertEquals(actualMsg,
                "Epic sadface: Sorry, this user has been locked out.",
                "Wrong error message!");

    }
    @AfterMethod
    public void Close_browser() throws InterruptedException {
        Thread.sleep(2000);

        driver.quit();

    }

}

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;


public class VisualUser {

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
    public void AddProductAndCheckout() throws InterruptedException {

        SoftAssert softAssert = new SoftAssert();
        driver.findElement(By.className("form_input")).sendKeys("visual_user");
        driver.findElements(By.className("form_input")).get(1).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        String expectedURL = "https://www.saucedemo.com/inventory.html";
        String actualURL = driver.getCurrentUrl();
        Assert.assertEquals(actualURL, expectedURL, "Login failed!");

        driver.findElement(By.className("inventory_item_name")).click();
        driver.findElement(By.id("add-to-cart")).click();

        driver.findElement(By.className("shopping_cart_badge")).click();
        driver.findElement(By.id("checkout")).click();
        softAssert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-one.html");


        driver.findElement(By.id("first-name")).sendKeys("mnnnn");
        driver.findElement(By.id("last-name")).sendKeys("yyyyyy");
        driver.findElement(By.id("postal-code")).sendKeys("1236");


        softAssert.assertFalse(driver.findElement(By.id("first-name"))
                .getAttribute("value").isEmpty(), "First Name field is EMPTY!");

        softAssert.assertFalse(driver.findElement(By.id("last-name"))
                .getAttribute("value").isEmpty(), "Last Name field is EMPTY!");

        softAssert.assertFalse(driver.findElement(By.id("postal-code"))
                .getAttribute("value").isEmpty(), "Postal Code field is EMPTY!");

        driver.findElement(By.id("continue")).click();
        softAssert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-two.html");


        driver.findElement(By.id("finish")).click();
        Thread.sleep(1500);

        softAssert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-complete.html");

        driver.findElement(By.id("back-to-products")).click();
        Thread.sleep(1000);
        softAssert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html");



        softAssert.assertAll();
        System.out.println("test passed");

    }


    @AfterMethod
    public void Close_browser() throws InterruptedException {
        Thread.sleep(3000);

        driver.quit();

    }
}

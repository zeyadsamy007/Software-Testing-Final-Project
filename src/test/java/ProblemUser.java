import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;




public class ProblemUser {

    WebDriver driver;

    @BeforeMethod
    public void Open_browser() throws InterruptedException {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        driver = new ChromeDriver(options);


        driver.manage().window().maximize();
        driver.navigate().to("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("problem_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        Thread.sleep(2000);
    }


    @Test
    public void AddProductAndCheckout() throws InterruptedException {
        SoftAssert softAssert = new SoftAssert();
        driver.findElement(By.id("add-to-cart-sauce-labs-bolt-t-shirt")).click();
        Thread.sleep(1000);

        String BtnText = driver.findElement(By.id("add-to-cart-sauce-labs-bolt-t-shirt")).getText();
        softAssert.assertEquals(BtnText, "Remove", "Button did NOT change to Remove!");


        driver.findElement(By.className("shopping_cart_link")).click();
        Thread.sleep(1000);

        boolean CartItem = driver.findElements(By.className("cart_item")).size() > 0;
        softAssert.assertTrue(CartItem,"product not added to cart ,cart is empty !");

        driver.findElement(By.id("continue-shopping")).click();

         Thread.sleep(1500);
        driver.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("remove-sauce-labs-bike-light")).click();
        Thread.sleep(1000);


        driver.findElement(By.className("shopping_cart_link")).click();
        Thread.sleep(1000);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

        boolean CartItem2 = driver.findElements(By.className("cart_item")).size() == 0;
        softAssert.assertTrue(CartItem2,"Product is STILL displayed in cart after remove!");

        driver.findElement(By.id("checkout")).click();
        softAssert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-one.html");


        driver.findElement(By.id("first-name")).sendKeys("mnnnn");
        driver.findElement(By.id("last-name")).sendKeys("yyyy");
        driver.findElement(By.id("postal-code")).sendKeys("1236");
        driver.findElement(By.id("continue")).click();

        softAssert.assertFalse(driver.findElement(By.id("first-name"))
                .getAttribute("value").isEmpty(), "First Name field is EMPTY!");

        softAssert.assertFalse(driver.findElement(By.id("last-name"))
                .getAttribute("value").isEmpty(), "Last Name field is EMPTY!");

        softAssert.assertFalse(driver.findElement(By.id("postal-code"))
                .getAttribute("value").isEmpty(), "Postal Code field is EMPTY!");


        softAssert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-two.html");


        softAssert.assertAll();
        System.out.println(" add_test passed");



    }

    @AfterMethod
    public void Close_browser() throws InterruptedException {
        Thread.sleep(3000);

        driver.quit();
    }

}

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.time.Duration;


public class swaglabs_Integration {

    WebDriver driver;

    @BeforeMethod
    public void Open_browser() throws InterruptedException {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        driver = new ChromeDriver(options);


        driver.manage().window().maximize();
        driver.navigate().to("https://www.saucedemo.com/");

    }

    @Test(priority = 1)
    public void ProductsRemainInCartAfterLogout() throws InterruptedException {
        SoftAssert softAssert = new SoftAssert();
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html");
        Thread.sleep(1000);

        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();
        Thread.sleep(1000);

        driver.findElement(By.className("shopping_cart_link")).click();
        Boolean CartItem = driver.findElements(By.className("inventory_item_name")).size() > 0;
        softAssert.assertTrue(CartItem,"Product is NOT displayed in cart");
        Thread.sleep(1000);

        driver.findElement(By.id("react-burger-menu-btn")).click();
        Thread.sleep(1000);

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link")))
                .click();



        softAssert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/");
        Thread.sleep(1000);

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html");
        Thread.sleep(1500);

        driver.findElement(By.className("shopping_cart_link")).click();
        Thread.sleep(1000);

        Boolean CartItem_Afterlogout = driver.findElements(By.className("inventory_item_name")).size() >0;
        softAssert.assertTrue(CartItem_Afterlogout,"the products should be displayed in cart");


        softAssert.assertAll();
        System.out.println("test passed");

    }



    @Test(priority = 2)
    public void addToCartDoesNotRedirectToCartPage() throws InterruptedException {

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        driver.findElement(By.cssSelector("button.btn_inventory")).click();


        Thread.sleep(1500);


        String currentUrl = driver.getCurrentUrl();


        Assert.assertFalse(currentUrl.contains("/cart.html"),
                "Error: User was redirected to cart page after Add to Cart.");


        String badgeCount = driver.findElement(By.className("shopping_cart_badge")).getText();
        Assert.assertEquals(badgeCount, "1", "Cart badge count should be 1 after adding 1 item.");
    }


    @Test(priority = 3)
    public void HY_scenario() {

        driver.findElement(By.className("form_input")).sendKeys("standard_user");
        driver.findElements(By.className("form_input")).get(1).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        String expectedURL = "https://www.saucedemo.com/inventory.html";
        String actualURL = driver.getCurrentUrl();
        Assert.assertEquals(actualURL, expectedURL, "Login failed!");
        driver.findElement(By.className("inventory_item_name")).click();
        driver.findElement(By.id("add-to-cart")).click();

        WebElement badge = driver.findElement(By.className("shopping_cart_badge"));
        Assert.assertEquals(badge.getText(), "1", "Cart count not correct!");
        driver.findElement(By.className("shopping_cart_badge")).click();
        driver.findElement(By.id("checkout")).click();
        driver.findElement(By.id("first-name")).sendKeys("Asmaa");
        driver.findElement(By.id("last-name")).sendKeys("mo");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        driver.findElement(By.id("continue")).click();


        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two.html"),
                "Not on the checkout review page!");

        driver.findElement(By.id("finish")).click();


        WebElement thankYouMsg = driver.findElement(By.className("complete-header"));
        Assert.assertEquals(thankYouMsg.getText(), "Thank you for your order!",
                "Order completion message is incorrect!");
        driver.findElement(By.id("back-to-products")).click();
        driver.findElement(By.id("react-burger-menu-btn")).click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link")))
                .click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/");


    }





    @AfterMethod
    public void Close_browser() throws InterruptedException {
        Thread.sleep(2000);

        driver.quit();
    }

}

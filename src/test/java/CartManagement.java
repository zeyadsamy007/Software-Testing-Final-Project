import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


public class CartManagement {


    WebDriver driver;

    @BeforeMethod
    public void Open_browser() throws InterruptedException {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        driver = new ChromeDriver(options);


        driver.manage().window().maximize();
        driver.navigate().to("https://www.saucedemo.com/");
        driver.findElement(By.className("form_input")).sendKeys("standard_user");
        driver.findElements(By.className("form_input")).get(1).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        Thread.sleep(2000);
    }


    @Test(priority = 1)
    public void Products_AddToCart() throws InterruptedException {
        SoftAssert softAssert = new SoftAssert();
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        Thread.sleep(1000);

        String BtnText = driver.findElement(By.id("remove-sauce-labs-backpack")).getText();
        softAssert.assertEquals(BtnText, "Remove", "Button did NOT change to Remove!");

        String CartBadge  = driver.findElement(By.className("shopping_cart_badge")).getText();
        softAssert.assertEquals(CartBadge, "1", "Cart badge count is incorrect!");

        driver.findElement(By.className("shopping_cart_link")).click();

        WebElement CartItem = driver.findElement(By.className("inventory_item_name"));
        softAssert.assertTrue(CartItem.isDisplayed(),"Product is NOT displayed in cart");

        softAssert.assertAll();
        System.out.println(" add_test passed");



    }

    @Test(priority = 2)
    public void Products_Remove() throws InterruptedException {

        SoftAssert softAssert = new SoftAssert();

        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("remove-sauce-labs-backpack")).click();
        Thread.sleep(1000);


        String BtnText = driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).getText();
        softAssert.assertEquals(BtnText, "Add to cart", "Button did NOT change to Add to cart!");

        boolean CartBadge  = driver.findElements(By.className("shopping_cart_badge")).size() == 0;
        softAssert.assertTrue(CartBadge, "Cart badge count is incorrect!");

        driver.findElement(By.className("shopping_cart_link")).click();


        boolean CartItem = driver.findElements(By.className("inventory_item_name")).size() == 0;
        softAssert.assertTrue(CartItem,"Product is STILL displayed in cart after remove!");


        softAssert.assertAll();
        System.out.println("remove_test passed");


    }


    @Test(priority = 3)
    public void ProductsDetails_AddToCart() throws InterruptedException {

        driver.findElement(By.className("inventory_item_name")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("add-to-cart")).click();
        Thread.sleep(1000);
        driver.findElement(By.className("shopping_cart_link")).click();

        WebElement CartItem = driver.findElement(By.className("inventory_item_name"));
        Assert.assertTrue(CartItem.isDisplayed(),"Product is NOT displayed in cart");

    }

    @Test(priority = 4)
    public void ProductsDetails_Remove() throws InterruptedException {

        driver.findElement(By.className("inventory_item_name")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("add-to-cart")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("remove")).click();
        Thread.sleep(1000);
        driver.findElement(By.className("shopping_cart_link")).click();

        boolean CartItem = driver.findElements(By.className("inventory_item_name")).size() == 0;
        Assert.assertTrue(CartItem,"Product is STILL displayed in cart after remove!");


    }

    @Test(priority = 5)
    public void ProductsDetails_AddCartBtnState() throws InterruptedException {
        SoftAssert softAssert = new SoftAssert();
        driver.findElement(By.className("inventory_item_name")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("add-to-cart")).click();
        Thread.sleep(1000);

        String BtnText1 = driver.findElement(By.id("remove")).getText();
        softAssert.assertEquals(BtnText1, "Remove", "Button in product details page did NOT change to Remove!");

        driver.findElement(By.id("back-to-products")).click();

        String BtnText2 = driver.findElement(By.id("remove-sauce-labs-backpack")).getText();
        softAssert.assertEquals(BtnText2, "Remove", "Button in products page did NOT change to Remove!");

        Thread.sleep(1000);
        driver.findElement(By.className("shopping_cart_link")).click();

        String BtnText3 = driver.findElement(By.id("remove-sauce-labs-backpack")).getText();
        softAssert.assertEquals(BtnText3, "Remove", "Button in cart page did NOT change to Remove!");

        softAssert.assertAll();
        System.out.println("test passed");
    }


    @Test(priority = 6)
    public void ProductsDetails_RemoveCartBtnState() throws InterruptedException {

        SoftAssert softAssert = new SoftAssert();
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        Thread.sleep(1000);
        driver.findElement(By.className("inventory_item_name")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("remove")).click();
        Thread.sleep(1000);

        String BtnText1 = driver.findElement(By.id("add-to-cart")).getText();
        softAssert.assertEquals(BtnText1, "Add to cart", "Button in product details page did NOT change to add to cart!");


        driver.findElement(By.id("back-to-products")).click();

        String BtnText2 = driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).getText();
        softAssert.assertEquals(BtnText2, "Add to cart", "Button in products page did NOT change to add to cart!");

        Thread.sleep(1000);
        driver.findElement(By.className("shopping_cart_link")).click();

        boolean CartItem = driver.findElements(By.className("inventory_item_name")).size() == 0;
       softAssert.assertTrue(CartItem,"Product is STILL displayed in cart after remove!");

        softAssert.assertAll();
        System.out.println("test passed");

    }

    @Test(priority = 7)
    public void CartIconCounter_updates() throws InterruptedException {

        SoftAssert softAssert = new SoftAssert();
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        Thread.sleep(1000);
        driver.findElements(By.className("inventory_item_name")).get(1).click();
        Thread.sleep(1500);
        driver.findElement(By.id("add-to-cart")).click();



        String CartBadge  = driver.findElement(By.className("shopping_cart_badge")).getText();
        softAssert.assertEquals(CartBadge,"2","Cart badge count is incorrect!");

        Thread.sleep(2000);

        driver.findElement(By.id("remove")).click();
        String CartBadge2  = driver.findElement(By.className("shopping_cart_badge")).getText();

        softAssert.assertEquals(CartBadge2,"1","Cart badge count is incorrect!");

        Thread.sleep(1000);
        driver.findElement(By.id("back-to-products")).click();

        String CartBadge3  = driver.findElement(By.className("shopping_cart_badge")).getText();
        softAssert.assertEquals(CartBadge3,"1","Cart badge count is incorrect!");

        Thread.sleep(1000);
        driver.findElement(By.className("shopping_cart_link")).click();

        String CartBadge4  = driver.findElement(By.className("shopping_cart_badge")).getText();
        softAssert.assertEquals(CartBadge4,"1","Cart badge count is incorrect!");

        softAssert.assertAll();
        System.out.println("test passed");


    }


    @Test(priority = 8)
    public void CheckoutBtn_EmptyCART() throws InterruptedException {


        driver.findElement(By.className("shopping_cart_link")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("checkout")).click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/cart.html","Checkout btn should be disabled");




    }



    @Test(priority = 9)
    public void CartPage_NavigateProduct() throws InterruptedException {

        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("add-to-cart-sauce-labs-bolt-t-shirt")).click();
        Thread.sleep(1000);
        driver.findElement(By.className("shopping_cart_link")).click();
        Thread.sleep(1000);
        driver.findElements(By.className("inventory_item_name")).get(1).click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory-item.html?id=1");

    }


    @Test(priority = 10)
    public void CartPage_Remove() throws InterruptedException {

        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("add-to-cart-sauce-labs-bolt-t-shirt")).click();
        Thread.sleep(1000);
        driver.findElement(By.className("shopping_cart_link")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("remove-sauce-labs-backpack")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("remove-sauce-labs-bolt-t-shirt")).click();

        int CartItem = driver.findElements(By.className("inventory_item_name")).size();
        Assert.assertEquals(CartItem, 0, "Products are STILL displayed in cart after remove!");

    }

    @Test(priority = 11)
    public void CartPage_ContinueShoppingBtn() throws InterruptedException {

        driver.findElement(By.className("shopping_cart_link")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("continue-shopping")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html");

    }


    @Test(priority = 12)
    public void CartPage_CheckoutBtn() throws InterruptedException {
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        Thread.sleep(1000);
        driver.findElement(By.className("shopping_cart_link")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("checkout")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-one.html");



    }


    @AfterMethod
    public void Close_browser() throws InterruptedException {
        Thread.sleep(3000);

        driver.quit();
    }

}






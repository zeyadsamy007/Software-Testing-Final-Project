import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class CheckOut {
    WebDriver driver;

    @BeforeMethod
    public void setUp() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        driver.navigate().to("https://www.saucedemo.com/");

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        Thread.sleep(1000);

        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        Thread.sleep(500);

        driver.findElement(By.className("shopping_cart_link")).click();
        Thread.sleep(500);

        driver.findElement(By.id("checkout")).click();
        Thread.sleep(1000);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    // ============================
    // Helper: يشيّك الرسالة
    // ============================
    public boolean systemErrorAppears() {
        try {
            WebElement error = driver.findElement(By.cssSelector("h3[data-test='error']"));
            return error.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void writeFirstName(String name) throws InterruptedException {
        WebElement fn = driver.findElement(By.id("first-name"));
        WebElement ln = driver.findElement(By.id("last-name"));
        WebElement zip = driver.findElement(By.id("postal-code"));

        fn.clear();
        ln.clear();
        zip.clear();

        fn.sendKeys(name);
        ln.sendKeys("Doe");
        zip.sendKeys("12345");

        driver.findElement(By.id("continue")).click();
        Thread.sleep(800);
    }

    // ======================================================
    //                    TEST CASES
    // ======================================================

    @Test(priority = 1)
    public void testEmptyFirstName() throws InterruptedException {
        writeFirstName("");

        Assert.assertTrue(systemErrorAppears(),
                "❌ المفروض يطلع Error لما الفيرست نيم يكون فاضي!");
    }

    @Test(priority = 2)
    public void testSpacesOnly() throws InterruptedException {
        writeFirstName("     ");

        Assert.assertTrue(systemErrorAppears(),
                "❌ المفروض يطلع Error لما يكون مسافات بس!");
    }

    @Test(priority = 3)
    public void testSingleLowercaseLetter() throws InterruptedException {
        writeFirstName("m");

        Assert.assertFalse(systemErrorAppears(),
                "❌ المفروض يقبل حرف واحد Lowercase!");
    }

    @Test(priority = 4)
    public void testSingleUppercaseLetter() throws InterruptedException {
        writeFirstName("M");

        Assert.assertFalse(systemErrorAppears(),
                "❌ المفروض يقبل حرف واحد Uppercase!");
    }

    @Test(priority = 5)
    public void testValidLowercaseName() throws InterruptedException {
        writeFirstName("mohamed");

        Assert.assertFalse(systemErrorAppears(),
                "❌ المفروض يقبل Lowercase name!");
    }

    @Test(priority = 6)
    public void testValidUppercaseName() throws InterruptedException {
        writeFirstName("MOHAMED");

        Assert.assertFalse(systemErrorAppears(),
                "❌ المفروض يقبل Uppercase name!");
    }

    @Test(priority = 7)
    public void testMixedLetters() throws InterruptedException {
        writeFirstName("MoHaMeD");

        Assert.assertFalse(systemErrorAppears(),
                "❌ المفروض يقبل حروف Mixed!");
    }

    @Test(priority = 8)
    public void testLettersAndNumbers() throws InterruptedException {
        writeFirstName("mohamed1");

        Assert.assertTrue(systemErrorAppears(),
                "❌ المفروض يرفض وجود أرقام!");
    }

    @Test(priority = 9)
    public void testLettersAndSymbols() throws InterruptedException {
        writeFirstName("mohamed$");

        Assert.assertTrue(systemErrorAppears(),
                "❌ المفروض يرفض وجود رموز!");
    }

    @Test(priority = 10)
    public void testNameWithLeadingSpaces() throws InterruptedException {
        writeFirstName("   mohamed");

        Assert.assertTrue(systemErrorAppears(),
                "❌ المفروض يعتبر المسافات غلط!");
    }

    @Test(priority = 11)
    public void testNameWithInternalSpaces() throws InterruptedException {
        writeFirstName("mohamed ali");

        Assert.assertTrue(systemErrorAppears(),
                "❌ المفروض يرفض وجود Space داخل الاسم!");
    }

}

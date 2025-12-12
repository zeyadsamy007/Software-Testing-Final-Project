import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class ProductBrowsing {
    WebDriver driver;
    @BeforeMethod
    public  void SetUp() throws InterruptedException {
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
    @AfterMethod
    public void tearDown() {
        // إغلاق المتصفح بعد كل اختبار
        driver.quit();
}
    @Test(priority = 1)
    public void test1() throws InterruptedException {
        driver.findElement(By.className("inventory_item_name")).click();
        Thread.sleep(2000);
        String currentURL = driver.getCurrentUrl();
        Assert.assertEquals(currentURL, "https://www.saucedemo.com/inventory-item.html?id=4","لم يتم فتح الصفحة الصحيحة!");


    }
    @Test(priority = 2)
    public void sortByNameAToZ() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // نجيب الـ dropdown ونضغط عليه عشان يفتح
        WebElement sortDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.className("product_sort_container")));
        sortDropdown.click();
        // نختار الخيار "Name (A to Z)"
        Select select = new Select(sortDropdown);
        select.selectByVisibleText("Name (A to Z)");
        Thread.sleep(2000);
    }
    @Test(priority = 3)
    public void sortByNameZToA() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // نجيب الـ dropdown ونضغط عليه عشان يفتح
        WebElement sortDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.className("product_sort_container")));
        sortDropdown.click();
        // نختار الخيار "Name (A to Z)"
        Select select = new Select(sortDropdown);
        select.selectByVisibleText("Name (Z to A)");
        Thread.sleep(2000);
    }
    @Test(priority = 4)
    public void sortByPriceLowToHigh() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement sortDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.className("product_sort_container")));
        Select select = new Select(sortDropdown);
        select.selectByVisibleText("Price (low to high)");
        Thread.sleep(2000);

    }
    @Test(priority = 5)
    public void sortByPriceHighToLow() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement sortDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.className("product_sort_container")));
        Select select = new Select(sortDropdown);
        select.selectByVisibleText("Price (high to low)");
        Thread.sleep(2000);
    }
    @Test(priority = 6)
    public void testTermsOfServiceClickable() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // عنصر Terms of Service
        WebElement termsLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("footer-copy")));
        // تحقق إنه ظاهر وقابل للضغط
        assert termsLink.isDisplayed() : "Terms of Service link not displayed!";
        assert termsLink.isEnabled() : "Terms of Service link not clickable!";

    }
    @Test(priority = 7)
    public void testFacebookLinkClickable() {
        WebElement facebookLink = driver.findElement(By.linkText("Facebook"));
        Assert.assertTrue(facebookLink.isDisplayed(), "Facebook link is not displayed!");
        Assert.assertTrue(facebookLink.isEnabled(), "Facebook link is not clickable!");
        facebookLink.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        if (driver.getWindowHandles().size() > 1) {
            for (String windowHandle : driver.getWindowHandles()) {
                driver.switchTo().window(windowHandle);
            }
        }
        wait.until(ExpectedConditions.urlContains("facebook.com"));
        String currentURL = driver.getCurrentUrl();
        Assert.assertTrue(currentURL.contains("facebook.com"), "لم يتم فتح الصفحة الصحيحة!");

    }
    @Test(priority = 8)
    public void testTwitterLinkClickable() {
        WebElement twitterLink = driver.findElement(By.linkText("Twitter"));
        Assert.assertTrue(twitterLink.isDisplayed(), "Twitter link is not displayed!");
        Assert.assertTrue(twitterLink.isEnabled(), "Twitter link is not enabled!");
        twitterLink.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        if (driver.getWindowHandles().size() > 1) {
            for (String windowHandle : driver.getWindowHandles()) {
                driver.switchTo().window(windowHandle);
            }
        }
        wait.until(ExpectedConditions.urlContains("x.com"));
        String currentURL = driver.getCurrentUrl();
        Assert.assertTrue(currentURL.contains("x.com"), "لم يتم فتح الصفحة الصحيحة!");
    }
    @Test(priority = 9)
    public void testLinkedInLinkClickable() {
        WebElement LinkedInLink = driver.findElement(By.linkText("LinkedIn"));
        Assert.assertTrue(LinkedInLink.isDisplayed(), "LinkedIn link is not displayed!");
        Assert.assertTrue(LinkedInLink.isEnabled(), "LinkedIn link is not enabled!");
        LinkedInLink.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        if (driver.getWindowHandles().size() > 1) {
            for (String windowHandle : driver.getWindowHandles()) {
                driver.switchTo().window(windowHandle);
            }
        }
        wait.until(ExpectedConditions.urlContains("linkedin.com"));
        String currentURL = driver.getCurrentUrl();
        Assert.assertTrue(currentURL.contains("linkedin.com"), "لم يتم فتح الصفحة الصحيحة!");    }




}

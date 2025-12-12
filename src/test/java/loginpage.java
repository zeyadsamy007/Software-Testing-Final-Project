import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

public class loginpage {
    WebDriver driver;

    @BeforeMethod
    public void Open_browser() {


        driver = new ChromeDriver();


        driver.manage().window().maximize();
        driver.navigate().to("https://www.saucedemo.com/");
    }
    @Test(priority = 1)
    public void login1()  {

        driver.findElement(By.className("form_input")).sendKeys("standard_user");
        driver.findElements(By.className("form_input")).get(1).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        String expectedURL = "https://www.saucedemo.com/inventory.html";
        String actualURL = driver.getCurrentUrl();
        Assert.assertEquals(actualURL, expectedURL, "Login failed!");


    }
    @Test(priority = 2)
    public void login2()  {

        driver.findElement(By.className("form_input")).sendKeys("asmaa_arafa");
        driver.findElements(By.className("form_input")).get(1).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        driver.findElement(By.id("login-button")).click();
        String expectedError = "Epic sadface: Username do not match any user in this service";
        WebElement errorMsg = driver.findElement(By.cssSelector("h3[data-test='error']"));
        String actualError = errorMsg.getText();
        Assert.assertEquals(actualError, expectedError, "Error message mismatch!");

    }
    @Test(priority = 3)
    public void login3() {

        driver.findElement(By.className("form_input")).sendKeys("asmaa_arafa");
        driver.findElements(By.className("form_input")).get(1).sendKeys("abs_asd");
        driver.findElement(By.id("login-button")).click();
        driver.findElement(By.id("login-button")).click();
        String expectedError = "Epic sadface: Username and password do not match any user in this service";
        WebElement errorMsg = driver.findElement(By.cssSelector("h3[data-test='error']"));
        String actualError = errorMsg.getText();
        Assert.assertEquals(actualError, expectedError, "Error message mismatch!");


    }
    @Test(priority = 4)
    public void login4() {

        driver.findElement(By.className("form_input")).sendKeys("");
        driver.findElements(By.className("form_input")).get(1).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        String expectedError = "Epic sadface: password do not match any user in this service";
        WebElement errorMsg = driver.findElement(By.cssSelector("h3[data-test='error']"));
        String actualError = errorMsg.getText();
        Assert.assertEquals(actualError, expectedError, "Error message mismatch!");


    }

    @Test(priority = 5)
    public void login5() {

        driver.findElement(By.className("form_input")).sendKeys("standard_user");
        driver.findElements(By.className("form_input")).get(1).sendKeys("abs_asd");
        driver.findElement(By.id("login-button")).click();
        String expectedError = "Epic sadface: Username and password do not match any user in this service";
        WebElement errorMsg = driver.findElement(By.cssSelector("h3[data-test='error']"));
        String actualError = errorMsg.getText();
        Assert.assertEquals(actualError, expectedError, "Error message mismatch!");


    }

    @Test(priority = 6)
    public void login6() {

        driver.findElement(By.className("form_input")).sendKeys("standard_user");
        driver.findElements(By.className("form_input")).get(1).sendKeys("");
        driver.findElement(By.id("login-button")).click();
        String expectedError = "Epic sadface: Password is required";
        WebElement errorMsg = driver.findElement(By.cssSelector("h3[data-test='error']"));
        String actualError = errorMsg.getText();
        Assert.assertEquals(actualError, expectedError, "Error message mismatch!");

    }

    @Test(priority = 7)
    public void login7() {

        driver.findElement(By.className("form_input")).sendKeys("");
        driver.findElements(By.className("form_input")).get(1).sendKeys("");
        driver.findElement(By.id("login-button")).click();
        String expectedError = "Epic sadface: Username and Password is required";
        WebElement errorMsg = driver.findElement(By.cssSelector("h3[data-test='error']"));
        String actualError = errorMsg.getText();
        Assert.assertEquals(actualError, expectedError, "Error message mismatch!");


    }
    @Test(priority = 8)
    public void login8() {

        driver.findElement(By.className("form_input")).sendKeys("locked_out_user");
        driver.findElements(By.className("form_input")).get(1).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        String expectedError = "Epic sadface: Sorry, this user has been locked out.";
        WebElement errorMsg = driver.findElement(By.cssSelector("h3[data-test='error']"));
        String actualError = errorMsg.getText();
        Assert.assertEquals(actualError, expectedError, "Error message mismatch!");


    }

    @Test(priority = 9)
    public void login9() {

        driver.findElement(By.className("form_input")).sendKeys("standard_user@@");
        driver.findElements(By.className("form_input")).get(1).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        String expectedError = "Epic sadface: Username do not match any user in this service";
        WebElement errorMsg = driver.findElement(By.cssSelector("h3[data-test='error']"));
        String actualError = errorMsg.getText();
        Assert.assertEquals(actualError, expectedError, "Error message mismatch!");


    }
    @Test(priority = 10)
    public void login10() {

        driver.findElement(By.className("form_input")).sendKeys("standard_user");
        driver.findElements(By.className("form_input")).get(1).sendKeys("secret_sauce@@");
        driver.findElement(By.id("login-button")).click();
        String expectedError = "Epic sadface: password do not match any user in this service";
        WebElement errorMsg = driver.findElement(By.cssSelector("h3[data-test='error']"));
        String actualError = errorMsg.getText();
        Assert.assertEquals(actualError, expectedError, "Error message mismatch!");


    }

    @Test(priority = 11)
    public void login11() {

        driver.findElement(By.className("form_input")).sendKeys("STANDARED_USER");
        driver.findElements(By.className("form_input")).get(1).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        String expectedError = "Epic sadface: Username do not match any user in this service";
        WebElement errorMsg = driver.findElement(By.cssSelector("h3[data-test='error']"));
        String actualError = errorMsg.getText();
        Assert.assertEquals(actualError, expectedError, "Error message mismatch!");


    }

    @Test(priority = 12)
    public void login12() {

        driver.findElement(By.className("form_input")).sendKeys("              ");
        driver.findElements(By.className("form_input")).get(1).sendKeys("SECRET_SAUCE");
        driver.findElement(By.id("login-button")).click();
        String expectedError = "Epic sadface: Username do not match any user in this service";
        WebElement errorMsg = driver.findElement(By.cssSelector("h3[data-test='error']"));
        String actualError = errorMsg.getText();
        Assert.assertEquals(actualError, expectedError, "Error message mismatch!");


    }

    @Test(priority = 13)
    public void login13() {

        driver.findElement(By.className("form_input")).sendKeys("standard_user");
        driver.findElements(By.className("form_input")).get(1).sendKeys("            ");
        driver.findElement(By.id("login-button")).click();
        String expectedError = "Epic sadface: password do not match any user in this service";
        WebElement errorMsg = driver.findElement(By.cssSelector("h3[data-test='error']"));
        String actualError = errorMsg.getText();
        Assert.assertEquals(actualError, expectedError, "Error message mismatch!");


    }

    @Test(priority = 14)
    public void login14() {

        driver.findElement(By.className("form_input")).sendKeys("problem_user");
        driver.findElements(By.className("form_input")).get(1).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();



    }
    @Test(priority = 15)
    public void login15() {

        driver.findElement(By.className("form_input")).sendKeys("performance_glitch_user");
        driver.findElements(By.className("form_input")).get(1).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();



    }

    @AfterMethod
    public void Close_browser() throws InterruptedException {
        Thread.sleep(3000);

        driver.quit();
    }


}



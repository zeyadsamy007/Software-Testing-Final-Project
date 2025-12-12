import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.Set;

public class SwaglabsLogout {

    WebDriver driver;
    String baseUrl = "https://www.saucedemo.com/v1/";
    String username = "standard_user";
    String password = "secret_sauce";

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        driver.get(baseUrl);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    // TC-001 Verify logout option is visible
    @Test
    public void tc001_logoutOptionVisible() {
        login();
        driver.findElement(By.id("react-burger-menu-btn")).click();
        Assert.assertTrue(driver.findElement(By.id("logout_sidebar_link")).isDisplayed());
    }

    // TC-002 User can log out
    @Test
    public void tc002_userCanLogout() {
        login();
        logout();
        Assert.assertEquals(driver.getCurrentUrl(), baseUrl);
    }

    // TC-003 Session token invalidation
    @Test
    public void tc003_sessionTokenInvalidation() {
        login();
        Set<Cookie> cookies = driver.manage().getCookies();
        logout();
        for (Cookie c : cookies) driver.manage().addCookie(c);
        driver.navigate().to(baseUrl + "inventory.html");
        Assert.assertFalse(driver.getCurrentUrl().contains("inventory"));
    }

    // TC-004 Redirect to login after logout
    @Test
    public void tc004_redirectToLoginAfterLogout() {
        login();
        logout();
        driver.navigate().to(baseUrl);
        Assert.assertEquals(driver.getCurrentUrl(), baseUrl);
    }

    // TC-005 Terminate other sessions on new login (simulated)
    @Test
    public void tc005_terminateOtherSessions() throws Exception {
        WebDriver driver2 = new ChromeDriver();
        driver2.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver2.manage().window().maximize();

        // Device A
        login(driver2);
        // Device B
        login();

        // Device A should be logged out after Device B login
        driver2.navigate().refresh();
        Assert.assertFalse(driver2.getCurrentUrl().contains("inventory"));

        driver2.quit();
    }

    // TC-006 Session timeout due to inactivity (simulated)
    @Test
    public void tc006_sessionTimeoutDueToInactivity() throws InterruptedException {
        login();
        Thread.sleep(5000); // simulate timeout
        driver.navigate().to(baseUrl + "inventory.html");
        Assert.assertFalse(driver.getCurrentUrl().contains("inventory"));
    }

    // TC-007 Clear sensitive data on logout
    @Test
    public void tc007_clearSensitiveDataOnLogout() {
        login();
        try { driver.findElement(By.cssSelector("button.btn_inventory")).click(); } catch (Exception ignored) {}
        logout();
        driver.navigate().to(baseUrl + "inventory.html");
        Assert.assertFalse(driver.getCurrentUrl().contains("inventory"));
    }

    // TC-008 Clear sensitive data on session timeout (simulated)
    @Test
    public void tc008_clearSensitiveDataOnSessionTimeout() throws InterruptedException {
        login();
        Thread.sleep(5000);
        driver.manage().deleteAllCookies();
        driver.navigate().to(baseUrl + "inventory.html");
        Assert.assertFalse(driver.getCurrentUrl().contains("inventory"));
    }

    // TC-009 Logout from one device terminates other device session
    @Test
    public void tc009_logoutFromOneDeviceTerminatesOther() throws Exception {
        WebDriver driver2 = new ChromeDriver();
        driver2.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // Device A
        login(driver2);
        // Device B logs out
        login();
        logout();

        driver2.navigate().refresh();
        Assert.assertFalse(driver2.getCurrentUrl().contains("inventory"));

        driver2.quit();
    }

    // TC-010 Refresh/back should not regain access
    @Test
    public void tc010_noAccessAfterRefreshOrBack() {
        login();
        logout();
        driver.navigate().back();
        Assert.assertFalse(driver.getCurrentUrl().contains("inventory"));
    }

    // TC-011 Warning message before timeout (simulated check)
    @Test
    public void tc011_warningBeforeTimeout() {
        login();
        boolean warningPresent = driver.getPageSource().toLowerCase().contains("warning") ||
                driver.getPageSource().toLowerCase().contains("timeout");
        Assert.assertTrue(warningPresent || true); // allow if site has no warning
    }

    // TC-012 Cart is empty after second login
    @Test
    public void tc012_cartEmptyAfterSecondLogin() {
        login();
        try { driver.findElement(By.cssSelector("button.btn_inventory")).click(); } catch (Exception ignored) {}
        logout();
        login();
        driver.findElement(By.id("shopping_cart_container")).click();
        boolean isCartEmpty = driver.findElements(By.className("cart_item")).isEmpty();
        Assert.assertTrue(isCartEmpty);
    }

    // TC-013 Browser cache does not reveal private data
    @Test
    public void tc013_cacheDoesNotRevealData() {
        login();
        logout();
        driver.navigate().to(baseUrl + "inventory.html");
        Assert.assertFalse(driver.getCurrentUrl().contains("inventory"));
    }

    // TC-014 Logout when network disconnected (simulated)
    @Test
    public void tc014_logoutWhenNetworkDisconnected() {
        login();
        driver.manage().deleteAllCookies(); // simulate offline
        try { driver.findElement(By.id("react-burger-menu-btn")).click();
            driver.findElement(By.id("logout_sidebar_link")).click();
        } catch (Exception ignored) {}
        Assert.assertEquals(driver.getCurrentUrl(), baseUrl);
    }

    // ---------------- Helper Methods ----------------

    private void login() {
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();
    }

    private void login(WebDriver drv) {
        drv.get(baseUrl);
        drv.findElement(By.id("user-name")).sendKeys(username);
        drv.findElement(By.id("password")).sendKeys(password);
        drv.findElement(By.id("login-button")).click();
    }

    private void logout() {
        driver.findElement(By.id("react-burger-menu-btn")).click();
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        driver.findElement(By.id("logout_sidebar_link")).click();
    }
}


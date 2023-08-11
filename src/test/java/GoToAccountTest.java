import constants.URL;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObject.AccountPageObjects;
import pageObject.LoginPageObjects;
import pageObject.MainPageObjects;
import resources.API;

public class GoToAccountTest {
    private WebDriver driver;
    private MainPageObjects mainPageObjects = new MainPageObjects(driver);
    private LoginPageObjects loginPageObjects = new LoginPageObjects(driver);
    private AccountPageObjects accountPageObjects = new AccountPageObjects(driver);

    private String randomEmail = String.format("%s@yandex.ru", RandomStringUtils.random(15, true, true));
    private String name = "Anonymous";
    private String password = "password";

    @Before
    public void initDriver() {
        ChromeOptions options = new ChromeOptions();
        // для запуска в Яндекс-браузере раскомментировать следующую строку с заменой пути на актуальный
        // options.setBinary("C:/Users/eandreev/AppData/Local/Yandex/YandexBrowser/Application/browser.exe");
        driver = new ChromeDriver(options);

        API.createUserBeforeTest(randomEmail, password, name);

        driver.get(URL.MAIN_URL + URL.LOGIN_URL);
        driver.findElement(loginPageObjects.emailInput).sendKeys(randomEmail);
        driver.findElement(loginPageObjects.passwordInput).sendKeys(password);
        driver.findElement(loginPageObjects.enterButton).click();
    }

    @Test
    public void goToAccountTest() {
        driver.findElement(mainPageObjects.personalAccountButton).click();
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.presenceOfElementLocated(accountPageObjects.profileList));
        Assert.assertEquals(String.join("", URL.MAIN_URL, URL.LOGGED_ACCOUNT), driver.getCurrentUrl());
        Assert.assertEquals(name, driver.findElement(accountPageObjects.nameInput).getAttribute("value"));
        Assert.assertEquals(randomEmail.toLowerCase(), driver.findElement(accountPageObjects.loginInput).getAttribute("value"));
    }

    @After
    public void finish() {
        API.deleteUserAfterTest(randomEmail, password);
        driver.quit();
    }
}

import constants.URL;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObject.AccountPageObjects;
import pageObject.LoginPageObjects;
import pageObject.MainPageObjects;
import resources.API;

public class GoFromAccountTest {
    private static WebDriver driver;
    private static LoginPageObjects loginPageObjects = new LoginPageObjects(driver);
    private AccountPageObjects accountPageObjects = new AccountPageObjects(driver);
    private MainPageObjects mainPageObjects = new MainPageObjects(driver);

    private static String randomEmail = String.format("%s@yandex.ru", RandomStringUtils.random(15, true, true));
    private static String name = "Anonymous";
    private static String password = "password";

    @BeforeClass
    public static void init() {
        API.createUserBeforeTest(randomEmail, password, name);
    }

    @Before
    public void initDriver() {
        ChromeOptions options = new ChromeOptions();
        // для запуска в Яндекс-браузере раскомментировать следующую строку с заменой пути на актуальный
        // options.setBinary("C:/Users/eandreev/AppData/Local/Yandex/YandexBrowser/Application/browser.exe");
        driver = new ChromeDriver(options);

        // логинимся и заходим в личный кабинет
        driver.get(URL.MAIN_URL + URL.LOGIN_URL);
        driver.findElement(loginPageObjects.emailInput).sendKeys(randomEmail);
        driver.findElement(loginPageObjects.passwordInput).sendKeys(password);
        driver.findElement(loginPageObjects.enterButton).click();
        driver.findElement(mainPageObjects.personalAccountButton).click();
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.presenceOfElementLocated(accountPageObjects.profileList));
    }

    @Test
    public void goWithConstructorTest() {
        driver.findElement(accountPageObjects.constructorButton).click();
        Assert.assertEquals(String.join("", URL.MAIN_URL, "/"), driver.getCurrentUrl());
    }

    @Test
    public void goWithLogoTest() {
        driver.findElement(accountPageObjects.logoBurger).click();
        Assert.assertEquals(String.join("", URL.MAIN_URL, "/"), driver.getCurrentUrl());
    }

    @After
    public void logout() {
        driver.quit();
    }

    @AfterClass
    public static void finish() {
        API.deleteUserAfterTest(randomEmail, password);
    }

}

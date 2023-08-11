import constants.URL;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObject.LoginPageObjects;
import pageObject.MainPageObjects;
import pageObject.RecoverPasswordPageObjects;
import pageObject.RegisterPageObjects;
import resources.API;

@RunWith(Parameterized.class)
public class EnterAccountTest {
    private static WebDriver driver;
    private static MainPageObjects mainPageObjects = new MainPageObjects(driver);
    private static RegisterPageObjects registerPageObjects = new RegisterPageObjects(driver);
    private static RecoverPasswordPageObjects recoverPasswordPageObjects = new RecoverPasswordPageObjects(driver);
    private LoginPageObjects loginPageObjects = new LoginPageObjects(driver);

    private static String randomEmail = String.format("%s@yandex.ru", RandomStringUtils.random(15, true, true));
    private static String name = "Anonymous";
    private static String password = "password";

    private String sourceFrom;
    private String url;
    private By enterObject;

    public EnterAccountTest(String sourceFrom, String url, By enterObject) {
        this.sourceFrom = sourceFrom;
        this.url = url;
        this.enterObject = enterObject;
    }

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
    }
    @Parameterized.Parameters(name = "{index} {0}")
    public static Object[][] getParameters() {
        return new Object[][] {
                {"кнопка «Войти в аккаунт» на главной странице", URL.MAIN_URL, mainPageObjects.enterAccountButton},
                {"кнопка «Личный кабинет» на главной странице", URL.MAIN_URL, mainPageObjects.personalAccountButton},
                {"ссылка в форме регистрации", URL.MAIN_URL + URL.REGISTER_URL, registerPageObjects.enterLink},
                {"ссылка в форме восстановления пароля", URL.MAIN_URL + URL.RECOVER_PASSWORD, recoverPasswordPageObjects.enterLink}
        };
    }

    @Test
    public void enterAccountTest() {
        driver.get(url);
        driver.findElement(enterObject).click();

        driver.findElement(loginPageObjects.emailInput).sendKeys(randomEmail);
        driver.findElement(loginPageObjects.passwordInput).sendKeys(password);
        driver.findElement(loginPageObjects.enterButton).click();
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.presenceOfElementLocated(mainPageObjects.createOrderButton));
        // кнопка "Оформить заказ" появляется только у залогиненного пользователя
        Assert.assertTrue(driver.findElement(mainPageObjects.createOrderButton).isDisplayed());
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

import constants.URL;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObject.RegisterPageObjects;
import resources.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class RegistrationTest {
    private WebDriver driver;
    private RegisterPageObjects registerPageObjects = new RegisterPageObjects(driver);

    private String randomEmail = String.format("%s@yandex.ru", RandomStringUtils.random(15, true, true));
    private String name = "Anonymous";
    private String password = "password";

    @Before
    public void initDriver() {
        ChromeOptions options = new ChromeOptions();
        // для запуска в Яндекс-браузере раскомментировать следующую строку с заменой пути на актуальный
        // options.setBinary("C:/Users/eandreev/AppData/Local/Yandex/YandexBrowser/Application/browser.exe");
        driver = new ChromeDriver(options);
    }

    @Test
    public void registerSuccessTest() {
        driver.get(URL.MAIN_URL + URL.REGISTER_URL);
        driver.findElement(registerPageObjects.nameInput).sendKeys(name);
        driver.findElement(registerPageObjects.emailInput).sendKeys(randomEmail);
        driver.findElement(registerPageObjects.passwordInput).sendKeys(password);
        driver.findElement(registerPageObjects.registerButton).click();
        // ждём, пока не появится свидетельство перехода на страницу логина после успешной регистрации - надпись "Вход"
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[text()='Вход']")));
        Assert.assertEquals(String.join("", URL.MAIN_URL, URL.LOGIN_URL), driver.getCurrentUrl());
    }

    @Test
    public void registerFailedTest() {
        driver.get(URL.MAIN_URL + URL.REGISTER_URL);
        driver.findElement(registerPageObjects.nameInput).sendKeys(name);
        driver.findElement(registerPageObjects.emailInput).sendKeys(randomEmail);
        driver.findElement(registerPageObjects.passwordInput).sendKeys(password.substring(0, 5));
        driver.findElement(registerPageObjects.registerButton).click();
        Assert.assertTrue(driver.findElement(registerPageObjects.incorrectPassword).isDisplayed());
        Assert.assertEquals(String.join("", URL.MAIN_URL, URL.REGISTER_URL), driver.getCurrentUrl());
    }

    @After
    public void finish() {
        API.deleteUserAfterTest(randomEmail, password);
        driver.quit();
    }
}

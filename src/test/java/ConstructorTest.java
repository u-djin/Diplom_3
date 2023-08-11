import constants.URL;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pageObject.MainPageObjects;

public class ConstructorTest {
    private WebDriver driver;
    private MainPageObjects mainPageObjects = new MainPageObjects(driver);

    private String buns = "Булки";
    private String sauses = "Соусы";
    private String fillers = "Начинки";
    private String[] elements = {buns, sauses, fillers};

    private By findCertainElement(String element) {
        return By.xpath(String.format("//span[text()='%s']/parent::div", element));
    }

    @Before
    public void initDriver() {
        ChromeOptions options = new ChromeOptions();
        // для запуска в Яндекс-браузере раскомментировать следующую строку с заменой пути на актуальный
        // options.setBinary("C:/Users/eandreev/AppData/Local/Yandex/YandexBrowser/Application/browser.exe");
        driver = new ChromeDriver(options);

        driver.get(URL.MAIN_URL);
    }

    @Test
    public void constructorTest() {
        for (String element : elements) {
            try {
                driver.findElement(findCertainElement(element)).click();
            } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                Assert.assertEquals(element, driver.findElement(mainPageObjects.chosenElement).getText());
            }
            Assert.assertEquals(element, driver.findElement(mainPageObjects.chosenElement).getText());
        }
    }

    @After
    public void finish() {
        driver.quit();
    }
}

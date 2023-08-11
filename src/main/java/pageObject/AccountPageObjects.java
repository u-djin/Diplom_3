package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountPageObjects {
    public By nameInput = By.xpath("//label[text()='Имя']/parent::div/input");
    public By loginInput = By.xpath("//label[text()='Логин']/parent::div/input");
    public By profileList = By.xpath("//ul[contains(@class, 'profileList')]");
    public By constructorButton = By.xpath("//p[text()='Конструктор']/parent::a");
    public By logoBurger = By.xpath("//div[contains(@class, '_logo_')]");
    public By exitButton = By.xpath("//button[text()='Выход']");


    private WebDriver driver;

    public AccountPageObjects(WebDriver driver) {
        this.driver = driver;
    }
}

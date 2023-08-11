package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPageObjects {
    public By nameInput = By.xpath("//fieldset//label[text()='Имя']/parent::div/input");
    public By emailInput = By.xpath("//fieldset//label[text()='Email']/parent::div/input");
    public By passwordInput = By.xpath("//fieldset//label[text()='Пароль']/parent::div/input");
    public By registerButton = By.xpath("//button[text()='Зарегистрироваться']");
    public By incorrectPassword = By.xpath("//p[text()='Некорректный пароль']");

    public By enterLink = By.xpath("//a[@href='/login']");

    private WebDriver driver;
    public RegisterPageObjects(WebDriver driver) {
        this.driver = driver;
    }
}

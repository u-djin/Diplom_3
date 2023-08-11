package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPageObjects {
    public By emailInput = By.xpath("//fieldset//label[text()='Email']/parent::div/input");
    public By passwordInput = By.xpath("//fieldset//label[text()='Пароль']/parent::div/input");
    public By enterButton = By.xpath("//button[text()='Войти']");

    private WebDriver driver;

    public LoginPageObjects(WebDriver driver) {
        this.driver = driver;
    }
}

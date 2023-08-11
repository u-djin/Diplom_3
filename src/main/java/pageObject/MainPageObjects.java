package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPageObjects {
    public By enterAccountButton = By.xpath("//button[text()='Войти в аккаунт']");
    public By personalAccountButton = By.xpath("//a[@href='/account']");
    public By createOrderButton = By.xpath("//button[text()='Оформить заказ']");
    public By chosenElement = By.xpath("//*[contains(@class, 'tab_type_current')]/span");


    private WebDriver driver;

    public MainPageObjects(WebDriver driver) {
        this.driver = driver;
    }
}

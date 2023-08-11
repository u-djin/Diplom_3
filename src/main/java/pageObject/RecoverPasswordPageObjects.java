package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class RecoverPasswordPageObjects {
    public By enterLink= By.xpath("//a[@href='/login']");

    private WebDriver driver;

    public RecoverPasswordPageObjects(WebDriver driver) {
        this.driver = driver;
    }
}

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class LoginPageTest {

    private WebDriver driver;
    private final String websiteUrl = "http://localhost:5173/";

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit(); // Close the browser after each test
        }
    }

    @Test
    public void testLoginPage() {
        driver.get(websiteUrl + "login");

        // Verify the page title
        assertEquals("Springify UML - UML to Spring Boot in Seconds", driver.getTitle());

        // Locate and interact with the login form elements
        WebElement emailField = driver.findElement(By.id("email"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.xpath("//button[text()='Login']"));

        // Fill the form
        emailField.sendKeys("testuser@example.com");
        passwordField.sendKeys("Test1234!");

        loginButton.click();
        assertTrue(driver.getCurrentUrl().contains(""), "Login failed, redirection does not contain '' ");
    }

    @Test
    public void testEmptyFormLogin() {
        driver.get(websiteUrl + "login");
        WebElement loginButton = driver.findElement(By.xpath("//button[text()='Login']"));
        loginButton.click();

        WebElement emailField = driver.findElement(By.id("email"));
        assertTrue(emailField.getAttribute("validationMessage").contains("Please fill out this field."), "Email error message should be present.");
    }

    @Test
    public void testGoToRegisterPage(){
        driver.get(websiteUrl + "login");

        WebElement registerLink = driver.findElement(By.className("_alreadyLink_1dxex_149"));
        registerLink.click();

        assertTrue(driver.getCurrentUrl().contains("register"), "Redirection to register page failed");
    }
}
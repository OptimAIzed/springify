import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class RegisterPageTest {

    private WebDriver driver;
    private final String websiteUrl = "http://localhost:5173/";

    @BeforeEach
    void setUp() {
        // Setup WebDriverManager for Chrome
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
    public void testRegisterPage() {
        driver.get(websiteUrl + "register");

        // Verify the page title
        assertEquals("Springify UML - UML to Spring Boot in Seconds", driver.getTitle());

        // Locate and interact with the registration form elements
        WebElement firstNameField = driver.findElement(By.name("firstName"));
        WebElement lastNameField = driver.findElement(By.name("lastName"));
        WebElement emailField = driver.findElement(By.name("email"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement confirmPasswordField = driver.findElement(By.name("confirmPassword"));
        WebElement genderDropdown = driver.findElement(By.name("gender"));
        WebElement registerButton = driver.findElement(By.xpath("//button[text()='Register']"));

        //Fill the form
        firstNameField.sendKeys("Test");
        lastNameField.sendKeys("User");
        emailField.sendKeys("testuser@example.com");
        passwordField.sendKeys("Test1234!");
        confirmPasswordField.sendKeys("Test1234!");
        Select genderSelect = new Select(genderDropdown);
        genderSelect.selectByValue("male");

        // Submit the form
        registerButton.click();

        assertTrue(driver.getCurrentUrl().contains(""), "Registration failed, redirection does not contain '' ");
    }

    @Test
    public void testEmptyFormRegister() {
        driver.get(websiteUrl + "register");

        WebElement registerButton = driver.findElement(By.xpath("//button[text()='Register']"));
        registerButton.click();

        // Wait for the validation message to appear on a focused element.
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input:invalid")));

        // Execute JavaScript to get the browser validation message
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String validationMessage = (String) js.executeScript(
                "return document.activeElement.validationMessage"
        );
        assertTrue(!validationMessage.isEmpty(), "Error message should be present");
        assertEquals("Please fill out this field.", validationMessage, "Error message text does not match");
    }

    @Test
    public void testInvalidEmail() {
        driver.get(websiteUrl + "register");

        // Locate and interact with the registration form elements
        WebElement firstNameField = driver.findElement(By.name("firstName"));
        WebElement lastNameField = driver.findElement(By.name("lastName"));
        WebElement emailField = driver.findElement(By.name("email"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement confirmPasswordField = driver.findElement(By.name("confirmPassword"));
        WebElement genderDropdown = driver.findElement(By.name("gender"));
        WebElement registerButton = driver.findElement(By.xpath("//button[text()='Register']"));

        // Fill the form
        firstNameField.sendKeys("Test");
        lastNameField.sendKeys("User");
        emailField.sendKeys("testuserexample.com");  // Invalid email
        passwordField.sendKeys("Test1234!");
        confirmPasswordField.sendKeys("Test1234!");
        Select genderSelect = new Select(genderDropdown);
        genderSelect.selectByValue("male");

        // Submit the form
        registerButton.click();


        // Wait for the validation message to appear on the focused element.
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input:invalid")));


        // Execute JavaScript to get the browser validation message
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String validationMessage = (String) js.executeScript(
                "return document.activeElement.validationMessage"
        );

        assertTrue(!validationMessage.isEmpty(), "Error message should be present");
        assertEquals("Please include an '@' in the email address. 'testuserexample.com' is missing an '@'.", validationMessage, "Error message text does not match");
    }

    @Test
    public void testPasswordMismatch(){
        driver.get(websiteUrl + "register");

        // Locate and interact with the registration form elements
        WebElement firstNameField = driver.findElement(By.name("firstName"));
        WebElement lastNameField = driver.findElement(By.name("lastName"));
        WebElement emailField = driver.findElement(By.name("email"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement confirmPasswordField = driver.findElement(By.name("confirmPassword"));
        WebElement genderDropdown = driver.findElement(By.name("gender"));
        WebElement registerButton = driver.findElement(By.xpath("//button[text()='Register']"));

        //Fill the form
        firstNameField.sendKeys("Test");
        lastNameField.sendKeys("User");
        emailField.sendKeys("testuser@example.com");
        passwordField.sendKeys("Test1234!");
        confirmPasswordField.sendKeys("Test1234");
        Select genderSelect = new Select(genderDropdown);
        genderSelect.selectByValue("male");

        // Submit the form
        registerButton.click();

        // Wait for the alert to be present
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        // Get the text of the alert message
        String alertText = alert.getText();

        // Assert the alert text
        assertEquals("Passwords do not match", alertText, "Alert message text does not match");

        // Accept the alert (dismiss it)
        alert.accept();
    }

    @Test
    public void testGoToLoginPage(){
        driver.get(websiteUrl + "register");

        WebElement loginLink = driver.findElement(By.className("_alreadyLink_rl22l_163"));
        loginLink.click();

        assertTrue(driver.getCurrentUrl().contains("login"), "Redirection to login page failed");
    }
}
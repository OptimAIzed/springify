import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.testng.Assert.*;

public class HomePageTest {

    private WebDriver driver;
    private final String websiteUrl = "http://localhost:5173/";

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        logIn();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public void logIn() {
        driver.get(websiteUrl + "login");

        // Locate and interact with the login form elements
        WebElement emailField = driver.findElement(By.id("email"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.xpath("//button[text()='Login']"));

        // Fill the form
        emailField.sendKeys("lzyahyapk@gmail.com");
        passwordField.sendKeys("yahya123");

        loginButton.click();

        //Wait for the redirection to home page.
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe(websiteUrl));
    }

    @Test
    public void testHomePageTitle() {
        assertEquals("Springify UML - UML to Spring Boot in Seconds", driver.getTitle());
    }

    @Test
    public void testHomePageElements(){
        // Verify the presence of the logo
        WebElement logo = driver.findElement(By.className("_logo_plb8n_15"));
        assertTrue(logo.isDisplayed(), "Logo is not displayed");

        // Verify the presence of the greetings
        WebElement greetings = driver.findElement(By.className("_greetings_plb8n_57"));
        assertTrue(greetings.isDisplayed(), "Greetings is not displayed");

        // Verify the presence of the logout button
        WebElement logoutButton = driver.findElement(By.className("_button_plb8n_39"));
        assertTrue(logoutButton.isDisplayed(), "Logout button is not displayed");

        //Verify the presence of the GENERATE button
        WebElement generateButton = driver.findElement(By.xpath("//button[text()='GENERATE']"));
        assertTrue(generateButton.isDisplayed(), "Generate button is not displayed");
    }

    @Test
    public void testThemeToggle(){
        WebElement themeToggle = driver.findElement(By.className("_holder_wj8ll_79"));
        assertTrue(themeToggle.isDisplayed(), "Theme toggle is not displayed");
    }

    @Test
    public void testHistoryIcon(){
        WebElement historyIcon = driver.findElement(By.xpath("//*[name()='svg' and @data-testid='HistoryIcon']"));
        assertTrue(historyIcon.isDisplayed(), "History Icon is not displayed");
    }

    @Test
    public void testGithubIcon(){
        WebElement githubIcon = driver.findElement(By.xpath("//*[name()='svg' and @data-testid='GitHubIcon']"));
        assertTrue(githubIcon.isDisplayed(), "Github Icon is not displayed");
    }

    @Test
    public void testProjectTypeRadioButtons(){
        List<WebElement> projectTypes = driver.findElements(By.cssSelector("input[name='type']"));
        for (WebElement projectType : projectTypes) {
            projectType.click();
            assertTrue(projectType.isSelected(), "Radio button for project type "+ projectType.getAttribute("value") + "is not selected");
        }
    }

    @Test
    public void testLanguageRadioButtons(){
        List<WebElement> languages = driver.findElements(By.cssSelector("input[name='language']"));
        for (WebElement language : languages) {
            language.click();
            assertTrue(language.isSelected(), "Radio button for language "+ language.getAttribute("value") + "is not selected");
        }
    }

    @Test
    public void testBootVersionRadioButtons(){
        List<WebElement> bootVersions = driver.findElements(By.cssSelector("input[name='bootVersion']"));
        for (WebElement bootVersion : bootVersions) {
            bootVersion.click();
            assertTrue(bootVersion.isSelected(), "Radio button for boot version "+ bootVersion.getAttribute("value") + "is not selected");
        }
    }

    @Test
    public void testPackagingRadioButtons(){
        List<WebElement> packagingTypes = driver.findElements(By.cssSelector("input[name='packaging']"));
        for (WebElement packagingType : packagingTypes) {
            packagingType.click();
            assertTrue(packagingType.isSelected(), "Radio button for packaging type "+ packagingType.getAttribute("value") + "is not selected");
        }
    }

    @Test
    public void testJavaVersionRadioButtons(){
        List<WebElement> javaVersions = driver.findElements(By.cssSelector("input[name='javaVersion']"));
        for (WebElement javaVersion : javaVersions) {
            javaVersion.click();
            assertTrue(javaVersion.isSelected(), "Radio button for java version "+ javaVersion.getAttribute("value") + "is not selected");
        }
    }

    @Test
    public void testDependencyButton() {
        WebElement dependencyButton = driver.findElement(By.xpath("//button[text()='ADD DEPENDENCIES...']"));
        assertTrue(dependencyButton.isDisplayed(), "Dependency button should be displayed");
        dependencyButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement overlay = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("_overlay_bhwvw_179")));
        assertTrue(overlay.isDisplayed(), "Modal should be displayed");
    }

    @Test
    public void testPhotoDropArea(){
        WebElement photoDrop = driver.findElement(By.className("_container_q6471_1"));
        assertTrue(photoDrop.isDisplayed(), "Photo drop area should be displayed");
    }

    @Test
    public void testLogoutButton(){
        WebElement logoutButton = driver.findElement(By.className("_button_plb8n_39"));
        logoutButton.click();
        assertTrue(driver.getCurrentUrl().contains("login"), "Redirection to login page failed");
    }

    @Test
    public void testHistoryIconModal(){
        WebElement historyIcon = driver.findElement(By.xpath("//*[name()='svg' and @data-testid='HistoryIcon']"));
        historyIcon.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement overlay = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("_overlay_ffgot_1")));
        assertTrue(overlay.isDisplayed(), "Modal should be displayed");
    }

    @Test
    public void testGithubIconLink() {
        WebElement githubIcon = driver.findElement(By.xpath("//*[name()='svg' and @data-testid='GitHubIcon']"));
        String originalWindow = driver.getWindowHandle();
        githubIcon.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        for (String windowHandle : driver.getWindowHandles()) {
            if(!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        assertTrue(driver.getCurrentUrl().contains("github.com"), "Should redirect to github repository");
        driver.close();
        driver.switchTo().window(originalWindow);
    }
}
package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.page_objects.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.page_objects.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.page_objects.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialCrudApplicationTests {
    @LocalServerPort
    private Integer port;
    private String BASE_URL;
    private static WebDriver driver;
    private LoginPage login;
    private SignupPage signup;
    private HomePage home;
    private boolean isSignedUp = false;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterAll
    public static void afterAll() {
        driver.quit();
    }

    @BeforeEach
    public void beforeEach() {
        BASE_URL = "http://localhost:" + port;
        login = new LoginPage(driver);
        signup = new SignupPage(driver);
        home = new HomePage(driver);

        if (!isSignedUp) {
            driver.get(BASE_URL + "/signup");
            signup.signup("a", "1", "Alf", "XYZ");
            isSignedUp = true;
        }
    }

    private void login(String username, String password) {
        driver.get(BASE_URL + "/login");
        login.login(username, password);
    }

    @Test
    void testCreateAndViewCredential() throws InterruptedException {
        login("a", "1");
        // Create Credential
        String url = "https://localhost:xxx";
        String username = "XYZ";
        String password = "klasdmflsdamfas";

        Thread.sleep(1000);
        home.goToCredentialTab();

        home.createCredential(url, username, password);
        assertEquals("Result", driver.getTitle());

        driver.get(BASE_URL + "/home");

        Thread.sleep(1000);
        home.goToCredentialTab();

        String pageSource = driver.getPageSource();
        assertEquals(true, pageSource.contains(url));
        assertEquals(true, pageSource.contains(username));
        assertEquals(false, home.getEncryptedPassword() == password);

        home.openFirstCredentialModal();
        Thread.sleep(1000);
        assertEquals(true, home.getDecryptedPassword().equals(password));
        home.closeCredentialModal();
        home.deleteCredential();
    }

    @Test
    void testUpdateCredential() throws InterruptedException {
        login("a", "1");

        // Create Credential
        String url = "https://localhost:xxx";
        String username = "XYZ";
        String password = "klasdmflsdamfas";

        Thread.sleep(1000);
        home.goToCredentialTab();

        home.createCredential(url, username, password);
        assertEquals("Result", driver.getTitle());

        driver.get(BASE_URL + "/home");

        Thread.sleep(1000);
        home.goToCredentialTab();

        // Update Note
        url = "https://xxx.yyyy";
        username = "kza";
        password = "secret123";

        home.updateCredential(url, username, password);
        assertEquals("Result", driver.getTitle());
        driver.get(BASE_URL + "/home");

        Thread.sleep(1000);
        home.goToCredentialTab();

        String pageSource = driver.getPageSource();
        assertEquals(true, pageSource.contains(url));
        assertEquals(true, pageSource.contains(username));

        // Delete Credential
        home.deleteCredential();
    }

    @Test
    void testDeleteCredential() throws InterruptedException {
        login("a", "1");

        // Create Credential
        String url = "https://localhost:xxx";
        String username = "XYZ";
        String password = "klasdmflsdamfas";

        Thread.sleep(1000);
        home.goToCredentialTab();

        home.createCredential(url, username, password);
        assertEquals("Result", driver.getTitle());
        driver.get(BASE_URL + "/home");

        Thread.sleep(1000);
        home.goToCredentialTab();

        // Delete Credential
        home.deleteCredential();
        assertEquals("Result", driver.getTitle());
        driver.get(BASE_URL + "/home");

        Thread.sleep(1000);
        home.goToCredentialTab();

        String pageSource = driver.getPageSource();
        assertEquals(false, pageSource.contains(url));
        assertEquals(false, pageSource.contains(username));
    }
}

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
public class AuthorizationAuthenticationApplicationTests {

    @LocalServerPort
    private Integer port;
    private String BASE_URL;
    private static WebDriver driver;
    private LoginPage login;
    private SignupPage signup;
    private HomePage home;

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
    }

    @Test
    public void testUnauthorizedCanOnlyAccessLoginAndSignupPages() {
        String loginTitle = "Login";

        driver.get(BASE_URL + "/home");
        assertEquals(loginTitle, driver.getTitle());

        driver.get(BASE_URL + "/result");
        assertEquals(loginTitle, driver.getTitle());
    }

    @Test
    public void testUserSignupLoginAndLogout() {
        String username = "alfianlosari";
        String password = "1";

        driver.get(BASE_URL + "/signup");
        signup.signup(username, password, "Alf", "XYZ");

        driver.get(BASE_URL + "/login");
        login.login(username, password);
        assertEquals("Home", driver.getTitle());

        home.logout();
        assertEquals("Login", driver.getTitle());
    }

}

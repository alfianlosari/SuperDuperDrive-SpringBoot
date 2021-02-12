package com.udacity.jwdnd.course1.cloudstorage.page_objects;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    @FindBy(id="inputUsername")
    private WebElement usernameField;

    @FindBy(id="inputPassword")
    private WebElement passwordField;

    @FindBy(id="inputFirstName")
    private WebElement firstNameField;

    @FindBy(id="inputLastName")
    private WebElement lastNameField;

    @FindBy(id="submit-button")
    private  WebElement submitButton;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void signup(String username, String password, String firstName, String lastName) {
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        firstNameField.sendKeys(firstName);
        lastNameField.sendKeys(lastName);
        submitButton.submit();
    }


}

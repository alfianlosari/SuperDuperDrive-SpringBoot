package com.udacity.jwdnd.course1.cloudstorage.page_objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    @FindBy(id="logout-button")
    private WebElement logoutButton;

    @FindBy(id="nav-tab")
    private WebElement navTab;

    @FindBy(id="nav-notes-tab")
    private WebElement noteTab;

    @FindBy(id = "note-title")
    private WebElement noteTitleField;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionField;

    @FindBy(id = "noteSubmit")
    private WebElement noteSubmitButton;

    @FindBy(id = "createNoteButton")
    private WebElement createNoteButton;

    @FindBy(css = "#notes-list td:first-child button")
    private WebElement notesEditButton;

    @FindBy(css = "#notes-list td:first-child a")
    private WebElement notesDeleteButton;

    @FindBy(id="nav-credentials-tab")
    private WebElement credentialTab;

    @FindBy(id = "credential-url")
    private WebElement credentialURLField;

    @FindBy(id = "credential-username")
    private WebElement credentialUsernameField;

    @FindBy(id = "credential-password")
    private WebElement credentialPasswordField;

    @FindBy(id = "credentialSubmit")
    private WebElement credentialSubmitButton;

    @FindBy(id = "createCredentialButton")
    private WebElement createCredentialButton;

    @FindBy(css = "#credentials-list td:last-child")
    private WebElement credentialEncryptedPassword;

    @FindBy(css = "#credentials-list td:first-child button")
    private WebElement credentialEditButton;

    @FindBy(css = "#credentials-list td:first-child a")
    private WebElement credentialDeleteButton;

    @FindBy(id = "credentialModalClose")
    private WebElement credentialModalCloseButton;

    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void logout() {
        logoutButton.click();
    }

    public void goToNoteTab() {
        noteTab.click();
    }

    public void goToCredentialTab() {
        credentialTab.click();
    }

    public void openFirstCredentialModal() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.elementToBeClickable(credentialEditButton));
        credentialEditButton.click();
    }

    public void closeCredentialModal() {
        credentialModalCloseButton.click();
    }

    public void createNote(String notetitle, String noteDescription) throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, 5);

        wait.until(ExpectedConditions.elementToBeClickable(createNoteButton));
        createNoteButton.click();

        wait.until(ExpectedConditions.visibilityOf(noteTitleField));
        noteTitleField.clear();
        noteTitleField.sendKeys(notetitle);

        wait.until(ExpectedConditions.visibilityOf(noteDescriptionField));
        noteDescriptionField.clear();
        noteDescriptionField.sendKeys(noteDescription);

        noteSubmitButton.submit();
    }

    public void updateNote(String notetitle, String noteDescription) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 5);

        wait.until(ExpectedConditions.elementToBeClickable(notesEditButton));
        notesEditButton.click();

        wait.until(ExpectedConditions.visibilityOf(noteTitleField));
        noteTitleField.clear();
        noteTitleField.sendKeys(notetitle);

        wait.until(ExpectedConditions.visibilityOf(noteDescriptionField));
        noteDescriptionField.clear();
        noteDescriptionField.sendKeys(noteDescription);

        noteSubmitButton.submit();
    }

    public void deleteNote() throws InterruptedException {
        Thread.sleep(1000);
        notesDeleteButton.click();
    }

    public void createCredential(String url, String username, String password) {
        WebDriverWait wait = new WebDriverWait(driver, 5);

        wait.until(ExpectedConditions.elementToBeClickable(createCredentialButton));
        createCredentialButton.click();

        wait.until(ExpectedConditions.visibilityOf(credentialURLField));
        credentialURLField.clear();
        credentialURLField.sendKeys(url);

        wait.until(ExpectedConditions.visibilityOf(credentialUsernameField));
        credentialUsernameField.clear();
        credentialUsernameField.sendKeys(username);

        wait.until(ExpectedConditions.visibilityOf(credentialPasswordField));
        credentialPasswordField.clear();
        credentialPasswordField.sendKeys(password);

        credentialSubmitButton.submit();
    }

    public void updateCredential(String url, String username, String password) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 5);

        wait.until(ExpectedConditions.elementToBeClickable(credentialEditButton));
        credentialEditButton.click();

        wait.until(ExpectedConditions.visibilityOf(credentialURLField));
        credentialURLField.clear();
        credentialURLField.sendKeys(url);

        wait.until(ExpectedConditions.visibilityOf(credentialUsernameField));
        credentialUsernameField.clear();
        credentialUsernameField.sendKeys(username);

        wait.until(ExpectedConditions.visibilityOf(credentialPasswordField));
        credentialPasswordField.clear();
        credentialPasswordField.sendKeys(password);

        credentialSubmitButton.submit();
    }

    public String getEncryptedPassword() {
        return credentialEncryptedPassword.getText();
    }

    public String getDecryptedPassword() {
        return credentialPasswordField.getAttribute("value");
    }

    public void deleteCredential() throws InterruptedException {
        Thread.sleep(1000);
        credentialDeleteButton.click();
    }

}



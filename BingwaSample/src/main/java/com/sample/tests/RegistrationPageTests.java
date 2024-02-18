package com.sample.tests;

import com.microsoft.playwright.*;
import com.sample.pages.LandingPage;
import com.sample.pages.LoginPage;
import com.sample.pages.RegistrationPage;
import com.sample.pages.VerificationPage;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;

public class RegistrationPageTests {
    Page page;
    BrowserContext context;
    LoginPage loginPage;
    RegistrationPage registrationPage;

    @BeforeClass
    public void setup() {
        Browser browser;
        Playwright playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
        );
        context = browser.newContext();
        context.setDefaultTimeout(90000);
        page = context.newPage();
        page.navigate("https://bingwa.ke/");
        LandingPage landingPage = new LandingPage(page);
        loginPage = landingPage.goToLogin();
        registrationPage = loginPage.register();
    }

    @AfterClass
    public void tearDown() {
        try {
            page.close();
            context.close();
        } catch (Exception e) {
            //page already closed, exception ignored
        }
    }

    @Test(testName = "Register page is visible")
    public void testPageVisible() {
        Assert.assertTrue(registrationPage.pageVisible());
    }

    @Test(testName = "Register with missing firstname")
    public void testMissingFirstname() {
        HashMap<String, String> user_details = new HashMap<>();
        user_details.put("email", "test@email.com");
       user_details.put("lastname", "Lastname");
        user_details.put("password", "@ABcd432");
        VerificationPage verificationPage = registrationPage.register(user_details);
        Assert.assertNull(verificationPage);
        Assert.assertFalse(registrationPage.isRegisterButtonEnabled());
    }

    @Test(testName = "Register with missing lastname")
    public void testMissingLastname() {
        page.reload();
        HashMap<String, String> user_details = new HashMap<>();
        user_details.put("email", "test@email.com");
        user_details.put("firstname", "Firstname");
        user_details.put("password", "@ABcd432");
        VerificationPage verificationPage = registrationPage.register(user_details);
        Assert.assertNull(verificationPage);
        Assert.assertFalse(registrationPage.isRegisterButtonEnabled());
    }


    @Test(testName = "Register with missing phonenumber and email")
    public void testMissingPhonenumber() {
        page.reload();
        HashMap<String, String> user_details = new HashMap<>();
        user_details.put("firstname", "Firstname");
        user_details.put("lastname", "Lastname");
        user_details.put("password", "@ABcd432");
        VerificationPage verificationPage = registrationPage.register(user_details);
        Assert.assertNull(verificationPage);
        Assert.assertFalse(registrationPage.isRegisterButtonEnabled());
    }


    @Test(testName = "Register with invalid phonenumber")
    public void testInvalidPhonenumber() {
        page.reload();
        HashMap<String, String> user_details = new HashMap<>();
        user_details.put("firstname", "Firstname");
        user_details.put("lastname", "Lastname");
        user_details.put("phonenumber", "12ABCD");
        user_details.put("password", "@ABcd432");
        VerificationPage verificationPage = registrationPage.register(user_details);
        Assert.assertNull(verificationPage);
        Assert.assertFalse(registrationPage.isRegisterButtonEnabled());
    }

    @Test(testName = "Register with invalid email")
    public void testInvalidEmail() {
        page.reload();
        HashMap<String, String> user_details = new HashMap<>();
        user_details.put("firstname", "Firstname");
        user_details.put("lastname", "Lastname");
        user_details.put("email", "invalid.com");
        user_details.put("password", "@ABcd432");
        VerificationPage verificationPage = registrationPage.register(user_details);
        Assert.assertNull(verificationPage);
        Assert.assertFalse(registrationPage.isRegisterButtonEnabled());
    }


    @Test(testName = "Register with missing password")
    public void testMissingPassword() {
        page.reload();
        HashMap<String, String> user_details = new HashMap<>();
        user_details.put("firstname", "Firstname");
        user_details.put("lastname", "Lastname");
        user_details.put("email", "test@email.com");
        VerificationPage verificationPage = registrationPage.register(user_details);
        Assert.assertNull(verificationPage);
        Assert.assertFalse(registrationPage.isRegisterButtonEnabled());
    }


    @Test(testName = "Register with invalid password")
    public void testInvalidPassword() {
        page.reload();
        HashMap<String, String> user_details = new HashMap<>();
        user_details.put("firstname", "Firstname");
        user_details.put("lastname", "Lastname");
        user_details.put("email", "invalid.com");
        user_details.put("password", "122");
        VerificationPage verificationPage = registrationPage.register(user_details);
        Assert.assertNull(verificationPage);
        Assert.assertFalse(registrationPage.isRegisterButtonEnabled());
    }


    @Test(testName = "Register with existing email")
    public void testExistingEmail() {
        //page.reload();
        page.close();
        page = context.newPage();
        registrationPage = goToRegistrationPage(page);
        HashMap<String, String> user_details = new HashMap<>();
        user_details.put("firstname", "Firstname");
        user_details.put("lastname", "Lastname");
        user_details.put("email", "leirtech@gmail.com");
        user_details.put("password", "leir1234");
        VerificationPage verificationPage = registrationPage.register(user_details);
        Assert.assertFalse(verificationPage.pageVisible());
        Assert.assertTrue(registrationPage.pageVisible());
    }


    @Test(testName = "Register with existing phonenumber")
    public void testExistingPhonenumber() {
        page.close();
        page = context.newPage();
        registrationPage = goToRegistrationPage(page);
        HashMap<String, String> user_details = new HashMap<>();
        user_details.put("firstname", "Firstname");
        user_details.put("lastname", "Lastname");
        user_details.put("phonenumber", "0728322059");
        user_details.put("password", "leir1234");
        VerificationPage verificationPage = registrationPage.register(user_details);
        Assert.assertFalse(verificationPage.pageVisible());
        Assert.assertTrue(registrationPage.pageVisible());
    }


    @Test(testName = "Password is masked")
    public void testPasswordMasked() {
        page.reload();
        HashMap<String, String> user_details = new HashMap<>();
        user_details.put("firstname", "Firstname");
        user_details.put("lastname", "Lastname");
        user_details.put("password", "122gd");
        VerificationPage verificationPage = registrationPage.register(user_details);
        Assert.assertNull(verificationPage);
        Assert.assertTrue(registrationPage.isPasswordMasked());
    }


    @Test(testName = "Password unmasking")
    public void testPasswordUnmasking() {
        page.reload();
        HashMap<String, String> user_details = new HashMap<>();
        user_details.put("firstname", "Firstname");
        user_details.put("lastname", "Lastname");
        user_details.put("password", "122gd");
        VerificationPage verificationPage = registrationPage.register(user_details);
        Assert.assertNull(verificationPage);
        registrationPage.unMaskPassword();
        Assert.assertFalse(registrationPage.isPasswordMasked());
    }

    public RegistrationPage goToRegistrationPage(Page page){
        page.navigate("https://bingwa.ke/");
        LandingPage landingPage = new LandingPage(page);
        loginPage = landingPage.goToLogin();
        return loginPage.register();
    }
}

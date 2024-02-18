package com.sample.tests;

import com.microsoft.playwright.*;
import com.sample.pages.HomePage;
import com.sample.pages.LandingPage;
import com.sample.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginPageTests {
    Page page;
    BrowserContext context;
    LoginPage loginPage;

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

    @Test(testName = "Login using invalid email")
    public void testLoginWithInvalidEmail() {
        loginPage.login("kim@gmail.com", "@2500chegeG");
        Assert.assertTrue(loginPage.hasLoginError());
    }

    @Test(testName = "Login using invalid phonenumber")
    public void testLoginWithInvalidPhonenumber() {
        loginPage.login("0728322066 ", "@2500chegeG");
        Assert.assertTrue(loginPage.hasLoginError());
    }


    @Test(testName = "Login using invalid phonenumber and password")
    public void testLoginWithInvalidPhonenumberAndPassword() {
        loginPage.login("0728322066", "2500chegeG123");
        Assert.assertTrue(loginPage.hasLoginError());
    }

    @Test(testName = "Password unmasking")
    public void testPasswordUnmasking() {
        Assert.assertTrue(loginPage.pageVisible());
        Assert.assertTrue(loginPage.isPasswordMasked());
        loginPage.unMaskPassword();
        Assert.assertFalse(loginPage.isPasswordMasked());
    }

    @Test(testName = "Login using valid email and password")
    public void testLoginWithValidEmailAndPassword() {
        HomePage homePage = loginPage.login("leirtech@gmail.com", "leir1234");
        Assert.assertTrue(homePage.pageVisible());
        homePage.logout();
    }

    @Test(testName = "Login using valid phonenumber and password")
    public void testLoginWithValidPhonenumberAndPassword() {
        HomePage homePage = loginPage.login("0728322059", "leir1234");
        Assert.assertTrue(homePage.pageVisible());
        homePage.logout();
    }

}

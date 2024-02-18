package com.sample.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.HashMap;

public class LoginPage {
    final Page page;

    Locator text_title;
    Locator input_username;
    Locator input_password;
    Locator button_unmask_password;
    Locator button_login;
    Locator icon_loging_in;
    Locator link_register;

    //errors
    Locator text_user_not_found;
    Locator text_invalid_credentials;

    public LoginPage(Page page) {
        this.page = page;
        init();
    }

    public void init() {
        text_title = page.locator("//span[text()='Login to Bingwa']");
        input_username = page.locator("//mat-label[text()='Phone or Email Address']/../../../..//input");
        input_password = page.locator("//input[@placeholder='Password']");
        button_unmask_password = page.locator("//visibility-icon");
        button_login = page.locator("//button[@id='login_page_login_cta_button_account_login']");
        icon_loging_in = page.locator("//span[text()='Login ']/../span[2]/span");
        link_register = page.locator("//button[@id='login_page_register_button_switch_to_register']");
        text_user_not_found = page.locator("//div[text()=' User not found, register to be a Bingwa ']");
        text_invalid_credentials = page.locator("//div[text()=' Wrong username or password, you can reset in case you forgot your password ']");
    }

    public boolean pageVisible() {
        try {
            text_title.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            return text_title.isVisible();
        } catch (TimeoutError te) {
            return false;
        }
    }

    public RegistrationPage register() {
        link_register.click();
        return new RegistrationPage(page);
    }

    public HomePage login(String username, String password){
        input_username.fill(username);
        input_password.fill(password);
        if(button_login.isEnabled()) {
            button_login.click();
            icon_loging_in.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            icon_loging_in.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));
            return new HomePage(page);
        } else {
            return null;
        }
    }

    public void unMaskPassword(){
        if(input_password.getAttribute("type").equalsIgnoreCase("password"))
            button_unmask_password.click();
    }

    public void maskPassword(){
        if(input_password.getAttribute("type").equalsIgnoreCase("text"))
            button_unmask_password.click();
    }

    public boolean isPasswordMasked(){
        System.out.println(input_password.getAttribute("type"));
        return input_password.getAttribute("type").equalsIgnoreCase("password");
    }

    public boolean hasLoginError(){
        return text_user_not_found.isVisible() || text_invalid_credentials.isVisible();
    }

}

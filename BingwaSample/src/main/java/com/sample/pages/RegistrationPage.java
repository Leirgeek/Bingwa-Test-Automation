package com.sample.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.HashMap;

public class RegistrationPage {
    final Page page;
    Locator text_title;
    Locator input_firstname;
    Locator input_lastname;
    Locator input_phonenumber;
    Locator input_email;
    Locator input_password;
    Locator button_unmask_password;
    Locator link_use_email;
    Locator link_use_phonenumber;
    Locator button_register;
    Locator link_login;
    Locator text_missing_password;
    Locator text_invalid_password;

    public RegistrationPage(Page page) {
        this.page = page;
        init();
    }

    public void init() {
        text_title = page.locator("//span[text()='Create your account']");
        input_firstname = page.locator("//input[@formcontrolname='firstName']");
        input_lastname = page.locator("//input[@formcontrolname='lastName']");
        input_phonenumber = page.locator("//input[@formcontrolname='phoneNumber']");
        input_email = page.locator("//input[@formcontrolname='email']");
        link_use_phonenumber = page.locator("//button[contains(text(),'Phone')]");
        link_use_email = page.locator("//button[contains(text(),'Email')]");
        input_password = page.locator("//input[@formcontrolname='password']");
        button_unmask_password = page.locator("//visibility-icon");
        button_register = page.locator("//button[@id='register-button']");
        text_missing_password = page.locator("//div[text()='* Password is required.']");
        text_invalid_password = page.locator("//div[contains(text(),'Your password must be at least 8 characters long')]");
    }

    public boolean pageVisible() {
        try {
            text_title.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(20000));
            return text_title.isVisible();
        } catch (TimeoutError te) {
            return false;
        }
    }

    public VerificationPage register(HashMap<String, String> user_details){
        pageVisible();
        String email = user_details.get("email");
        String phonenumber = user_details.get("phonenumber");
        String first_name = user_details.get("firstname");
        String last_name = user_details.get("lastname");
        String password = user_details.get("password");

        if(input_email.isVisible())
            input_email.clear();
        if(input_phonenumber.isVisible())
            input_phonenumber.clear();
        if(input_password.isVisible())
            input_password.clear();
        if(input_lastname.isVisible())
            input_lastname.clear();
        if(input_firstname.isVisible())
            input_firstname.clear();

        if(email != null && !email.isEmpty()){
            if(link_use_email.isVisible())
                link_use_email.click();

            input_email.fill(email);
        } else if(phonenumber != null && !phonenumber.isEmpty()){
            if(link_use_phonenumber.isVisible())
                link_use_phonenumber.click();

            input_phonenumber.fill(phonenumber);
        }

        if(first_name != null && !first_name.isEmpty()) {
            input_firstname.fill(first_name);
        }

        if(last_name != null && !last_name.isEmpty()) {
            input_lastname.fill(last_name);
        }

        if(password != null && !password.isEmpty()) {
            input_password.fill(password);
        }

        if(button_register.isEnabled()) {
            button_register.click();
            return new VerificationPage(page);
        } else {
            return null;
        }
    }

    public boolean isRegisterButtonEnabled(){
        return button_register.isEnabled();
    }

    public boolean passwordInvalid(){
        return text_invalid_password.isVisible();
    }

    public boolean passwordMissing(){
        return text_missing_password.isVisible();
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
        return input_password.getAttribute("type").equalsIgnoreCase("password");
    }
}

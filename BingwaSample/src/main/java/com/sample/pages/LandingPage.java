package com.sample.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LandingPage {
    final Page page;

    Locator link_signin;

    public LandingPage(Page page) {
        this.page = page;
        init();
    }

    public void init() {
        link_signin = page.locator("//ion-button[@id='header_section_sign_in_to_account']");
    }

    public LoginPage goToLogin() {
        link_signin.click();
        return new LoginPage(page);
    }
}

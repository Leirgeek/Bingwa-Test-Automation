package com.sample.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import com.microsoft.playwright.options.WaitForSelectorState;

public class VerificationPage {
    final Page page;
    Locator text_title;

    public VerificationPage(Page page) {
        this.page = page;
        init();
    }

    public void init() {
        text_title = page.locator("//span[text()='Account Verification']");
    }

    public boolean pageVisible() {
        try {
            text_title.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(20000));
            return text_title.isVisible();
        } catch (TimeoutError te) {
            return false;
        }
    }
}

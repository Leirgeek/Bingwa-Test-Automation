package com.sample.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import com.microsoft.playwright.options.WaitForSelectorState;

public class HomePage {
    //https://bingwa.ke/app/home
    final Page page;

    Locator text_title;
    Locator icon_messages;
    Locator icon_avatar;
    Locator button_logout;

    public HomePage(Page page) {
        this.page = page;
        init();
    }

    public void init(){
        icon_messages = page.locator("//a[@aria-label='My Chat']");
        icon_avatar = page.locator("//img[@class='header-avatar-image']");
        button_logout = page.locator("//ion-label[text()=' Log Out ']/..");
    }

    public boolean pageVisible(){
        try{
            icon_avatar.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(30000));
            return icon_avatar.isVisible();
        }catch (TimeoutError te){
            return false;
        }
    }

    public void logout(){
        if(!button_logout.isVisible())
            icon_avatar.click();
        button_logout.click();
    }
}

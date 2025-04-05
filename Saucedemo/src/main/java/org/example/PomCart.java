package org.example;

import Actions.ActionUI;

public class PomCart {
    public PomCart(){
        act=new ActionUI();
        checkPage=new PomCheckout();
    }
    ActionUI act;
    PomCheckout checkPage;
    protected String headerXpath="//div[@class=\"subheader\"]";
    private String checkoutXpath="//a[text()=\"CHECKOUT\"]";

    public PomCheckout checkout(){
        act.click(act.byXpath(checkoutXpath),act.byXpath(checkPage.header));
        return checkPage;
    }
}

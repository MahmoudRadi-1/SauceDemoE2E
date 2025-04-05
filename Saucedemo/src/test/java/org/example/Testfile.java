package org.example;

import Actions.ActionUI;
import Actions.ActionsBroswer;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.*;

public class Testfile {
    WebDriver driver;
    PomLogin loginPage;
    PomProducts prodPage;
    PomCart cartPage;
    PomCheckout checkPage;
    PomPayment payPage;
    ActionUI act;

    @BeforeMethod
    public void setup() {
        ActionsBroswer.setDrivers(ActionsBroswer.Browsers.chrome);
        act = new ActionUI();
        loginPage = new PomLogin();
        prodPage = new PomProducts();
        checkPage = new PomCheckout();
        cartPage = new PomCart();
        payPage = new PomPayment();
        ActionsBroswer.windowMaximize();
    }

    @Test
    public void e2e_NormalUser() {
        System.out.println("e2e_NormalUser");
        loginPage.navLoginPage().enterUsername(loginPage.user).enterPW().clickLogin()
                .addToCart(prodPage.backPack()).addToCart(prodPage.boltShirt()).removeFromCart(prodPage.boltShirt()).addToCart(prodPage.testAllShirt()).viewCart();
        Assert.assertEquals(prodPage.actualInCart.get(), prodPage.ExpInCart.get());
        cartPage.checkout()
        .fillData().clickContinue();
        Assert.assertEquals(payPage.cartTotal(), prodPage.expCartTotal.get(), "Order Total Mismatch");
        payPage.clickFinish();
    }

    @Test
    public void e2e_PerformanceGlitch() {
        System.out.println("e2e_PerformanceGlitch");
        loginPage.navLoginPage().enterUsername(loginPage.glitchUser).enterPW().clickLogin();
        prodPage.addToCart(prodPage.backPack()).addToCart(prodPage.boltShirt()).removeFromCart(prodPage.boltShirt()).addToCart(prodPage.testAllShirt()).viewCart();
        Assert.assertEquals(prodPage.actualInCart.get(), prodPage.ExpInCart.get());
        cartPage.checkout();
        checkPage.fillData().clickContinue();
        Assert.assertEquals(payPage.cartTotal(), prodPage.expCartTotal.get(), "Order Total Mismatch");
        payPage.clickFinish();
    }

    @Test
    public void Locked_User_loginFail() {
        System.out.println("Locked_User_loginFail");
        loginPage.navLoginPage().enterUsername(loginPage.lockedUser).enterPW().clickLogin();
    }
    
    @Test
    public void problemUserAddToCart() {
        System.out.println("problemUserAddToCart");
        loginPage.navLoginPage().enterUsername(loginPage.problemUser).enterPW().clickLogin();
        prodPage.addToCart(prodPage.backPack()).addToCart(prodPage.boltShirt()).removeFromCart(prodPage.boltShirt()).addToCart(prodPage.testAllShirt());
        Assert.assertEquals(prodPage.actualInCart.get(), prodPage.ExpInCart.get(), "Add or Remove failed");
    }

    @Test
    public void problem_User_Checkout() {
        System.out.println("problem_User_Checkout");
        loginPage.navLoginPage().enterUsername(loginPage.problemUser).enterPW().clickLogin();
        prodPage.addToCart(prodPage.backPack()).addToCart(prodPage.bikeLight()).addToCart(prodPage.onesie()).viewCart();
        Assert.assertEquals(prodPage.actualInCart.get(), prodPage.ExpInCart.get());
        cartPage.checkout();
        checkPage.fillData().clickContinue();
        Assert.assertEquals(payPage.cartTotal(), prodPage.expCartTotal.get(), "Order Total Mismatch");
        payPage.clickFinish();
    }

    @AfterMethod
    public void teardown() {
        ActionsBroswer.quitDriver();
        if (prodPage != null) prodPage.clearThreadLocalData();
        if (payPage != null) payPage.clearThreadLocalData();
    }
}

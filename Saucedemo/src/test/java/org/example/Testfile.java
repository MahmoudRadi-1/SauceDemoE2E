package org.example;
import Actions.ActionUI;
import Actions.ActionsBrowser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;

public class Testfile {
    WebDriver driver;
    PomLogin loginPage;
    PomProducts prodPage;
    PomCart cartPage;
    PomCheckout checkPage;
    PomPayment payPage;
    ActionUI act;

    @BeforeEach
    public void setup() {
        ActionsBrowser.setDrivers(ActionsBrowser.Browsers.chrome);
        act = new ActionUI();
        loginPage = new PomLogin();
        prodPage = new PomProducts();
        checkPage = new PomCheckout();
        cartPage = new PomCart();
        payPage = new PomPayment();
        driver = ActionsBrowser.drivers.get();
        driver.manage().window().maximize();
    }

    @Test
    public void e2e_NormalUser() {
        System.out.println("e2e_NormalUser");
        loginPage.navLoginPage().enterUsername(loginPage.user).enterPW().clickLogin();
        prodPage.addToCart(prodPage.BackPack()).addToCart(prodPage.BoltShirt())
                .removeFromCart(prodPage.BoltShirt()).addToCart(prodPage.TestAllShirt()).viewCart();
        Assertions.assertEquals(prodPage.actualInCart.get(), prodPage.ExpInCart.get());
        cartPage.checkout();
        checkPage.fillData().clickContinue();
        Assertions.assertEquals(payPage.cartTotal(), prodPage.expCartTotal.get(), "Order Total Mismatch");
        payPage.clickFinish();
    }

    @Test
    public void e2e_PerformanceGlitch() {
        System.out.println("e2e_PerformanceGlitch");
        loginPage.navLoginPage().enterUsername(loginPage.glitchUser).enterPW().clickLogin();
        prodPage.addToCart(prodPage.BackPack()).addToCart(prodPage.BoltShirt())
                .removeFromCart(prodPage.BoltShirt()).addToCart(prodPage.TestAllShirt()).viewCart();
        Assertions.assertEquals(prodPage.actualInCart.get(), prodPage.ExpInCart.get());
        cartPage.checkout();
        checkPage.fillData().clickContinue();
        Assertions.assertEquals(payPage.cartTotal(), prodPage.expCartTotal.get(), "Order Total Mismatch");
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
        prodPage.addToCart(prodPage.BackPack()).addToCart(prodPage.BoltShirt()).removeFromCart(prodPage.BoltShirt())
                .addToCart(prodPage.TestAllShirt());
        Assertions.assertEquals(prodPage.actualInCart.get(), prodPage.ExpInCart.get(), "Add or Remove failed");
    }

    @Test
    public void problem_User_Checkout() {
        System.out.println("problem_User_Checkout");
        loginPage.navLoginPage().enterUsername(loginPage.problemUser).enterPW().clickLogin();
        prodPage.addToCart(prodPage.BackPack()).addToCart(prodPage.BikeLight()).addToCart(prodPage.Onesie()).viewCart();
        Assertions.assertEquals(prodPage.actualInCart.get(), prodPage.ExpInCart.get());
        cartPage.checkout();
        checkPage.fillData().clickContinue();
        Assertions.assertEquals(payPage.cartTotal(), prodPage.expCartTotal.get(), "Order Total Mismatch");
        payPage.clickFinish();
    }

    @AfterEach
    public void teardown() {
        ActionsBrowser.quitDriver();
    }
}
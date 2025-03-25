package org.example;

import Actions.ActionUI;

public class PomProducts {
    public PomProducts(){
        act = new ActionUI();
        cartPage = new PomCart();
    }
    private ActionUI act;
    private PomCart cartPage;
    private String prodName;
    private String url = "https://www.saucedemo.com/v1/inventory.html";
    protected String headerXpath = "//div[@class=\"product_label\"]";
    private String addButton = "/ancestor::div[@class=\"inventory_item\"]//button[@class=\"btn_primary btn_inventory\" and text()=\"ADD TO CART\"]";
    private String removeButton = "/ancestor::div[@class=\"inventory_item\"]//button[@class=\"btn_secondary btn_inventory\" and text()=\"REMOVE\"]";
    private String inCartXpath = "//span[@class=\"fa-layers-counter shopping_cart_badge\"]";
    private String priceXpath = "/ancestor::div[@class=\"inventory_item\"]//div[@class=\"inventory_item_price\"]";
    ThreadLocal<Double> ExpInCart = ThreadLocal.withInitial(() -> 0.0);
    ThreadLocal<Double> actualInCart = ThreadLocal.withInitial(() -> 0.0);
    ThreadLocal<Double> expCartTotal = ThreadLocal.withInitial(() -> 0.0);
    ThreadLocal<Double> totalTemp = ThreadLocal.withInitial(() -> 0.0);

    private String getProductXpath(String prodName) {
        return "//div[@class='inventory_item_name' and text()='" + prodName + "']";
    }

    public String backPack() {
        prodName = "Sauce Labs Backpack";
        return prodName;
    }

    public String bikeLight() {
        prodName = "Sauce Labs Bike Light";
        return prodName;
    }

    public String boltShirt() {
        prodName = "Sauce Labs Bolt T-Shirt";
        return prodName;
    }

    public String fleeceJacket() {
        prodName = "Sauce Labs Fleece Jacket";
        return prodName;
    }

    public String onesie() {
        prodName = "Sauce Labs Onesie";
        return prodName;
    }

    public String testAllShirt() {
        prodName = "Test.allTheThings() T-Shirt (Red)";
        return prodName;
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public PomProducts addToCart(String item) {
        try {
            act.click(act.byXpath(getProductXpath(item) + addButton), act.byXpath(inCartXpath));
            ExpInCart.set(ExpInCart.get() + 1.0);
            expCartTotal.set(expCartTotal.get() + act.getNum(getProductXpath(item) + priceXpath));
            totalTemp.set(Double.parseDouble(String.format("%.2f", expCartTotal.get())));
            expCartTotal.set(totalTemp.get());
            System.out.printf("Items in cart: %.0f%nCart Total: %.2f%n", ExpInCart.get(), expCartTotal.get());
            actualInCart.set(act.getNum(inCartXpath));
        } catch (Exception e) {
            System.out.println("Add item " + item + " Failed");
        }
        return this;
    }

    public PomProducts removeFromCart(String item) {
        try {
            act.click(act.byXpath(getProductXpath(item) + removeButton), act.byXpath(inCartXpath));
            ExpInCart.set(ExpInCart.get() - 1.0);
            expCartTotal.set(expCartTotal.get() - act.getNum(getProductXpath(item) + priceXpath));
            totalTemp.set(Double.parseDouble(String.format("%.2f", expCartTotal.get())));
            expCartTotal.set(totalTemp.get());
            System.out.printf("Items in cart: %.0f%nCart Total: %.2f%n", ExpInCart.get(), expCartTotal.get());
            actualInCart.set(act.getNum(inCartXpath));
        } catch (Exception e) {
            System.out.println("Remove item " + item + " Failed");
        }
        return this;
    }

    public void viewCart() {
        act.click(act.byXpath(inCartXpath), act.byXpath(cartPage.headerXpath));
    }
    public void clearThreadLocalData() {
        ExpInCart.remove();
        actualInCart.remove();
        expCartTotal.remove();
        totalTemp.remove();
    }
}

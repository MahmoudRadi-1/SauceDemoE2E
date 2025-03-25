package org.example;

import Actions.ActionUI;

public class PomCheckout {
    public PomCheckout(){
        act=new ActionUI();
        paymentPage=new PomPayment();
    }
    private ActionUI act;
    private PomPayment paymentPage;
    protected String header="//div[text()=\"Checkout: Your Information\"]";
    private String continueButtonXpath="//input[@type=\"submit\"]";
    private String fNameID="first-name";
    private String lNameID="last-name";
    private String postalID="postal-code";
    private String fName="Mahmoud";
    private String lName="Radi";
    private String postalCode="11742";

            public PomCheckout fillData(){
                act.enterText(act.byID(fNameID),fName).enterText(act.byID(lNameID),lName).enterText(act.byID(postalID),postalCode);
                return this;
            }
            public PomCheckout clickContinue(){
                act.click(act.byXpath(continueButtonXpath),act.byXpath(paymentPage.paymentHeaderXpath));
                return this;
            }
}

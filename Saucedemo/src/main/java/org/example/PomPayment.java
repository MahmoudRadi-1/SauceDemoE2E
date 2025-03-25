package org.example;

import Actions.ActionUI;

public class PomPayment {
    public PomPayment(){
        act=new ActionUI();
    }
    private ActionUI act;
    protected String paymentHeaderXpath ="//div[text()=\"Payment Information:\"]";
    private String totalXpath="//div[@class=\"summary_subtotal_label\"]";
    private String finishXpath="//a[text()=\"FINISH\"]";
    private String checkoutCompleteID="checkout_complete_container";
    ThreadLocal<Double> actualCartTotal = ThreadLocal.withInitial(() -> 0.0);
    public double cartTotal(){
        actualCartTotal.set(act.getNum(totalXpath));
        return actualCartTotal.get();
    }
    public PomPayment clickFinish(){
        act.click(act.byXpath(finishXpath),act.byID(checkoutCompleteID));
        return this;
    }
    public void clearThreadLocalData() {
        actualCartTotal.remove();
    }
}

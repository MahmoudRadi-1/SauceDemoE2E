package org.example;

import Actions.ActionUI;

public class PomLogin {
    public PomLogin(){
        prodpage= new PomProducts();
        act=new ActionUI();
    }
    private PomProducts prodpage;
    private ActionUI act;
    private String url = "https://www.saucedemo.com/v1/";
    protected String user = "standard_user";
    protected String lockedUser = "locked_out_user";
    protected String problemUser = "problem_user";
    protected String glitchUser = "performance_glitch_user";
    protected String password = "secret_sauce";

    private String uNameID = "user-name";
    private String pwID = "password";
    private String loginBtnID = "login-button";




public PomLogin navLoginPage(){
    act.nav(url);
    return this;
}
    public PomLogin enterUsername(String uname) {
        act.enterText(act.byID(uNameID), uname);
        return this;
    }

    public PomLogin enterPW() {
        act.enterText(act.byID(pwID), password);
        return this;
    }
    public PomLogin clickLogin(){

        try {
            act.click(act.byID(loginBtnID),act.byXpath(prodpage.headerXpath));
        } catch (Exception e) {
            System.out.println("login Failed");
        }
        return this;
    }

}
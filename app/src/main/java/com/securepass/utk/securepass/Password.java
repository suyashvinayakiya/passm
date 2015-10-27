package com.securepass.utk.securepass;

/**
 * Created by utk on 15-10-25.
 */
public class Password {
    private String name;
    private String pass;

    public Password(String name, String pass){
        this.name = name;
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}

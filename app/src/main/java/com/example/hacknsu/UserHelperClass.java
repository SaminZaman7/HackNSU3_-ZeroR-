package com.example.hacknsu;

public class UserHelperClass {


    String username, email, blood, pass, cpass, uid;

    public UserHelperClass() {
    }

    public UserHelperClass(String username, String email, String blood, String pass, String cpass, String uid) {
        this.username = username;
        this.email = email;
        this.blood = blood;
        this.pass = pass;
        this.cpass = cpass;
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getCpass() {
        return cpass;
    }

    public void setCpass(String cpass) {
        this.cpass = cpass;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


}

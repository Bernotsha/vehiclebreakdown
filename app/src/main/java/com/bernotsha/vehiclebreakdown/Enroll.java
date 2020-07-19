package com.bernotsha.vehiclebreakdown;

public class Enroll {
    String name,mobile,email,address,licence,password,confirmpassword;
    Enroll()
    {

    }

    public Enroll(String name, String mobile, String email, String address, String licence, String password, String confirmpassword) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.address = address;
        this.licence = licence;
        this.password = password;
        this.confirmpassword = confirmpassword;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getLicence() {
        return licence;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmpassword() {
        return confirmpassword;
    }
}

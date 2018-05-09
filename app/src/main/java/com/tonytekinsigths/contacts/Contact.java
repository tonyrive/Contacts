package com.tonytekinsigths.contacts;

import java.io.Serializable;

public class Contact implements Serializable {
    private String FirstName;
    private String LastName;
    private String Company;
    private String Picture;
    private String Phone;
    private String Address;
    private String Address2;

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        this.FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        this.LastName = lastName;
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        this.Company = company;
    }

    public String getDisplayName() {
        String display = "";

        if (!isNullOrEmpty(getFullName().trim()))
            display = getFullName();
        else if (!isNullOrEmpty(getCompany()))
            display = getCompany();

        return display;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        this.Picture = picture;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        this.Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public String getAddress2() {
        return Address2;
    }

    public void setAddress2(String address2) {
        this.Address2 = address2;
    }

    private boolean isNullOrEmpty(String value){
        return value == null || value.trim().isEmpty();
    }
}

package com.revature.shoply.user.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.shoply.models.User;
import com.revature.shoply.models.enums.AddressType;
import jakarta.persistence.*;

import java.util.UUID;

public class IncomingAddressDTO {

    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private AddressType type;

    public IncomingAddressDTO() {
    }

    public IncomingAddressDTO(String addressLine1, String addressLine2, String city, String state, String zipCode, String country, AddressType type) {
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
        this.type = type;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {

        if(addressLine1 == null || addressLine1.isBlank()) throw new IllegalArgumentException("Address Line 1 cannot be blank or null");
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        if(city == null || city.isBlank()) throw new IllegalArgumentException("City cannot be blank or null");
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        if(state == null || state.isBlank()) throw new IllegalArgumentException("State cannot be blank or null");
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        if(zipCode == null || zipCode.isBlank()) throw new IllegalArgumentException("ZipCode cannot be blank or null");
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        if(country == null || country.isBlank()) throw new IllegalArgumentException("Country cannot be blank or null");
        this.country = country;
    }

    public AddressType getType() {
        return type;
    }

    public void setType(AddressType type) {
        if(type == null) throw new IllegalArgumentException("Address Type cannot be blank or null");
        this.type = type;
    }
}

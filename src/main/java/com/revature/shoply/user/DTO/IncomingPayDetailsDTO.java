package com.revature.shoply.user.DTO;

import java.util.UUID;

public class IncomingPayDetailsDTO {

    private String cardNumber;
    private String cardHolderName;
    private String expireDate;
    private UUID addressId;
    private boolean isDefault;

    public IncomingPayDetailsDTO() {
    }

    public IncomingPayDetailsDTO(String cardNumber, String cardHolderName, String expireDate, UUID addressId, boolean isDefault) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expireDate = expireDate;
        this.addressId = addressId;
        this.isDefault = isDefault;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.isBlank()) throw new IllegalArgumentException("Card number cannot be blank or null");
        this.cardNumber = cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        if (cardHolderName == null || cardHolderName.isBlank()) throw new IllegalArgumentException("Card Holder Name cannot be blank or null");
        this.cardHolderName = cardHolderName;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        if (expireDate == null || expireDate.isBlank()) throw new IllegalArgumentException("Expire date cannot be blank or null");
        this.expireDate = expireDate;
    }

    public UUID getAddressId() {
        return addressId;
    }

    public void setAddressId(UUID addressId) {
        if (addressId == null) throw new IllegalArgumentException("AddressID cannot be null");
        this.addressId = addressId;
    }

    public boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }
}
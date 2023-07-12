package com.acima.model;

public class AuthRequest {

    private String locationId;
    private String merchantName;

    public AuthRequest() {
    }

    public AuthRequest(String locationId, String merchantName) {
        this.locationId = locationId;
        this.merchantName = merchantName;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
}

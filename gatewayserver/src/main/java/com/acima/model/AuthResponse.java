package com.acima.model;

public class AuthResponse {

    private String token;
    private String type = "Bearer";

    private QRCodeResponse qrDetails;

    public AuthResponse() {
    }

    public AuthResponse(String token, QRCodeResponse qrDetails) {
        this.token = token;
        this.qrDetails = qrDetails;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public QRCodeResponse getQrDetails() {
        return qrDetails;
    }

    public void setQrDetails(QRCodeResponse qrDetails) {
        this.qrDetails = qrDetails;
    }
}

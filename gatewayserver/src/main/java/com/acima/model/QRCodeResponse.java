package com.acima.model;

public class QRCodeResponse {

    private String requestId;
    private String locationGuid;
    private String utmSource;
    private String  utmMedium;
    private String utmCampaign;
    private String utmContent;
    private String utmTerm;

    private String utmGmid;

    public QRCodeResponse() {
    }

    public QRCodeResponse(String requestId, String locationGuid, String utmSource, String utmMedium, String utmCampaign, String utmContent, String utmTerm, String utmGmid) {
        this.requestId = requestId;
        this.locationGuid = locationGuid;
        this.utmSource = utmSource;
        this.utmMedium = utmMedium;
        this.utmCampaign = utmCampaign;
        this.utmContent = utmContent;
        this.utmTerm = utmTerm;
        this.utmGmid = utmGmid;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getLocationGuid() {
        return locationGuid;
    }

    public void setLocationGuid(String locationGuid) {
        this.locationGuid = locationGuid;
    }

    public String getUtmSource() {
        return utmSource;
    }

    public void setUtmSource(String utmSource) {
        this.utmSource = utmSource;
    }

    public String getUtmMedium() {
        return utmMedium;
    }

    public void setUtmMedium(String utmMedium) {
        this.utmMedium = utmMedium;
    }

    public String getUtmCampaign() {
        return utmCampaign;
    }

    public void setUtmCampaign(String utmCampaign) {
        this.utmCampaign = utmCampaign;
    }

    public String getUtmContent() {
        return utmContent;
    }

    public void setUtmContent(String utmContent) {
        this.utmContent = utmContent;
    }

    public String getUtmTerm() {
        return utmTerm;
    }

    public void setUtmTerm(String utmTerm) {
        this.utmTerm = utmTerm;
    }

    public String getUtmGmid() {
        return utmGmid;
    }

    public void setUtmGmid(String utmGmid) {
        this.utmGmid = utmGmid;
    }
}


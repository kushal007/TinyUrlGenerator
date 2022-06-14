package com.kushal.tinyurlgenerator.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Request object for POST method")
public class UrlRequestDto {

    @ApiModelProperty(required = true, notes = "Url to convert to short")
    private String longUrl;

    @ApiModelProperty(required = false, notes = "Custom short url provided by user")
    private String customTinyUrl; //optional

    @ApiModelProperty(required = false, notes = "Custom Expiration date provided by user")
    private String expirationDate; //optional

    public UrlRequestDto(String url, String customTinyUrl, String expirationDate) {
        this.longUrl = url;
        this.customTinyUrl = customTinyUrl;
        this.expirationDate = expirationDate;
    }

    public String getUrl() {
        return longUrl;
    }

    public void setUrl(String url) {
        this.longUrl = url;
    }

    public String getCustomTinyUrl() {
        return customTinyUrl;
    }

    public void setCustomTinyUrl(String customTinyUrl) {
        this.customTinyUrl = customTinyUrl;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "UrlDto{" +
                "longUrl='" + longUrl + '\'' +
                ", customTinyUrl='" + customTinyUrl + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                '}';
    }
}

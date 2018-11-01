package com.example.dell.booking_cyber.DTO;

import java.io.Serializable;

public class CyberGamingDTO implements Serializable {
    private Integer id;

    private String name;

    private String logo;

    private Integer accountId;

    private String address;

    private Double starAverage;

    private Double logitude;

    private Double latitude;

    private Boolean active;

    private Boolean deleted;

    public CyberGamingDTO(Integer id, String name, String logo, Integer accountId, String address, Double starAverage, Double logitude, Double latitude, Boolean active, Boolean deleted) {
        this.id = id;
        this.name = name;
        this.logo = logo;
        this.accountId = accountId;
        this.address = address;
        this.starAverage = starAverage;
        this.logitude = logitude;
        this.latitude = latitude;
        this.active = active;
        this.deleted = deleted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getStarAverage() {
        return starAverage;
    }

    public void setStarAverage(Double starAverage) {
        this.starAverage = starAverage;
    }

    public Double getLogitude() {
        return logitude;
    }

    public void setLogitude(Double logitude) {
        this.logitude = logitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        active = active;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        deleted = deleted;
    }
}

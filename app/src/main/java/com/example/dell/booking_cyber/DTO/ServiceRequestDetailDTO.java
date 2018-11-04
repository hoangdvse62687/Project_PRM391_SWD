package com.example.dell.booking_cyber.DTO;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class ServiceRequestDetailDTO implements Serializable {
    private Integer id;

    private Integer userId;

    private String username;

    private Integer cyberGamingId;

    private String cyberGamingName;

    private Double duration;

    private Integer numberOfServiceSlot;

    private Boolean done;

    private Boolean paid;

    private Date paidDate;

    private Date dateRequest;

    private Date goingDate;

    private String evaluation;

    private Integer star;

    private Double longitude;

    private Double latitude;

    private String code;

    private Double totalPrice;

    private Integer roomId;

    private String roomname;

    private Integer configurationId;

    private String configurationName;

    private Boolean approved;

    private Boolean active;

    private Boolean deleted;


    public ServiceRequestDetailDTO(Integer id, Integer userId, String username, Integer cyberGamingId, String cyberGamingName, Double duration, Integer numberOfServiceSlot, Boolean done, Boolean paid, Date paidDate, Date dateRequest, Date goingDate, String evaluation, Integer star, Double longitude, Double latitude, String code, Double totalPrice, Integer roomId, String roomname, Integer configurationId, String configurationName, Boolean approved, Boolean active, Boolean deleted) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.cyberGamingId = cyberGamingId;
        this.cyberGamingName = cyberGamingName;
        this.duration = duration;
        this.numberOfServiceSlot = numberOfServiceSlot;
        this.done = done;
        this.paid = paid;
        this.paidDate = paidDate;
        this.dateRequest = dateRequest;
        this.goingDate = goingDate;
        this.evaluation = evaluation;
        this.star = star;
        this.longitude = longitude;
        this.latitude = latitude;
        this.code = code;
        this.totalPrice = totalPrice;
        this.roomId = roomId;
        this.roomname = roomname;
        this.configurationId = configurationId;
        this.configurationName = configurationName;
        this.approved = approved;
        this.active = active;
        this.deleted = deleted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCyberGamingName() {
        return cyberGamingName;
    }

    public void setCyberGamingName(String cyberGamingName) {
        this.cyberGamingName = cyberGamingName;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Integer getNumberOfServiceSlot() {
        return numberOfServiceSlot;
    }

    public void setNumberOfServiceSlot(Integer numberOfServiceSlot) {
        this.numberOfServiceSlot = numberOfServiceSlot;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public Date getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    public Date getDateRequest() {
        return dateRequest;
    }

    public void setDateRequest(Date dateRequest) {
        this.dateRequest = dateRequest;
    }

    public Date getGoingDate() {
        return goingDate;
    }

    public void setGoingDate(Date goingDate) {
        this.goingDate = goingDate;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getRoomname() {
        return roomname;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }

    public String getConfigurationName() {
        return configurationName;
    }

    public void setConfigurationName(String configurationName) {
        this.configurationName = configurationName;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCyberGamingId() {
        return cyberGamingId;
    }

    public void setCyberGamingId(Integer cyberGamingId) {
        this.cyberGamingId = cyberGamingId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getConfigurationId() {
        return configurationId;
    }

    public void setConfigurationId(Integer configurationId) {
        this.configurationId = configurationId;
    }
}

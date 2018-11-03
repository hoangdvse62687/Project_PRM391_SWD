package com.example.dell.booking_cyber.DTO;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class ServiceRequestDTO implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer cyberGamingId;

    private Double duration;

    private Integer numberOfServiceSlot;

    private Boolean isDone;

    private Double isPaid;

    private LocalDateTime paidDate;

    private LocalDateTime dateRequest;

    private LocalDateTime goingDate;

    private String evaluation;

    private Integer star;

    private Double longitude;

    private Double latitude;

    private String code;

    private Double totalPrice;

    private Integer roomId;

    private Integer configurationId;

    private Boolean approved;

    private Boolean active;

    private Boolean deleted;

    public ServiceRequestDTO() {
    }

    public ServiceRequestDTO(Integer id, Integer userId, Integer cyberGamingId, Double duration, Integer numberOfServiceSlot, Boolean isDone, Double isPaid, LocalDateTime paidDate, LocalDateTime dateRequest, LocalDateTime goingDate, String evaluation, Integer star, Double longitude, Double latitude, String code, Double totalPrice, Integer roomId, Integer configurationId, Boolean approved, Boolean active, Boolean deleted) {
        this.id = id;
        this.userId = userId;
        this.cyberGamingId = cyberGamingId;
        this.duration = duration;
        this.numberOfServiceSlot = numberOfServiceSlot;
        this.isDone = isDone;
        this.isPaid = isPaid;
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
        this.configurationId = configurationId;
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
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public Double getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Double isPaid) {
        this.isPaid = isPaid;
    }

    public LocalDateTime getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(LocalDateTime paidDate) {
        this.paidDate = paidDate;
    }

    public LocalDateTime getDateRequest() {
        return dateRequest;
    }

    public void setDateRequest(LocalDateTime dateRequest) {
        this.dateRequest = dateRequest;
    }

    public LocalDateTime getGoingDate() {
        return goingDate;
    }

    public void setGoingDate(LocalDateTime goingDate) {
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
}

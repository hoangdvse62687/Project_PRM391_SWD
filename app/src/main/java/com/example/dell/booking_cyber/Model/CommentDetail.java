package com.example.dell.booking_cyber.Model;

import com.example.dell.booking_cyber.DTO.AccountDTO;
import com.example.dell.booking_cyber.DTO.CyberGamingDTO;

import java.io.Serializable;
import java.util.Date;

public class CommentDetail implements Serializable {
    private Integer cyberId;
    private AccountDTO User;
    private Integer rating;
    private String description;
    private Date timeComment;

    public CommentDetail(Integer cyberId, AccountDTO user, Integer rating, String description, Date timeComment) {
        this.cyberId = cyberId;
        User = user;
        this.rating = rating;
        this.description = description;
        this.timeComment = timeComment;
    }

    public Integer getCyberId() {
        return cyberId;
    }

    public void setCyberId(Integer cyberId) {
        this.cyberId = cyberId;
    }

    public AccountDTO getUser() {
        return User;
    }

    public void setUser(AccountDTO user) {
        User = user;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTimeComment() {
        return timeComment;
    }

    public void setTimeComment(Date timeComment) {
        this.timeComment = timeComment;
    }
}

package com.example.dell.booking_cyber.DTO;

import java.io.Serializable;

public class ImageDTO implements Serializable {
    private Integer id;

    private String imageURL;

    private Integer cyberGamingId;

    private Boolean active;

    private Boolean deleted;

    public ImageDTO(Integer id, String imageURL, Integer cyberGamingId, Boolean active, Boolean deleted) {
        this.id = id;
        this.imageURL = imageURL;
        this.cyberGamingId = cyberGamingId;
        this.active = active;
        this.deleted = deleted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Integer getCyberGamingId() {
        return cyberGamingId;
    }

    public void setCyberGamingId(Integer cyberGamingId) {
        this.cyberGamingId = cyberGamingId;
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

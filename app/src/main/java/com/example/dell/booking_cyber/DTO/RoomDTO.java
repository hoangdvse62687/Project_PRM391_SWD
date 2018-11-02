package com.example.dell.booking_cyber.DTO;

import java.io.Serializable;

public class RoomDTO implements Serializable {
    private Integer id;

    private String name;

    private String description;

    private Double price;

    private Integer cyber_gaming_id;

    private Boolean active;

    private Boolean deleted;

    public RoomDTO(Integer id, String name, String description, Double price, Integer cyber_gaming_id, Boolean active, Boolean deleted) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.cyber_gaming_id = cyber_gaming_id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCyber_gaming_id() {
        return cyber_gaming_id;
    }

    public void setCyber_gaming_id(Integer cyber_gaming_id) {
        this.cyber_gaming_id = cyber_gaming_id;
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

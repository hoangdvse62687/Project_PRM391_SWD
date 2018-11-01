package com.example.dell.booking_cyber.DTO;

import java.io.Serializable;

public class UserDTO implements Serializable {
    private Integer id;

    private Integer accountId;

    private String name;

    private String avatar;

    private String email;

    private String phone;

    private Double sex;

    private Boolean active;

    private Boolean deleted;

    public UserDTO() {
    }

    public UserDTO(String name, String avatar, String email, String phone, Double sex, Boolean active, Boolean deleted) {
        this.name = name;
        this.avatar = avatar;
        this.email = email;
        this.phone = phone;
        this.sex = sex;
        this.active = active;
        this.deleted = deleted;
    }

    public UserDTO(Integer id, Integer accountId, String name, String avatar, String email, String phone, Double sex, Boolean active, Boolean deleted) {
        this.id = id;
        this.accountId = accountId;
        this.name = name;
        this.avatar = avatar;
        this.email = email;
        this.phone = phone;
        this.sex = sex;
        this.active = active;
        this.deleted = deleted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getSex() {
        return sex;
    }

    public void setSex(Double sex) {
        this.sex = sex;
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

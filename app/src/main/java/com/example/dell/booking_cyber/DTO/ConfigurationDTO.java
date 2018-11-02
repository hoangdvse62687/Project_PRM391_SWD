package com.example.dell.booking_cyber.DTO;

import java.io.Serializable;

public class ConfigurationDTO implements Serializable{
    private Integer id;

    private String name;

    private Integer cyberGamingId;

    private String cpu;

    private String gpu;

    private String ram;

    private String mouse;

    private String keyboard;

    private String headphone;

    private Double price;

    private Boolean active;

    private Boolean delete;

    public ConfigurationDTO(Integer id, String name, Integer cyberGamingId, String cpu, String gpu, String ram, String mouse, String keyboard, String headphone, Double price, Boolean active, Boolean delete) {
        this.id = id;
        this.name = name;
        this.cyberGamingId = cyberGamingId;
        this.cpu = cpu;
        this.gpu = gpu;
        this.ram = ram;
        this.mouse = mouse;
        this.keyboard = keyboard;
        this.headphone = headphone;
        this.price = price;
        this.active = active;
        this.delete = delete;
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

    public Integer getCyberGamingId() {
        return cyberGamingId;
    }

    public void setCyberGamingId(Integer cyberGamingId) {
        this.cyberGamingId = cyberGamingId;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getMouse() {
        return mouse;
    }

    public void setMouse(String mouse) {
        this.mouse = mouse;
    }

    public String getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(String keyboard) {
        this.keyboard = keyboard;
    }

    public String getHeadphone() {
        return headphone;
    }

    public void setHeadphone(String headphone) {
        this.headphone = headphone;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getDeleted() {
        return delete;
    }

    public void setDeleted(Boolean deleted) {
        this.delete = deleted;
    }
}

package com.example.dim.wineroom.data.entities;

import java.io.Serializable;

public class Grower implements Serializable, Cloneable{

    private Integer id;
    private String grower_label;
    private String grower_addr;
    private String grower_addr1;
    private String grower_addr2;
    private String grower_city;
    private String grower_zip;
    private String grower_phone;
    private String grower_mail;

    public Grower() {
    }

    public Grower(String grower_label, String grower_addr, String grower_addr1, String grower_addr2, String grower_city, String grower_zip, String grower_phone, String grower_mail) {
        this.grower_label = grower_label;
        this.grower_addr = grower_addr;
        this.grower_addr1 = grower_addr1;
        this.grower_addr2 = grower_addr2;
        this.grower_city = grower_city;
        this.grower_zip = grower_zip;
        this.grower_phone = grower_phone;
        this.grower_mail = grower_mail;
    }

    public Grower(Integer id, String grower_label, String grower_addr, String grower_addr1, String grower_addr2, String grower_city, String grower_zip, String grower_phone, String grower_mail) {
        this.id = id;
        this.grower_label = grower_label;
        this.grower_addr = grower_addr;
        this.grower_addr1 = grower_addr1;
        this.grower_addr2 = grower_addr2;
        this.grower_city = grower_city;
        this.grower_zip = grower_zip;
        this.grower_phone = grower_phone;
        this.grower_mail = grower_mail;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGrower_label() {
        return grower_label;
    }

    public void setGrower_label(String grower_label) {
        this.grower_label = grower_label;
    }

    public String getGrower_addr() {
        return grower_addr;
    }

    public void setGrower_addr(String grower_addr) {
        this.grower_addr = grower_addr;
    }

    public String getGrower_addr1() {
        return grower_addr1;
    }

    public void setGrower_addr1(String grower_addr1) {
        this.grower_addr1 = grower_addr1;
    }

    public String getGrower_addr2() {
        return grower_addr2;
    }

    public void setGrower_addr2(String grower_addr2) {
        this.grower_addr2 = grower_addr2;
    }

    public String getGrower_city() {
        return grower_city;
    }

    public void setGrower_city(String grower_city) {
        this.grower_city = grower_city;
    }

    public String getGrower_zip() {
        return grower_zip;
    }

    public void setGrower_zip(String grower_zip) {
        this.grower_zip = grower_zip;
    }

    public String getGrower_phone() {
        return grower_phone;
    }

    public void setGrower_phone(String grower_phone) {
        this.grower_phone = grower_phone;
    }

    public String getGrower_mail() {
        return grower_mail;
    }

    public void setGrower_mail(String grower_mail) {
        this.grower_mail = grower_mail;
    }

    @Override
    public String toString() {
        return "Grower{" +
                "id=" + id +
                ", grower_label='" + grower_label + '\'' +
                ", grower_addr='" + grower_addr + '\'' +
                ", grower_addr1='" + grower_addr1 + '\'' +
                ", grower_addr2='" + grower_addr2 + '\'' +
                ", grower_city='" + grower_city + '\'' +
                ", grower_zip='" + grower_zip + '\'' +
                ", grower_phone='" + grower_phone + '\'' +
                ", grower_mail='" + grower_mail + '\'' +
                '}';
    }
}

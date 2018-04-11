package com.example.dim.wineroom.data.entities;

import java.io.Serializable;

public class User implements Serializable, Cloneable{

    private Integer id;
    private String user_name;
    private String user_lastname;
    private String user_mail;

    public User() {
    }

    public User(String user_name, String user_lastname, String user_mail) {
        this.user_name = user_name;
        this.user_lastname = user_lastname;
        this.user_mail = user_mail;
    }

    public User(Integer id, String user_name, String user_lastname, String user_mail) {
        this.id = id;
        this.user_name = user_name;
        this.user_lastname = user_lastname;
        this.user_mail = user_mail;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_lastname() {
        return user_lastname;
    }

    public void setUser_lastname(String user_lastname) {
        this.user_lastname = user_lastname;
    }

    public String getUser_mail() {
        return user_mail;
    }

    public void setUser_mail(String user_mail) {
        this.user_mail = user_mail;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", user_name='" + user_name + '\'' +
                ", user_lastname='" + user_lastname + '\'' +
                ", user_mail='" + user_mail + '\'' +
                '}';
    }
}

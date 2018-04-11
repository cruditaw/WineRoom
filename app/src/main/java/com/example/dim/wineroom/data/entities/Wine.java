package com.example.dim.wineroom.data.entities;

import java.io.Serializable;

public class Wine implements Serializable, Cloneable{

    private Integer id;
    private String wine_label;
    private String wine_yard;
    private String wine_year;
    private Grape wine_grape;
    private Grower wine_grower;

    public Wine() {
    }

    public Wine(String wine_label, String wine_yard, String wine_year, Grape wine_grape, Grower wine_grower) {
        this.wine_label = wine_label;
        this.wine_yard = wine_yard;
        this.wine_year = wine_year;
        this.wine_grape = wine_grape;
        this.wine_grower = wine_grower;
    }

    public Wine(Integer id, String wine_label, String wine_yard, String wine_year, Grape wine_grape, Grower wine_grower) {
        this.id = id;
        this.wine_label = wine_label;
        this.wine_yard = wine_yard;
        this.wine_year = wine_year;
        this.wine_grape = wine_grape;
        this.wine_grower = wine_grower;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWine_label() {
        return wine_label;
    }

    public void setWine_label(String wine_label) {
        this.wine_label = wine_label;
    }

    public String getWine_yard() {
        return wine_yard;
    }

    public void setWine_yard(String wine_yard) {
        this.wine_yard = wine_yard;
    }

    public String getWine_year() {
        return wine_year;
    }

    public void setWine_year(String wine_year) {
        this.wine_year = wine_year;
    }

    public Grape getWine_grape() {
        return wine_grape;
    }

    public void setWine_grape(Grape wine_grape) {
        this.wine_grape = wine_grape;
    }

    public Grower getWine_grower() {
        return wine_grower;
    }

    public void setWine_grower(Grower wine_grower) {
        this.wine_grower = wine_grower;
    }

    @Override
    public String toString() {
        return "Wine{" +
                "id=" + id +
                ", wine_label='" + wine_label + '\'' +
                ", wine_yard='" + wine_yard + '\'' +
                ", wine_year='" + wine_year + '\'' +
                ", wine_grape=" + wine_grape.toString() +
                ", wine_grower=" + wine_grower.toString() +
                '}';
    }
}

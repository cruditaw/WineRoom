package com.example.dim.wineroom.data.entities;

import java.io.Serializable;

public class Cellar implements Serializable, Cloneable{

    private Integer id;
    private Wine cellar_wine;
    private User cellar_user;
    private Integer cellar_qty;

    public Cellar() {
    }

    public Cellar(Integer id, Wine cellar_wine, User cellar_user, Integer cellar_qty) {
        this.id = id;
        this.cellar_wine = cellar_wine;
        this.cellar_user = cellar_user;
        this.cellar_qty = cellar_qty;
    }

    public Cellar(Wine cellar_wine, User cellar_user, Integer cellar_qty) {
        this.cellar_wine = cellar_wine;
        this.cellar_user = cellar_user;
        this.cellar_qty = cellar_qty;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Wine getCellar_wine() {
        return cellar_wine;
    }

    public void setCellar_wine(Wine cellar_wine) {
        this.cellar_wine = cellar_wine;
    }

    public User getCellar_user() {
        return cellar_user;
    }

    public void setCellar_user(User cellar_user) {
        this.cellar_user = cellar_user;
    }

    public Integer getCellar_qty() {
        return cellar_qty;
    }

    public void setCellar_qty(Integer cellar_qty) {
        this.cellar_qty = cellar_qty;
    }

    @Override
    public String toString() {
        return "Cellar{" +
                "id=" + id +
                ", cellar_wine='" + cellar_wine.toString() + '\'' +
                ", cellar_user='" + cellar_user.toString() + '\'' +
                ", cellar_qty='" + cellar_qty + '\'' +
                '}';
    }
}

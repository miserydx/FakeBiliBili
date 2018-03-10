package com.bilibili.model.bean.common;

/**
 * Created by miserydx on 18/3/3.
 */

public class EntranceIcon {
    private int id;

    private String name;

    private Entrance_icon entrance_icon;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setEntrance_icon(Entrance_icon entrance_icon) {
        this.entrance_icon = entrance_icon;
    }

    public Entrance_icon getEntrance_icon() {
        return this.entrance_icon;
    }

}
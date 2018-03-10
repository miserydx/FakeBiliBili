package com.bilibili.model.bean.common;

/**
 * Created by miserydx on 18/3/3.
 */

public class Partition {
    private int id;

    private String name;

    private String area;

    private Sub_icon sub_icon;

    private int count;

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

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea() {
        return this.area;
    }

    public void setSub_icon(Sub_icon sub_icon) {
        this.sub_icon = sub_icon;
    }

    public Sub_icon getSub_icon() {
        return this.sub_icon;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return this.count;
    }

    public class Sub_icon {
        private String src;

        private String height;

        private String width;

        public void setSrc(String src) {
            this.src = src;
        }

        public String getSrc() {
            return this.src;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getHeight() {
            return this.height;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public String getWidth() {
            return this.width;
        }

    }

}

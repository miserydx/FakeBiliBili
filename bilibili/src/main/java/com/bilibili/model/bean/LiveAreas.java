package com.bilibili.model.bean;

/**
 * Created by jiayiyang on 17/5/5.
 */

public class LiveAreas {

    private int id;

    private String name;

    private Entrance_icon entrance_icon;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setEntrance_icon(Entrance_icon entrance_icon){
        this.entrance_icon = entrance_icon;
    }
    public Entrance_icon getEntrance_icon(){
        return this.entrance_icon;
    }

    class Entrance_icon {
        private String src;

        private int height;

        private int width;

        public void setSrc(String src){
            this.src = src;
        }
        public String getSrc(){
            return this.src;
        }
        public void setHeight(int height){
            this.height = height;
        }
        public int getHeight(){
            return this.height;
        }
        public void setWidth(int width){
            this.width = width;
        }
        public int getWidth(){
            return this.width;
        }

    }

}

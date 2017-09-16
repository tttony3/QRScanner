package com.github.tttony3.qrscanner;

/**
 * Created by tony on 2017/9/17.
 */

class BomModel {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    String title;
    String num;
    String type;

    @Override
    public String toString() {
        return "BomModel{" +
                "title='" + title + '\'' +
                ", num='" + num + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}

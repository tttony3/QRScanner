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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getAtnum() {
        return atnum;
    }

    public void setAtnum(String atnum) {
        this.atnum = atnum;
    }

    public String getStnum() {
        return stnum;
    }

    public void setStnum(String stnum) {
        this.stnum = stnum;
    }

    public String getAt() {
        return at;
    }

    public void setAt(String at) {
        this.at = at;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getPosi() {
        return posi;
    }

    public void setPosi(String posi) {
        this.posi = posi;
    }

    String title;
    String num;
    String posi;
    String type;
    String model;
    String atnum;
    String stnum;
    String at;
    String st;

    @Override
    public String toString() {
        return "BomModel{" +
                "model='" + model + '\'' +
                ", at='" + at + '\'' +
                ", st='" + st + '\'' +
                '}';
    }
}

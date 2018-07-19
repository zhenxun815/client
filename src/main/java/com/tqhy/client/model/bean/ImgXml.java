package com.tqhy.client.model.bean;

import java.io.Serializable;

/**
 * @author Yiheng
 * @create 2018/6/21
 * @since 1.0.0
 */
public class ImgXml implements Serializable {

    private static final long serialVersionUID = 1L;
    public String name;
    public double score;
    public double x;
    public double y;
    public double width;
    public double height;

    public ImgXml() {
    }

    public ImgXml(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "ImgXml{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}

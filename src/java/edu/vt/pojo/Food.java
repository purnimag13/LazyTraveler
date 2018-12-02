/*
 * Created by William Goodwin on 2018.12.02  * 
 * Copyright © 2018 William Goodwin. All rights reserved. * 
 */
package edu.vt.pojo;

/**
 *
 * @author WBG
 */
public class Food {
    
    private String lat;
    private String lng;
    private String price;
    private String name;
    private String rating;

    public Food(String lat, String lng, String price, String name, String rating) {
        this.lat = lat;
        this.lng = lng;
        this.price = price;
        this.name = name;
        this.rating = rating;
    }

    public Food() {
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
    
    
    
}

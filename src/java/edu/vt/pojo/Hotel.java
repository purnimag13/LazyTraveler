/*
 * Created by William Goodwin on 2018.12.02  * 
 * Copyright © 2018 William Goodwin. All rights reserved. * 
 */
package edu.vt.pojo;

/**
 *
 * @author WBG
 */
public class Hotel {
    
    private String address;
    private String rating;
    private String priceLevel;
    private String name;
    private String icon;

    public Hotel(String address, String rating, String priceLevel, String name, String icon) {
        this.address = address;
        this.rating = rating;
        this.priceLevel = priceLevel;
        this.name = name;
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(String priceLevel) {
        this.priceLevel = priceLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
}

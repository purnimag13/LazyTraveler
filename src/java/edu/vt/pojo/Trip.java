/*
 * Created by William Goodwin on 2018.11.25  * 
 * Copyright © 2018 William Goodwin. All rights reserved. * 
 */
package edu.vt.pojo;
import java.util.List;

/**
 *
 * @author WBG
 */
public class Trip {
    
    private String tripType;
    private String latitude;
    private String longitude;
    private String name;
    private String cost;
    private List<String> photos;
    private List<Food> foodList;
    private List<Hotel> hotelList;
    private List<Flight> flightList;
    private Forecast fiveDayforecast;

    public Trip(String lat, String lon, String name, List<String> photoList) {
        this.photos = photoList;
        this.latitude = lat;
        this.longitude = lon;
        this.name = name;
        
        
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public Forecast getFiveDayforecast() {
        return fiveDayforecast;
    }

    public void setFiveDayforecast(Forecast fiveDayforecast) {
        this.fiveDayforecast = fiveDayforecast;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public List<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }

    
    
    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Hotel> getHotelList() {
        return hotelList;
    }

    public void setHotelList(List<Hotel> hotelList) {
        this.hotelList = hotelList;
    }

    public List<Flight> getFlightList() {
        return flightList;
    }

    public void setFlightList(List<Flight> flightList) {
        this.flightList = flightList;
    }
    
    
    
}

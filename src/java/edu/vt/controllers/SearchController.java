/*
 * Created by William Goodwin on 2018.11.15  * 
 * Copyright © 2018 William Goodwin. All rights reserved. * 
 */
package edu.vt.controllers;

import edu.vt.pojo.Trip;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author WBG
 */
@SessionScoped
@Named(value = "searchController")
public class SearchController implements Serializable {

    private List<Trip> locations;
    private String season;
    private Integer budget;
    private Integer numDays;

    public SearchController() {
    }

    /*
    =========================
    Getter and Setter Methods
    =========================
     */
    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public Integer getNumDays() {
        return numDays;
    }

    public void setNumDays(Integer numDays) {
        this.numDays = numDays;
    }

    /*
    ================
    Instance Methods
    ================
     */
    public String tripSearch() {

        return "";
    }

    public String location(String place) {
        String location = "";
        switch (place) {
            case "beach":
                break;
            case "city":
                break;
            case "mountains":
                break;
            case "desert":
                break;
            case "snow":
                break;
            default:
                break;
        }

        return location;
    }
    
    public List<Trip> beachLocations(){
        
        
        return null;
    }

}

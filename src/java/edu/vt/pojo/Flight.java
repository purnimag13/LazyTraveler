/*
 * Created by William Goodwin on 2018.12.02  * 
 * Copyright © 2018 William Goodwin. All rights reserved. * 
 */
package edu.vt.pojo;

import java.io.IOException;
import javax.faces.context.FacesContext;
/**
 *
 * @author WBG
 */
public class Flight {
    
    private String ticketUrl;
    private String cost;
    private String airline;

    public Flight(String ticketUrl, String cost) {
        this.ticketUrl = ticketUrl;
        this.cost = cost;
    }

    public String getTicketUrl() {
        return ticketUrl;
    }

    public void setTicketUrl(String ticketUrl) {
        this.ticketUrl = ticketUrl;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }
    
    
    
}

/*
 * Created by Purnima Ghosh on 2018.11.19  * 
 * Copyright © 2018 Purnima Ghosh. All rights reserved. * 
 */
package edu.vt.EntityBeans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author purni
 */
@Entity
@Table(name = "SavedTrips")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SavedTrips.findAll", query = "SELECT s FROM SavedTrips s")
    , @NamedQuery(name = "SavedTrips.findById", query = "SELECT s FROM SavedTrips s WHERE s.id = :id")
    , @NamedQuery(name = "SavedTrips.findByStartLocation", query = "SELECT s FROM SavedTrips s WHERE s.startLocation = :startLocation")
    , @NamedQuery(name = "SavedTrips.findByLocation", query = "SELECT s FROM SavedTrips s WHERE s.location = :location")
    , @NamedQuery(name = "SavedTrips.findByStartDate", query = "SELECT s FROM SavedTrips s WHERE s.startDate = :startDate")
    , @NamedQuery(name = "SavedTrips.findByEndDate", query = "SELECT s FROM SavedTrips s WHERE s.endDate = :endDate")
    , @NamedQuery(name = "SavedTrips.findByFlightCost", query = "SELECT s FROM SavedTrips s WHERE s.flightCost = :flightCost")
    , @NamedQuery(name = "SavedTrips.findByHotelName", query = "SELECT s FROM SavedTrips s WHERE s.hotelName = :hotelName")
    , @NamedQuery(name = "SavedTrips.findByHotelCost", query = "SELECT s FROM SavedTrips s WHERE s.hotelCost = :hotelCost")})
public class SavedTrips implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "start_location")
    private String startLocation;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "location")
    private String location;
    @Basic(optional = false)
    @NotNull
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "flight_cost")
    private double flightCost;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "hotel_name")
    private String hotelName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hotel_cost")
    private double hotelCost;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 16777215)
    @Column(name = "restaurants")
    private String restaurants;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 16777215)
    @Column(name = "events")
    private String events;

    public SavedTrips() {
    }

    public SavedTrips(Integer id) {
        this.id = id;
    }

    public SavedTrips(Integer id, String startLocation, String location, Date startDate, Date endDate, double flightCost, String hotelName, double hotelCost, String restaurants, String events) {
        this.id = id;
        this.startLocation = startLocation;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.flightCost = flightCost;
        this.hotelName = hotelName;
        this.hotelCost = hotelCost;
        this.restaurants = restaurants;
        this.events = events;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getFlightCost() {
        return flightCost;
    }

    public void setFlightCost(double flightCost) {
        this.flightCost = flightCost;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public double getHotelCost() {
        return hotelCost;
    }

    public void setHotelCost(double hotelCost) {
        this.hotelCost = hotelCost;
    }

    public String getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(String restaurants) {
        this.restaurants = restaurants;
    }

    public String getEvents() {
        return events;
    }

    public void setEvents(String events) {
        this.events = events;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SavedTrips)) {
            return false;
        }
        SavedTrips other = (SavedTrips) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.vt.EntityBeans.SavedTrips[ id=" + id + " ]";
    }
    
}

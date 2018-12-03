/*
 * Created by William Goodwin on 2018.11.15  * 
 * Copyright © 2018 William Goodwin. All rights reserved. * 
 */
package edu.vt.controllers;

import edu.vt.globals.Methods;
import edu.vt.managers.*;
import edu.vt.pojo.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

/**
 * APIUrl
 * https://maps.googleapis.com/maps/api/place/textsearch/json?input=California%20beach&inputtype=textquery&fields=photos,formatted_address,name,rating,geometry&key=AIzaSyCxSNfHEfZ3hpmUiygj_6Fvyhp_i1xHouw"
 *
 * @author WBG
 */
@SessionScoped
@Named(value = "searchController")
public class SearchController implements Serializable {

    ///These fields will be filled by the search dialog box.
    private String searchStr;
    private String tripType;
    private Integer budget;
    private Integer tripLen;
    private String startDate;
    private String startLocation;
    ////////
    
    private List<Trip> tripsList;

    public SearchController() {
    }

    /*
    =========================
    Getter and Setter Methods
    =========================
     */


    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public Integer getTripLen() {
        return tripLen;
    }

    public void setTripLen(Integer tripLen) {
        this.tripLen = tripLen;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public List<Trip> getTripsList() {
        return tripsList;
    }

    public void setTripsList(List<Trip> tripsList) {
        this.tripsList = tripsList;
    }

    public String getSearchStr() {
        return searchStr;
    }

    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    /*
    ================
    Instance Methods
    ================
     */
    public void performTripSearch() {
        String apiUrl = makeApiUrl();
        tripsList = initializeTrips(apiUrl);
        
        //Initilize all of the data fields
        this.populateFoodData(tripsList);

        
    }

    public List<Trip> initializeTrips(String apiUrl) {
        
        List<Trip> trips = new ArrayList();
        
        /*
        Redirecting to show a JSF page involves more than one subsequent requests and
        the messages would die from one request to another if not kept in the Flash scope.
        Since we will redirect to show the search Results page, we invoke preserveMessages().
         */
        //Methods.preserveMessages();

        /*
        JSON uses the following notation:
        { }    represents a JavaScript object as a Dictionary with Key:Value pairs
        [ ]    represents Array
        [{ }]  represents an Array of JavaScript objects (dictionaries)
        :    separates Key from the Value
         */
        try {
            // Obtain the JSON file from the searchApiUrl
            String searchJsonData = Methods.readUrlContent(apiUrl);

            JSONArray jsonArray;
            JSONObject jsonObject;

            char firstChar = searchJsonData.charAt(0);

            if (firstChar == '[') {
                // It is a JSON array
                jsonArray = new JSONArray(searchJsonData);
                jsonObject = jsonArray.getJSONObject(0);
            } else {
                // It is a JSON object
                jsonObject = new JSONObject(searchJsonData);
            }


            /*
            Take the results data and put it into a json array.
             */
            String resultsStr = jsonObject.optString("results", "");

            JSONArray resultsArray = new JSONArray(resultsStr);
            int resultLength = resultsArray.length();
            
            //Make a new trip option for each result returned
            for (int i = 0; i < resultLength; i++){
                JSONObject result = resultsArray.getJSONObject(i);
                
                //Get Lat and Lng from result
                JSONObject geometryObject = new JSONObject(result.optString("geometry", ""));
                JSONObject locationObject = new JSONObject(result.optString("location", ""));
                String lat = locationObject.optString("lat", "");
                String lng = locationObject.optString("lng", "");
                
                //Get name
                String name = result.optString("name", "");
                
                //Get photo reference
                JSONArray photosArray = new JSONArray(result.optString("photos", ""));
                int numPhotos = photosArray.length();
                List<String> photos = new ArrayList();
                for (int j = 0; j < numPhotos; j++){
                    JSONObject photoObject = photosArray.getJSONObject(j);
                    String photoRef = photoObject.optString("photo_reference", "");
                    photos.add("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" + photoRef + "&key=AIzaSyCxSNfHEfZ3hpmUiygj_6Fvyhp_i1xHouw");
                }
                
                Trip trip = new Trip(lat, lng, name, photos);
                trips.add(trip);
            }

        } catch (Exception e) {
            Methods.showMessage("Fatal Error", "Unrecognized Search Query!",
                    "The Countries API provides no data for the search query entered!");
            clear();
        }
        clear();

        return trips;

    }

    public String makeApiUrl() {
        String front = "https://maps.googleapis.com/maps/api/place/textsearch/json?input=";
        String end = "&inputtype=textquery&fields=photos,formatted_address,name,rating,geometry&key=AIzaSyCxSNfHEfZ3hpmUiygj_6Fvyhp_i1xHouw";

        String[] searchParams = searchStr.split(" ");
        int paramLen = searchParams.length;

        for (int i = 0; i < paramLen; i++) {
            if (i == paramLen - 1) {
                front += searchParams[i];
            } else {
                front += searchParams[i] + "%20";
            }
        }
        return front + end;
    }
    
    public void populateFoodData(List<Trip> trips){
        int numTrips = trips.size();
        FoodDataManager manager = new FoodDataManager();
        
        for (int i = 0; i < numTrips; i++){
            List<Food> foodList = manager.nearbyFood(trips.get(i));
            
            trips.get(i).setFoodList(foodList);
        }
        
        
        
    }
    
    public void clear(){
        
    }

}

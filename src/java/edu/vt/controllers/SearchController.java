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
import java.util.Collections;
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
    private String searchStr = "";
    private String locationTemp = "";
    private String tripType = "";
    private Integer budget;
    private Integer tripLen;
    private String startDate;
    private String startLocation;
    private String address;
    private String city;
    private Trip selected;
    private String description;
    ////////

    private List<Trip> tripsList;
    private List<Food> foodList;
    private List<Hotel> hotelList;

    private List<String> beachLocations = new ArrayList<>();
    private List<String> mountainLocations = new ArrayList<>();
    private List<String> desertLocations = new ArrayList<>();
    private List<String> snowLocations = new ArrayList<>();
    private List<String> forestLocations = new ArrayList<>();
    private List<String> cityLocations = new ArrayList<>();
    private List<String> finalLocations = new ArrayList<>();

    public SearchController() {

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    
    public String getLocationTemp() {
        return locationTemp;
    }

    public List<String> getBeachLocations() {
        return beachLocations;
    }

    public void setBeachLocations(List<String> beachLocations) {
        this.beachLocations = beachLocations;
    }

    public List<String> getMountainLocations() {
        return mountainLocations;
    }

    public void setMountainLocations(List<String> mountainLocations) {
        this.mountainLocations = mountainLocations;
    }

    public List<String> getDesertLocations() {
        return desertLocations;
    }

    public void setDesertLocations(List<String> desertLocations) {
        this.desertLocations = desertLocations;
    }

    public List<String> getSnowLocations() {
        return snowLocations;
    }

    public void setSnowLocations(List<String> snowLocations) {
        this.snowLocations = snowLocations;
    }

    public List<String> getForestLocations() {
        return forestLocations;
    }

    public void setForestLocations(List<String> forestLocations) {
        this.forestLocations = forestLocations;
    }

    public List<String> getCityLocations() {
        return cityLocations;
    }

    public void generateLocations() {
        beachLocations.add("California");
        beachLocations.add("Miami Beach");
        beachLocations.add("Florida");
        beachLocations.add("Bondi Beach");
        beachLocations.add("Daytona");
        beachLocations.add("Malibu");
        beachLocations.add("Cancun");
        beachLocations.add("Long Beach");

        mountainLocations.add("Andes");
        mountainLocations.add("Colorado");
        mountainLocations.add("Seattle");
        mountainLocations.add("Denver");
        mountainLocations.add("Vermont");
        mountainLocations.add("Great Smoky Mountains");

        desertLocations.add("Egypt");
        desertLocations.add("Africa");
        desertLocations.add("Sahara");
        desertLocations.add("Arizona");

        snowLocations.add("Canada");
        snowLocations.add("Vermont");
        snowLocations.add("Breckenridge");
        snowLocations.add("Switzerland");
        snowLocations.add("Russia");

        forestLocations.add("Orick");
        forestLocations.add("Eastsound");
        forestLocations.add("Gatlinburg");
        forestLocations.add("Thailand");

        cityLocations.add("Las Vegas");
        cityLocations.add("New York City");
        cityLocations.add("Paris");
        cityLocations.add("Washington");
        cityLocations.add("Beijing");

    }

    /*
    =========================
    Getter and Setter Methods
    =========================
     */

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    

    public Trip getSelected() {
        return selected;
    }

    public void setSelected(Trip selected) {
        this.selected = selected;
    }

    public List<Hotel> getHotelList() {
        return hotelList;
    }

    public void setHotelList(List<Hotel> hotelList) {
        this.hotelList = hotelList;
    }

    public List<String> getFinalLocations() {
        return finalLocations;
    }

    public void setFinalLocations(List<String> finalLocations) {
        this.finalLocations = finalLocations;
    }

    public void setCityLocations(List<String> cityLocations) {
        this.cityLocations = cityLocations;
    }

    public void setLocationTemp(String locationTemp) {
        this.locationTemp = locationTemp;
    }

    public List<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }

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
    
    public List<Food> getTripFood(Trip trip) {
        List<Trip> tripList = getTripsList();
        String tripName = trip.getName();
        for (Trip i : tripList) {
            if (i.getName().equals(tripName)) {
                return i.getFoodList();
            }
        }
        return null;
    }

    public List<Hotel> getTripHotel(Trip trip){
        List<Trip> tripList = getTripsList();
        String tripName = trip.getName();
        for (Trip i : tripList) {
            if (i.getName().equals(tripName)){
                return i.getHotelList();
            }
        }
        return null;
    }
    public String performTripSearch() {
        this.makeLocationChoice();
        List<Trip> trips = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            searchStr = finalLocations.get(i);
            String apiUrl = makeApiUrl();
            tripsList = initializeTrips(apiUrl, trips);
            foodList = populateFoodData(tripsList);
            hotelList = populateHotelData(tripsList);
        }
        //Initilize all of the data fields
        return "/takeTrip/Trip?faces-redirect=true";

    }

    public void makeLocationChoice() {
        this.generateLocations();
        if (locationTemp.equals("Beach")) {
            List<String> copy = new ArrayList<>(beachLocations);
            Collections.shuffle(copy);
            copy.subList(0, 3);
            for (int i = 0; i < 3; i++) {
                finalLocations.add(copy.get(i));
            }
        } else if (locationTemp.equals("Desert")) {
            List<String> copy = new ArrayList<>(desertLocations);
            Collections.shuffle(copy);
            copy.subList(0, 3);
            for (int i = 0; i < 3; i++) {
                finalLocations.add(copy.get(i));
            }
        } else if (locationTemp.equals("Mountain")) {
            List<String> copy = new ArrayList<>(mountainLocations);
            Collections.shuffle(copy);
            copy.subList(0, 3);
            for (int i = 0; i < 3; i++) {
                finalLocations.add(copy.get(i));
            }
        } else if (locationTemp.equals("Snow")) {
            List<String> copy = new ArrayList<>(snowLocations);
            Collections.shuffle(copy);
            copy.subList(0, 3);
            for (int i = 0; i < 3; i++) {
                finalLocations.add(copy.get(i));
            }
        } else if (locationTemp.equals("Forest")) {
            List<String> copy = new ArrayList<>(forestLocations);
            Collections.shuffle(copy);
            copy.subList(0, 3);
            for (int i = 0; i < 3; i++) {
                finalLocations.add(copy.get(i));
            }
        } else if (locationTemp.equals("City")) {
            List<String> copy = new ArrayList<>(cityLocations);
            Collections.shuffle(copy);
            copy.subList(0, 3);
            for (int i = 0; i < 3; i++) {
                finalLocations.add(copy.get(i));
            }
        }

    }

    public List<Trip> initializeTrips(String apiUrl, List<Trip> trips) {

//        List<Trip> trips = new ArrayList();
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
            for (int i = 0; i < resultLength; i++) {
                JSONObject result = resultsArray.getJSONObject(i);

                //Get Lat and Lng from result
                JSONObject geometryObject = new JSONObject(result.optString("geometry", ""));
                JSONObject locationObject = new JSONObject(geometryObject.optString("location", ""));
                String lat = locationObject.optString("lat", "");
                String lng = locationObject.optString("lng", "");
                //Get name
                String name = result.optString("name", "");

                //Get photo reference
                JSONArray photosArray = new JSONArray(result.optString("photos", ""));
                int numPhotos = photosArray.length();
                List<String> photos = new ArrayList();
                for (int j = 0; j < numPhotos; j++) {
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

    public List<Food> populateFoodData(List<Trip> trips) {
        int numTrips = trips.size();
        FoodDataManager manager = new FoodDataManager();

        for (int i = 0; i < numTrips; i++) {
            foodList = manager.nearbyFood(trips.get(i));

            trips.get(i).setFoodList(foodList);
        }

        return foodList;
    }

    public List<Hotel> populateHotelData(List<Trip> trips) {
        int numTrips = trips.size();
        HotelDataManager manager = new HotelDataManager();

        for (int i = 0; i < numTrips; i++) {
            hotelList = manager.nearbyHotels(trips.get(i));

            trips.get(i).setHotelList(hotelList);
        }

        return hotelList;
    }

    public void clear() {

    }

}

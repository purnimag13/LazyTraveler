/*
 * Created by William Goodwin on 2018.11.16  * 
 * Copyright © 2018 William Goodwin. All rights reserved. * 
 */
package edu.vt.managers;

import edu.vt.globals.Methods;
import edu.vt.pojo.Food;
import edu.vt.pojo.Trip;
import java.util.ArrayList;
import java.util.List;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

/**
 *
 * @author WBG
 */
public class FoodDataManager {


    private String apiUrl;

    public FoodDataManager() {
        //Nothin gto initialize
    }

    public List<Food> nearbyFood(Trip trip){
        
        List<Food> foodList = new ArrayList();
        String tripLat = trip.getLatitude();
        String tripLng = trip.getLongitude();
        
        apiUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + tripLat + "," + tripLng + "&rankby=distance&type=food&keyword=food&key=AIzaSyCxSNfHEfZ3hpmUiygj_6Fvyhp_i1xHouw";
        
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
                JSONObject locationObject = new JSONObject(geometryObject.optString("location", ""));
                String lat = locationObject.optString("lat", "");
                String lng = locationObject.optString("lng", "");
                
                //Get name
                String name = result.optString("name", "");
                
                //Get price level
                String priceLevel = " ";
                if (result.optString ("price_level", "") == null)
                {
                    priceLevel = "not available";
                }
                else
                {
                    priceLevel = result.optString ("price_level", "");
                }
                //Get Rating 
                String rating = result.optString ("rating", "");
                
                foodList.add(new Food(lat, lng, priceLevel, name, rating));
            }
           

        } catch (Exception e) {
            Methods.showMessage("Fatal Error", "Unrecognized Search Query!",
                    "The Countries API provides no data for the search query entered!");
            clear();
        }
        clear();

        return foodList;

        
    }
    
    
    public void clear(){
        
    }
    
}
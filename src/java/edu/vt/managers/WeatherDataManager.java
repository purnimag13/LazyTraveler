/*
 * Created by William Goodwin on 2018.11.16  * 
 * Copyright © 2018 William Goodwin. All rights reserved. * 
 */
package edu.vt.managers;

import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;
import edu.vt.globals.Methods;

/**
 * API Key	lbss2nJoDvorl5feDodU6e9UFzeCvo8t
 *
 * @author WBG
 */
public class WeatherDataManager {

    private String latitude;
    private String longitude;
    private String locationApiUrl;
    private String weatherApiUrl;
    private String locationId;

    public WeatherDataManager() {
        //nothing to initialize
    }

    
    
    public void locationId() {
        String apiUrl = "http://dataservice.accuweather.com/locations/v1/cities/geoposition/search?apikey=lbss2nJoDvorl5feDodU6e9UFzeCvo8t&q=" + latitude + "%2C%20" + longitude;

        /*
        Redirecting to show a JSF page involves more than one subsequent requests and
        the messages would die from one request to another if not kept in the Flash scope.
        Since we will redirect to show the search Results page, we invoke preserveMessages().
         */
        Methods.preserveMessages();

        /*
        JSON uses the following notation:
        { }    represents a JavaScript object as a Dictionary with Key:Value pairs
        [ ]    represents Array
        [{ }]  represents an Array of JavaScript objects (dictionaries)
        :    separates Key from the Value
         */
        try {
            // Obtain the JSON file from the searchApiUrl
            String searchResultsJsonData = Methods.readUrlContent(apiUrl);

            JSONArray jsonArray;
            JSONObject jsonObject;

            /*
            Country Full Name or Capital City Name search returns a JSON array.
            Country Code, 2 letters or 3 letters, search returns a JSON object.
            Therefore, we do the processing by testing the first character.
             */
            char firstChar = searchResultsJsonData.charAt(0);

            if (firstChar == '[') {
                // It is a JSON array
                jsonArray = new JSONArray(searchResultsJsonData);
                jsonObject = jsonArray.getJSONObject(0);
            } else {
                // It is a JSON object
                jsonObject = new JSONObject(searchResultsJsonData);
            }


            /*
            Try to get the array in the JSON data.
             */
            String hits = jsonObject.optString("hits", "");
            JSONArray hitsJsonArray = new JSONArray(hits);

        } catch (Exception e) {
            Methods.showMessage("Fatal Error", "Unrecognized Search Query!",
                    "The Countries API provides no data for the search query entered!");
            clear();
        }

    }
    
    
    public void fiveDayForcast(){
        weatherApiUrl  = "http://dataservice.accuweather.com/forecasts/v1/daily/5day/" + locationId + "?apikey=lbss2nJoDvorl5feDodU6e9UFzeCvo8t";
    }
    
    
    /**
     * Clears the search values.
     */
    public void clear() {
        locationId = null;
        latitude = null;
        longitude = null;
    }
}

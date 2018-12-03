/*
 * Created by William Goodwin on 2018.11.24  * 
 * Copyright © 2018 William Goodwin. All rights reserved. * 
 */
package edu.vt.managers;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import edu.vt.globals.Methods;
import edu.vt.pojo.Flight;
import edu.vt.pojo.Food;
import edu.vt.pojo.Trip;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * aee14b-2470d6
 *
 * @author WBG
 */
public class FlightDataManager {

    public List<Flight> findFlights(String inboundDate, String outboudDate, String cabinClass, Trip trip, String startLocation) {
        try {

            HttpResponse<JsonNode> response = Unirest.post("https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/pricing/v1.0")
                    .header("X-RapidAPI-Key", "fsr5w8PpIrmshHMJMjS4tzJnByRcp1m3ltPjsn0cwo7Tq3WwLT")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .field("inboundDate", "2019-01-10")
                    .field("cabinClass", "business")
                    .field("children", 0)
                    .field("infants", 0)
                    .field("groupPricing", "false")
                    .field("country", "US")
                    .field("currency", "USD")
                    .field("locale", "en-US")
                    .field("originPlace", "SFO-sky")
                    .field("destinationPlace", "LHR-sky")
                    .field("outboundDate", "2019-01-01")
                    .field("adults", 1)
                    .asJson();
            String locationStr = response.getHeaders().getFirst("Location");
            System.out.println(locationStr);

            //http://partners.api.skyscanner.net/apiservices/pricing/uk1/v1.0/e38fe850-15b4-444c-a001-25791a607c83
            // the below code retrives the session key at the end of this url.
            int locationLen = locationStr.split("/").length;
            String sessionKey = locationStr.split("/")[locationLen - 1];

            System.out.println(sessionKey);

            HttpResponse<JsonNode> flightResponse = Unirest.get("https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/pricing/uk2/v1.0/" + sessionKey + "?pageIndex=0&pageSize=10")
                    .header("X-RapidAPI-Key", "fsr5w8PpIrmshHMJMjS4tzJnByRcp1m3ltPjsn0cwo7Tq3WwLT")
                    .asJson();

            JsonNode responseBody = flightResponse.getBody();
            JSONArray responseArray;
            JSONObject responseObject;
            if (responseBody.isArray()) {
                responseArray = responseBody.getArray();
            } else {
                responseObject = responseBody.getObject();
            }

        } catch (UnirestException ex) {
            Logger.getLogger(FlightDataManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void findAirportCodes(String location) {
        
        String[] coord = this.startCoordinates(location);
        String startAirport = "http://aviation-edge.com/v2/public/nearby?key=aee14b-2470d6&lat=" + coord[0] + "&lng=" + coord[1] + "&distance=100";

        
    }

    public String[] startCoordinates(String location) {

        String[] coordinates = new String[2];
        
        String front = "https://maps.googleapis.com/maps/api/place/textsearch/json?input=";
        String end = "&inputtype=textquery&fields=formatted_address,geometry&key=AIzaSyCxSNfHEfZ3hpmUiygj_6Fvyhp_i1xHouw";

        String[] searchParams = location.split(" ");
        int paramLen = searchParams.length;
        for (int i = 0; i < paramLen; i++) {
            if (i == paramLen - 1) {
                front += searchParams[i];
            } else {
                front += searchParams[i] + "%20";
            }
        }

        String apiUrl = front + end;

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

            org.primefaces.json.JSONArray jsonArray;
            org.primefaces.json.JSONObject jsonObject;

            char firstChar = searchJsonData.charAt(0);

            if (firstChar == '[') {
                // It is a JSON array
                jsonArray = new org.primefaces.json.JSONArray(searchJsonData);
                jsonObject = jsonArray.getJSONObject(0);
            } else {
                // It is a JSON object
                jsonObject = new org.primefaces.json.JSONObject(searchJsonData);
            }

            /*
            Take the results data and put it into a json array.
             */
            String resultsStr = jsonObject.optString("results", "");

            org.primefaces.json.JSONArray resultsArray = new org.primefaces.json.JSONArray(resultsStr);
            int resultLength = resultsArray.length();

            //Make a new trip option for each result returned
            org.primefaces.json.JSONObject result = resultsArray.getJSONObject(0);

            //Get Lat and Lng from result
            org.primefaces.json.JSONObject geometryObject = new org.primefaces.json.JSONObject(result.optString("geometry", ""));
            org.primefaces.json.JSONObject locationObject = new org.primefaces.json.JSONObject(result.optString("location", ""));
            String lat = locationObject.optString("lat", "");
            String lng = locationObject.optString("lng", "");
            
            coordinates[0] = lat;
            coordinates[1] = lng;

        } catch (Exception e) {
            Methods.showMessage("Fatal Error", "Unrecognized Search Query!",
                    "The Countries API provides no data for the search query entered!");
            clear();
        }
        clear();

        
        return coordinates;

    }
    
    public void clear(){
        
    }
    
}

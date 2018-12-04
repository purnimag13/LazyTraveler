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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * aee14b-2470d6
 *
 * @author WBG
 */

@Named(value = "flightDataManager")

public class FlightDataManager {

    private List<String> departureCodes;
    private List<String> arrivalCodes;
    private List<Flight> flightList;
    private String address;
    private String city;
    private String startDate;
    private String startLocation;
    private String inboundDate;
    private String outboundDate;

    
    
    
    /**
     * Call this method to populate the departureCodes List.
     *
     * @param lat
     * @param lng
     */
    public void findDepartureCodes(String lat, String lng) {
        String apiUrl = "http://aviation-edge.com/v2/public/nearby?key=aee14b-2470d6&lat=" + lat + "&lng=" + lng + "&distance=200";

        departureCodes = new ArrayList();

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

            // It is a JSON array
            jsonArray = new org.primefaces.json.JSONArray(searchJsonData);
            int resultLength = jsonArray.length();

            //Make a new trip option for each result returned
            for (int i = 0; i < resultLength; i++) {
                org.primefaces.json.JSONObject result = jsonArray.getJSONObject(i);

                String airportCode = result.optString("codeIataAirport", "");
                departureCodes.add(airportCode);

            }

        } catch (Exception e) {
            Methods.showMessage("Fatal Error", "Unrecognized Search Query!",
                    "The Countries API provides no data for the search query entered!");
        }

    }

    public void findArrivalCodes(Trip trip) {
        String lat = trip.getLatitude();
        String lng = trip.getLongitude();

        String apiUrl = "http://aviation-edge.com/v2/public/nearby?key=aee14b-2470d6&lat=" + lat + "&lng=" + lng + "&distance=200";

        arrivalCodes = new ArrayList();

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

            // It is a JSON array
            jsonArray = new org.primefaces.json.JSONArray(searchJsonData);
            int resultLength = jsonArray.length();

            //Make a new trip option for each result returned
            for (int i = 0; i < resultLength; i++) {
                org.primefaces.json.JSONObject result = jsonArray.getJSONObject(i);

                String airportCode = result.optString("codeIataAirport", "");
                arrivalCodes.add(airportCode);

            }

        } catch (Exception e) {
            Methods.showMessage("Fatal Error", "Unrecognized Search Query!",
                    "The Countries API provides no data for the search query entered!");
        }

    }
    
    public void formatStartLocation(String add, String city){
        String start = add + " " + city;
        this.startLocation = start;
    }
    
    public void formatInboundDate(String date){
        String inbound = date.substring(0, 2) + "-" + date.substring(3, 5) + "-" + date.substring(6, 10);
        this.inboundDate = inbound;
    }
    
    public void formatOutboundDate(String tripLength, String inboundDate){
        int endDate = ((Integer.parseInt((inboundDate.substring(3, 5))) + Integer.parseInt(tripLength)) % 30);
        this.outboundDate = (String)(endDate + "");
    }
       
    
    
    public List<Flight> findFlights(String inboundDate, String outboundDate, Trip trip, String startLocation) {
        
        String[] coords = this.startCoordinates(startLocation);
        this.findDepartureCodes(coords[0], coords[1]);
        this.findArrivalCodes(trip);
        
        try {
            int departListSize = departureCodes.size();
            int arrivalListSize = arrivalCodes.size();
            
            for (int i = 0; i < departListSize; i++) {
                for (int j = 0; j < arrivalListSize; j++) {

                    HttpResponse<JsonNode> response = Unirest.post("https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/pricing/v1.0")
                            .header("X-RapidAPI-Key", "fsr5w8PpIrmshHMJMjS4tzJnByRcp1m3ltPjsn0cwo7Tq3WwLT")
                            .header("Content-Type", "application/x-www-form-urlencoded")
                            .field("inboundDate", inboundDate)
                            .field("children", 0)
                            .field("infants", 0)
                            .field("groupPricing", "false")
                            .field("country", "US")
                            .field("currency", "USD")
                            .field("locale", "en-US")
                            .field("originPlace", departureCodes.get(i) + "-sky")
                            .field("destinationPlace", arrivalCodes.get(j) + "-sky")
                            .field("outboundDate", outboundDate)
                            .field("adults", 1)
                            .asJson();
                    String locationStr = response.getHeaders().getFirst("Location");

                    //CmRaAAAAUZWsp1RaxwtIv_rBGxUbH0VdpTHzBlzIUODuam9g34bIVuVS4Nx0q2qdEwfUolywT0Pk0mjAMnhTo-0Qa41DVrc3FzIfVKumVUeHRTHYGksy7rLDy2f23QLxutK3pdqQEhCTHMQILcYlc3SFQPVkyZ71GhQhsBn-yW5mRKSAjwCZbgyx0LNpKg
                    //http://partners.api.skyscanner.net/apiservices/pricing/uk1/v1.0/e38fe850-15b4-444c-a001-25791a607c83
                    // the below code retrives the session key at the end of this url.
                    int locationLen = locationStr.split("/").length;
                    String sessionKey = locationStr.split("/")[locationLen - 1];

                    HttpResponse<JsonNode> flightResponse = Unirest.get("https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/pricing/uk2/v1.0/" + sessionKey + "?pageIndex=0&pageSize=10")
                            .header("X-RapidAPI-Key", "fsr5w8PpIrmshHMJMjS4tzJnByRcp1m3ltPjsn0cwo7Tq3WwLT")
                            .asJson();
                    JsonNode responseBody = flightResponse.getBody();
                    
                    JSONObject results = responseBody.getObject();
                    JSONArray flightsArray = new JSONArray(results.optString("Itineraries", ""));
                    if (flightsArray.length() != 0){
                        
                        for (int k = 0; k < 1; k++){
                            
                            JSONObject itinerary = flightsArray.getJSONObject(k);
                            JSONArray pricingArray = itinerary.getJSONArray("PricingOptions");
                            JSONObject flightInfo = pricingArray.getJSONObject(0);
                            
                            String price = flightInfo.optString("Price", "");
                            String url = flightInfo.optString("DeeplinkUrl", "");
                            
                            flightList.add(new Flight(url, price));
                            
                        }
                        
                    }

                }
            }

        } catch (UnirestException ex) {
            Logger.getLogger(FlightDataManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(FlightDataManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flightList;
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
            org.primefaces.json.JSONObject locationObject = new org.primefaces.json.JSONObject(geometryObject.optString("location", ""));
            String lat = locationObject.optString("lat", "");
            String lng = locationObject.optString("lng", "");

            coordinates[0] = lat;
            coordinates[1] = lng;

        } catch (Exception e) {
            Methods.showMessage("Fatal Error", "Unrecognized Search Query!",
                    "The Countries API provides no data for the search query entered!");
        }

        return coordinates;

    }



}

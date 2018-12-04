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
    private String state;
    private String startDate = " ";
    private String startLocation = " ";
    private String inboundDate;
    private String outboundDate;
    private Integer tripLen;

    public FlightDataManager() {
    }

    public FlightDataManager(Integer tripLength, String address, String city, String state, String startDate) {
        this.tripLen = tripLength;
        this.city = city;
        this.address = address;
        this.state = state;
        this.startDate = startDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<String> getDepartureCodes() {
        return departureCodes;
    }

    public void setDepartureCodes(List<String> departureCodes) {
        this.departureCodes = departureCodes;
    }

    public List<String> getArrivalCodes() {
        return arrivalCodes;
    }

    public void setArrivalCodes(List<String> arrivalCodes) {
        this.arrivalCodes = arrivalCodes;
    }

    public List<Flight> getFlightList() {
        return flightList;
    }

    public void setFlightList(List<Flight> flightList) {
        this.flightList = flightList;
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

    public String getInboundDate() {
        return inboundDate;
    }

    public void setInboundDate(String inboundDate) {
        this.inboundDate = inboundDate;
    }

    public String getOutboundDate() {
        return outboundDate;
    }

    public void setOutboundDate(String outboundDate) {
        this.outboundDate = outboundDate;
    }

    public Integer getTripLen() {
        return tripLen;
    }

    public void setTripLen(Integer tripLen) {
        this.tripLen = tripLen;
    }


    public void findDepartureCodes(String state) {
        
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
            
            HttpResponse<JsonNode> response = Unirest.get("https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/autosuggest/v1.0/US/USD/en-US/?query=" + state)
                .header("X-RapidAPI-Key", "fsr5w8PpIrmshHMJMjS4tzJnByRcp1m3ltPjsn0cwo7Tq3WwLT")
                .asJson();

            JSONObject result = response.getBody().getObject();
            JSONArray places = new JSONArray(result.optString("Places", ""));

            int placesLen = places.length();
            int maxIndex = 3;
            if (placesLen < 3){
                maxIndex = placesLen;
            }
            //Make a new trip option for each result returned
            for (int i = 0; i < maxIndex; i++) {
 
                JSONObject placeObject = places.getJSONObject(i);
                
                String code = placeObject.optString("PlaceId", "");
                System.out.println(code);
                departureCodes.add(code);

            }

        } catch (UnirestException | JSONException e) {
            Methods.showMessage("Fatal Error", "Unrecognized Search Query!",
                    "The Countries API provides no data for the search query entered!");
        }

    }

    public void findArrivalCodes(Trip trip) {
        
        arrivalCodes = new ArrayList();
        String tripLocation = this.getCountryName(trip);

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
            
            HttpResponse<JsonNode> response = Unirest.get("https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/autosuggest/v1.0/US/USD/en-US/?query=" + tripLocation)
                .header("X-RapidAPI-Key", "fsr5w8PpIrmshHMJMjS4tzJnByRcp1m3ltPjsn0cwo7Tq3WwLT")
                .asJson();


            JSONObject result = response.getBody().getObject();
            JSONArray places = new JSONArray(result.optString("Places", ""));

            int placesLen = places.length();
            int maxIndex = 3;
            if (placesLen < 3){
                maxIndex = placesLen;
            }
            //Make a new trip option for each result returned
            for (int i = 0; i < maxIndex; i++) {
 
                JSONObject placeObject = places.getJSONObject(i);
                String code = placeObject.optString("PlaceId", "");

                departureCodes.add(code);

            }

        } catch (UnirestException | JSONException e) {
            Methods.showMessage("Fatal Error", "Unrecognized Search Query!",
                    "The Countries API provides no data for the search query entered!");
        }

    }

    public void formatStartLocation(String add, String city) {
        String start = add + " " + city;
        this.startLocation = start;
    }

    public void formatInboundDate(String date) {
        String[] temp = date.split(" ");
        String monthAsNum = " ";
        if (temp[1].equals("Jan")) {
            monthAsNum = "01";
        } else if (temp[1].equals("Feb")) {
            monthAsNum = "02";
        } else if (temp[1].equals("Mar")) {
            monthAsNum = "03";
        } else if (temp[1].equals("Apr")) {
            monthAsNum = "04";
        } else if (temp[1].equals("May")) {
            monthAsNum = "05";
        } else if (temp[1].equals("Jun")) {
            monthAsNum = "06";
        } else if (temp[1].equals("Jul")) {
            monthAsNum = "07";
        } else if (temp[1].equals("Aug")) {
            monthAsNum = "08";
        } else if (temp[1].equals("Sep")) {
            monthAsNum = "09";
        } else if (temp[1].equals("Oct")) {
            monthAsNum = "10";
        } else if (temp[1].equals("Nov")) {
            monthAsNum = "11";
        } else if (temp[1].equals("Dec")) {
            monthAsNum = "12";
        }
        String inbound = temp[5] + "-" + monthAsNum + "-" + temp[2];
        this.inboundDate = inbound;
    }

    public void formatOutboundDate(Integer tripLength, String inboundDate) {
        String[] temp = inboundDate.split("-");
        Integer day = Integer.parseInt(temp[2]);
        Integer month = Integer.parseInt(temp[1]);
        Integer year = Integer.parseInt(temp[0]);
        if (day + tripLength > 30) {
            if (month + 1 > 12) {
                year += 1;
            } else {
                month += 1;
            }
        } else {
            day += tripLength;
        }
        StringBuilder build = new StringBuilder();
        build.append(year);
        build.append("-");
        build.append(month);
        build.append("-");
        build.append(day);
        this.outboundDate = build.toString();
    }

    public List<Flight> findFlights(Trip trip) {
        flightList = new ArrayList<>();
        this.formatInboundDate(startDate);
        this.formatOutboundDate(tripLen, inboundDate);
        this.formatStartLocation(address, city);

        this.findDepartureCodes(state);
        this.findArrivalCodes(trip);
        int departListSize = this.departureCodes.size();
        int arrivalListSize = this.arrivalCodes.size();
        for (int i = 0; i < departListSize; i++) {
            for (int j = 0; j < arrivalListSize; j++) {
                try {

                    HttpResponse<JsonNode> response = Unirest.post("https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/pricing/v1.0")
                            .header("X-RapidAPI-Key", "fsr5w8PpIrmshHMJMjS4tzJnByRcp1m3ltPjsn0cwo7Tq3WwLT")
                            .header("Content-Type", "application/x-www-form-urlencoded")
                            .field("cabinClass", "business")
                            .field("inboundDate", outboundDate)
                            .field("children", 0)
                            .field("infants", 0)
                            .field("groupPricing", "false")
                            .field("country", "US")
                            .field("currency", "USD")
                            .field("locale", "en-US")
                            .field("originPlace", "SFO-sky")
                            .field("destinationPlace", "LHR-sky")
                            .field("outboundDate", inboundDate)
                            .field("adults", 1)
                            .asJson();
                    String locationStr = response.getHeaders().getFirst("Location");
                    if (locationStr == null)
                    {
                        continue;
                    }

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
                    if (flightsArray.length() != 0) {

                        for (int k = 0; k < 1; k++) {

                            JSONObject itinerary = flightsArray.getJSONObject(k);
                            JSONArray pricingArray = itinerary.getJSONArray("PricingOptions");
                            JSONObject flightInfo = pricingArray.getJSONObject(0);

                            String price = flightInfo.optString("Price", "");
                            String url = flightInfo.optString("DeeplinkUrl", "");
                            
                            
                            Flight temp = new Flight(url, price);
                            flightList.add(temp);

                        }

                    }

                }
        catch ( UnirestException | JSONException ex) 
                {
            Logger.getLogger(FlightDataManager.class.getName()).log(Level.SEVERE, null, ex);
        }
       }
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

    public String getCountryName(Trip trip){
        String lat = trip.getLatitude();
        String lng = trip.getLongitude();
        
        String countryName = "";
        String state = "";
        
        String apiUrl = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + "," + lng + "&key=AIzaSyCxSNfHEfZ3hpmUiygj_6Fvyhp_i1xHouw";
        
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

            //Make a new trip option for each result returned
            org.primefaces.json.JSONObject result = resultsArray.getJSONObject(0);
            
            JSONArray addressComponents = new JSONArray(result.optString("address_components", ""));
           
            
            for (int j = 0; j < addressComponents.length(); j++){
                JSONObject component = addressComponents.getJSONObject(j);
                String types = component.optString("types", "");
                if (types.contains("country")){
                    countryName = component.optString("short_name", "");
                }
                if (types.contains("administrative_area_level_1"))
                {
                    state = component.optString("long_name", "");
                }
                
            }

        } catch (Exception e) {
            Methods.showMessage("Fatal Error", "Unrecognized Search Query!",
                    "The Countries API provides no data for the search query entered!");
        }
        
        if (!"".equals(state)){
            state = state.replaceAll(" ", "+");
            return state;
        }
        countryName = countryName.replaceAll(" ", "+");
        return countryName;
    }
    
}

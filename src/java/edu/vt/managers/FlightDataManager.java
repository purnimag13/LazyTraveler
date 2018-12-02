/*
 * Created by William Goodwin on 2018.11.24  * 
 * Copyright © 2018 William Goodwin. All rights reserved. * 
 */
package edu.vt.managers;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import edu.vt.pojo.Flight;
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

    public List<Flight> findFlights(String inboundDate) {
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
            

            HttpResponse<JsonNode> flightResponse = Unirest.get("https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/pricing/uk2/v1.0/"+ sessionKey +"?pageIndex=0&pageSize=10")
                    .header("X-RapidAPI-Key", "fsr5w8PpIrmshHMJMjS4tzJnByRcp1m3ltPjsn0cwo7Tq3WwLT")
                    .asJson();
            
            JsonNode responseBody = flightResponse.getBody();
            JSONArray responseArray;
            JSONObject responseObject;
            if (responseBody.isArray()){
                responseArray = responseBody.getArray();
            }
            else{
                responseObject = responseBody.getObject();
            }
            
        } catch (UnirestException ex) {
            Logger.getLogger(FlightDataManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}

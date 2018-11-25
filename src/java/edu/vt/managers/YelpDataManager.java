/*
 * Created by William Goodwin on 2018.11.16  * 
 * Copyright © 2018 William Goodwin. All rights reserved. * 
 */
package edu.vt.managers;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.URLConnection;

/**
 *
 * @author WBG
 */
public class YelpDataManager {

    /**
     * Yelp api info:
     *
     * Client ID c10UB-bDjgBTGI7zjBKHrQ API Key:
     * lj4bB-s1mLfowylin4AMnSNPrIBtNtcZKQNQdRgPw5d-IX5t4FKJFMeoaWReMbmHm0kMEStCWGL0mNPT8vuO8giCV2-7w4A3HvU8Rsi9dvv9mZqDTVkFT0Ee-b7tW3Yx
     */
    private String searchUrl;
    private String jsonStr;
    private final String clientId = "c10UB-bDjgBTGI7zjBKHrQ";
    private final String apiKey = "lj4bB-s1mLfowylin4AMnSNPrIBtNtcZKQNQdRgPw5d-IX5t4FKJFMeoaWReMbmHm0kMEStCWGL0mNPT8vuO8giCV2-7w4A3HvU8Rsi9dvv9mZqDTVkFT0Ee-b7tW3Yx";

    Map<String, String> params = new HashMap<>();

    public YelpDataManager() {
        //  YelpFusionApiFactory apiFactory = new YelpFusionApiFactory();
    }

    public void data() {

        try {
            HttpResponse<JsonNode> response = Unirest.post("http://rapidapi.io/connect/YelpAPI/getBusinesses")
                    .header("X-Mashape-Key", "")
                    .header("X-Mashape-Host", "siddiq-such-flight-v1.p.rapidapi.com")
                    .asJson();

        } catch (UnirestException ex) {
            Logger.getLogger(YelpDataManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        // curl -X POST http://rapidapi.io/connect/YelpAPI/getBusinesses \
        //-d "accessToken=lj4bB-s1mLfowylin4AMnSNPrIBtNtcZKQNQdRgPw5d-IX5t4FKJFMeoaWReMbmHm0kMEStCWGL0mNPT8vuO8giCV2-7w4A3HvU8Rsi9dvv9mZqDTVkFT0Ee-b7tW3Yx"\
//	-d "location=22153"\
//	-u default-application_5bef63d2e4b0d1763ed6fd56:a86ef7a7-9505-4517-8410-0ab60f687611
        //      RapidApiConnect connect = new RapidApiConnect("default-application_5bef63d2e4b0d1763ed6fd56", "a86ef7a7-9505-4517-8410-0ab60f687611");
//Map<String, Argument> body = new HashMap<String, Argument>();
//body.put("accessToken", new Argument("data", "lj4bB-s1mLfowylin4AMnSNPrIBtNtcZKQNQdRgPw5d-IX5t4FKJFMeoaWReMbmHm0kMEStCWGL0mNPT8vuO8giCV2-7w4A3HvU8Rsi9dvv9mZqDTVkFT0Ee-b7tW3Yx"));
//body.put("location", new Argument("data", "22153"));
//try { 
//	Map<String, Object> response = connect.call("YelpAPI", "getBusinesses", body);
//if(response.get("success") != null) { 
        //} else{ 
        //} 
//} catch(Exception e){ 
        //}
        //  }

        // curl -X POST http://rapidapi.io/connect/YelpAPI/getBusinesses \
        //-d "accessToken=lj4bB-s1mLfowylin4AMnSNPrIBtNtcZKQNQdRgPw5d-IX5t4FKJFMeoaWReMbmHm0kMEStCWGL0mNPT8vuO8giCV2-7w4A3HvU8Rsi9dvv9mZqDTVkFT0Ee-b7tW3Yx"\
//	-d "location=22153"\
//	-u default-application_5bef63d2e4b0d1763ed6fd56:a86ef7a7-9505-4517-8410-0ab60f687611
        //      RapidApiConnect connect = new RapidApiConnect("default-application_5bef63d2e4b0d1763ed6fd56", "a86ef7a7-9505-4517-8410-0ab60f687611");
//Map<String, Argument> body = new HashMap<String, Argument>();
//body.put("accessToken", new Argument("data", "lj4bB-s1mLfowylin4AMnSNPrIBtNtcZKQNQdRgPw5d-IX5t4FKJFMeoaWReMbmHm0kMEStCWGL0mNPT8vuO8giCV2-7w4A3HvU8Rsi9dvv9mZqDTVkFT0Ee-b7tW3Yx"));
//body.put("location", new Argument("data", "22153"));
//try { 
//	Map<String, Object> response = connect.call("YelpAPI", "getBusinesses", body);
//if(response.get("success") != null) { 
        //} else{ 
        //} 
//} catch(Exception e){ 
        //}
        //  }
    }
}
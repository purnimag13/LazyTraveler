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
public class TrailDataManager {

    // These code snippets use an open-source library. http://unirest.io/java
    public TrailDataManager() {
        this.hep();
    }
    

    public void hep() {
        try {
            // These code snippets use an open-source library. http://unirest.io/java
            HttpResponse<String> response = Unirest.get("https://trailapi-trailapi.p.mashape.com/")
                    .header("X-Mashape-Key", "8MZyGMtMF6msh5yFXW7a1Q64XbdKp1myxycjsndSce8Vpqp462")
                    .header("Accept", "text/plain")
                    .asString();
            System.out.println(response);

        } catch (UnirestException ex) {
            Logger.getLogger(TrailDataManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

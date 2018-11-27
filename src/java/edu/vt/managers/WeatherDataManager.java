/*
 * Created by William Goodwin on 2018.11.16  * 
 * Copyright © 2018 William Goodwin. All rights reserved. * 
 */
package edu.vt.managers;

import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;
import edu.vt.globals.Methods;
import edu.vt.pojo.Forecast;

/**
 * API Key	lbss2nJoDvorl5feDodU6e9UFzeCvo8t
 * This class pulls data from the Accuweather Api and
 * puts it into a 5 day forecast object.
 * @author WBG
 */
public class WeatherDataManager {

    private String latitude;
    private String longitude;
    private String locationApiUrl;
    private String weatherApiUrl;
    private String locationId;

    /**
     * General Constructor
     */
    public WeatherDataManager() {
        //nothing to initialize
    }

    /**
     * Initializes the class with a location to pull weather data from/
     * @param lat latitude of location
     * @param lon longitude of location
     */
    public WeatherDataManager(String lat, String lon) {
        this.latitude = lat;
        this.longitude = lon;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLocationApiUrl() {
        return locationApiUrl;
    }

    public void setLocationApiUrl(String locationApiUrl) {
        this.locationApiUrl = locationApiUrl;
    }

    public String getWeatherApiUrl() {
        return weatherApiUrl;
    }

    public void setWeatherApiUrl(String weatherApiUrl) {
        this.weatherApiUrl = weatherApiUrl;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    /**
     * This method gets the location id from the api. it is needed because forecasts
     * are pulled from the api through a accuweather location id.
     */
    public void locationId() {
        String apiUrl = "http://dataservice.accuweather.com/locations/v1/cities/geoposition/search?apikey=lbss2nJoDvorl5feDodU6e9UFzeCvo8t&q=" + latitude + "%2C%20" + longitude;

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
            String locationResultsJsonData = Methods.readUrlContent(apiUrl);

            JSONArray jsonArray;
            JSONObject jsonObject;

            /*
            Country Full Name or Capital City Name search returns a JSON array.
            Country Code, 2 letters or 3 letters, search returns a JSON object.
            Therefore, we do the processing by testing the first character.
             */
            char firstChar = locationResultsJsonData.charAt(0);

            if (firstChar == '[') {
                // It is a JSON array
                jsonArray = new JSONArray(locationResultsJsonData);
                jsonObject = jsonArray.getJSONObject(0);
            } else {
                // It is a JSON object
                jsonObject = new JSONObject(locationResultsJsonData);
            }


            /*
            Try to get the array in the JSON data.
             */
            locationId = jsonObject.optString("Key", "");

        } catch (Exception e) {
            Methods.showMessage("Fatal Error", "Unrecognized Search Query!",
                    "The Countries API provides no data for the search query entered!");
            clear();
        }

    }

    /**
     * This method get the api data for a 5 day forecast and puts it into a 
     * forecast object.
     * @return the forecast object.
     */
    public Forecast fiveDayForecast() {
        weatherApiUrl = "http://dataservice.accuweather.com/forecasts/v1/daily/5day/" + locationId + "?apikey=lbss2nJoDvorl5feDodU6e9UFzeCvo8t";

        //Initilize new Forecast Object
        Forecast forecast = new Forecast();

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
            String weatherJsonData = Methods.readUrlContent(weatherApiUrl);

            JSONArray jsonArray;
            JSONObject jsonObject;

            /*
            Country Full Name or Capital City Name search returns a JSON array.
            Country Code, 2 letters or 3 letters, search returns a JSON object.
            Therefore, we do the processing by testing the first character.
             */
            char firstChar = weatherJsonData.charAt(0);

            if (firstChar == '[') {
                // It is a JSON array
                jsonArray = new JSONArray(weatherJsonData);
                jsonObject = jsonArray.getJSONObject(0);
            } else {
                // It is a JSON object
                jsonObject = new JSONObject(weatherJsonData);
            }


            /*
            Take the daily forecast data and put it into a json array.
             */
            String dailyForecastStr = jsonObject.optString("DailyForecasts", "");
            
            JSONArray dailyForecastArray = new JSONArray(dailyForecastStr);

            /**
             * Day 1 forecast Data
             */
            JSONObject day1Object = dailyForecastArray.getJSONObject(0);
            String day1Date = day1Object.optString("Date", "");
            day1Date = trimDate(day1Date);
            forecast.setDay1Date(day1Date);

            //getMinTemperature
            JSONObject day1TempData = new JSONObject(day1Object.optString("Temperature", ""));
            JSONObject day1TempMin = new JSONObject(day1TempData.optString("Minimum", ""));
            forecast.setDay1Low(day1TempMin.optString("Value", ""));

            //getMaxTemperature
            JSONObject day1TempMax = new JSONObject(day1TempData.optString("Maximum", ""));
            forecast.setDay1High(day1TempMax.optString("Value", ""));

            //getDaySky
            JSONObject day1DayData = new JSONObject(day1Object.optString("Day", ""));
            forecast.setDay1Sky(day1DayData.optString("IconPhrase", ""));

            //getNightSky
            JSONObject day1NightData = new JSONObject(day1Object.optString("Night", ""));
            forecast.setNight1Sky(day1NightData.optString("IconPhrase", ""));

            //getLink
            String day1Link = day1Object.optString("Link", "");
            forecast.setDay1Link(day1Link);

            
            /**
             * Day 2 forecast Data
             */
            JSONObject day2Object = dailyForecastArray.getJSONObject(1);
            String day2Date = day2Object.optString("Date", "");
            day2Date = trimDate(day2Date);
            forecast.setDay2Date(day2Date);

            //getMinTemperature
            JSONObject day2TempData = new JSONObject(day2Object.optString("Temperature", ""));
            JSONObject day2TempMin = new JSONObject(day2TempData.optString("Minimum", ""));
            forecast.setDay2Low(day2TempMin.optString("Value", ""));

            //getMaxTemperature
            JSONObject day2TempMax = new JSONObject(day2TempData.optString("Maximum", ""));
            forecast.setDay2High(day2TempMax.optString("Value", ""));

            //getDaySky
            JSONObject day2DayData = new JSONObject(day2Object.optString("Day", ""));
            forecast.setDay2Sky(day2DayData.optString("IconPhrase", ""));

            //getNightSky
            JSONObject day2NightData = new JSONObject(day2Object.optString("Night", ""));
            forecast.setNight2Sky(day2NightData.optString("IconPhrase", ""));

            //getLink
            String day2Link = day2Object.optString("Link", "");
            forecast.setDay2Link(day2Link);
            
            
            /**
             * Day 3 forecast Data
             */
            JSONObject day3Object = dailyForecastArray.getJSONObject(2);
            String day3Date = day3Object.optString("Date", "");
            day3Date = trimDate(day3Date);
            forecast.setDay3Date(day3Date);

            //getMinTemperature
            JSONObject day3TempData = new JSONObject(day3Object.optString("Temperature", ""));
            JSONObject day3TempMin = new JSONObject(day3TempData.optString("Minimum", ""));
            forecast.setDay3Low(day3TempMin.optString("Value", ""));

            //getMaxTemperature
            JSONObject day3TempMax = new JSONObject(day3TempData.optString("Maximum", ""));
            forecast.setDay3High(day3TempMax.optString("Value", ""));

            //getDaySky
            JSONObject day3DayData = new JSONObject(day3Object.optString("Day", ""));
            forecast.setDay3Sky(day3DayData.optString("IconPhrase", ""));

            //getNightSky
            JSONObject day3NightData = new JSONObject(day3Object.optString("Night", ""));
            forecast.setNight3Sky(day3NightData.optString("IconPhrase", ""));

            //getLink
            String day3Link = day3Object.optString("Link", "");
            forecast.setDay3Link(day3Link);
            
            
            /**
             * Day 4 forecast Data
             */
            JSONObject day4Object = dailyForecastArray.getJSONObject(3);
            String day4Date = day4Object.optString("Date", "");
            day4Date = trimDate(day4Date);
            forecast.setDay4Date(day4Date);

            //getMinTemperature
            JSONObject day4TempData = new JSONObject(day4Object.optString("Temperature", ""));
            JSONObject day4TempMin = new JSONObject(day4TempData.optString("Minimum", ""));
            forecast.setDay4Low(day4TempMin.optString("Value", ""));

            //getMaxTemperature
            JSONObject day4TempMax = new JSONObject(day4TempData.optString("Maximum", ""));
            forecast.setDay4High(day4TempMax.optString("Value", ""));

            //getDaySky
            JSONObject day4DayData = new JSONObject(day4Object.optString("Day", ""));
            forecast.setDay4Sky(day4DayData.optString("IconPhrase", ""));

            //getNightSky
            JSONObject day4NightData = new JSONObject(day4Object.optString("Night", ""));
            forecast.setNight4Sky(day4NightData.optString("IconPhrase", ""));

            //getLink
            String day4Link = day4Object.optString("Link", "");
            forecast.setDay4Link(day4Link);
            
            
            
            /**
             * Day 5 forecast Data
             */
            JSONObject day5Object = dailyForecastArray.getJSONObject(4);
            String day5Date = day5Object.optString("Date", "");
            day5Date = trimDate(day5Date);
            forecast.setDay5Date(day5Date);

            //getMinTemperature
            JSONObject day5TempData = new JSONObject(day5Object.optString("Temperature", ""));
            JSONObject day5TempMin = new JSONObject(day5TempData.optString("Minimum", ""));
            forecast.setDay5Low(day5TempMin.optString("Value", ""));

            //getMaxTemperature
            JSONObject day5TempMax = new JSONObject(day5TempData.optString("Maximum", ""));
            forecast.setDay5High(day5TempMax.optString("Value", ""));

            //getDaySky
            JSONObject day5DayData = new JSONObject(day5Object.optString("Day", ""));
            forecast.setDay5Sky(day5DayData.optString("IconPhrase", ""));

            //getNightSky
            JSONObject day5NightData = new JSONObject(day5Object.optString("Night", ""));
            forecast.setNight5Sky(day5NightData.optString("IconPhrase", ""));

            //getLink
            String day5Link = day5Object.optString("Link", "");
            forecast.setDay5Link(day5Link);
            

        } catch (Exception e) {
            Methods.showMessage("Fatal Error", "Unrecognized Search Query!",
                    "The Countries API provides no data for the search query entered!");
            clear();
        }
        clear();
        return forecast;

    }

    /**
     * Trims the date pulled from the api to just the year-month-day format.
     * @param date Date pulled from the api.
     * @return the trimmed date.
     */
    public String trimDate(String date){
        int tIndex = date.indexOf("T");
        return date.substring(0, tIndex);
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

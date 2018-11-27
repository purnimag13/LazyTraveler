/*
 * Created by William Goodwin on 2018.11.27  * 
 * Copyright © 2018 William Goodwin. All rights reserved. * 
 */
package edu.vt.managers;

import edu.vt.pojo.Forecast;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author WBG
 */
public class WeatherDataManagerTest {
    
    public WeatherDataManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of locationId method, of class WeatherDataManager.
     */
    @Test
    public void testLocationId() {
        
        WeatherDataManager instance = new WeatherDataManager("37.225780", "-80.410118");
        instance.locationId();
        System.out.println(instance.getLocationId());
        assertTrue(instance.getLocationId().equals("331254"));
        
    }

    /**
     * Test of fiveDayForcast method, of class WeatherDataManager.
     */
    @Test
    public void testFiveDayForcast() {
        System.out.println("fiveDayForecast");
        WeatherDataManager instance = new WeatherDataManager("37.225780", "-80.410118");
        instance.locationId();
        Forecast forecast = instance.fiveDayForecast();
        
        System.out.println("Actual: ");
        System.out.println(forecast.getDay1Date());
        assertTrue(forecast.getDay1Date().equals("2018-11-27"));
        
        System.out.println("Actual: ");
        System.out.println(forecast.getDay1High());
        assertTrue(forecast.getDay1High().equals("33"));
        
        System.out.println("Actual: ");
        System.out.println(forecast.getDay1Link());
        assertTrue(forecast.getDay1Link().equals("http://www.accuweather.com/en/us/blacksburg-va/24060/daily-weather-forecast/331254?day=1&lang=en-us"));
        
        System.out.println("Actual: ");
        System.out.println(forecast.getDay1Low());
        assertTrue(forecast.getDay1Low().equals("21"));
        
        System.out.println("Actual: ");
        System.out.println(forecast.getDay1Sky());
        assertTrue(forecast.getDay1Sky().equals("Intermittent clouds"));
        
        System.out.println("Actual: ");
        System.out.println(forecast.getNight1Sky());
        assertTrue(forecast.getNight1Sky().equals("Intermittent clouds"));
        
    }

    /**
     * Test of clear method, of class WeatherDataManager.
     */
    @Test
    public void testClear() {
        System.out.println("clear");
        WeatherDataManager instance = new WeatherDataManager();
        instance.clear();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

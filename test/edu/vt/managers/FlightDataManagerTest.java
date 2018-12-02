/*
 * Created by William Goodwin on 2018.12.02  * 
 * Copyright © 2018 William Goodwin. All rights reserved. * 
 */
package edu.vt.managers;

import edu.vt.pojo.Flight;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author WBG
 */
public class FlightDataManagerTest {
    
    public FlightDataManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of findFlights method, of class FlightDataManager.
     */
    @Test
    public void testFindFlights() {
        System.out.println("findFlights");
        FlightDataManager instance = new FlightDataManager();
        List<Flight> expResult = null;

        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

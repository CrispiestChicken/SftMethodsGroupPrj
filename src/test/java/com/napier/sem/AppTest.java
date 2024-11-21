package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    static App app;
    static City city;
    static Capital_City capitalcity;
    @BeforeAll
    static void init() {
        app = new App();
        app.connect("localhost:33060", 30000);
    }


    /**
     * Tests if the data collected from the database is what it's meant to be
     * for getting citties
     */
    @Test
    void testGetCity(){
        ArrayList<City> testCity = app.getCity();
        assertTrue(testCity.get(0).name.equals("Kabul"));
    }

    /**
     * Tests if the data collected from the database is what it's meant to be
     * for the top populateed cities in a dirtrict
     */
    @Test
    void testGetTopPopulateCitiesInADistrict(){
        ArrayList<City> cityPopulationInADistrict = app.getTopPopulateCitiesInADistrict(2, "Zuid-Holland");
        assertTrue(cityPopulationInADistrict.get(0).name.equals("Rotterdam"));
        assertTrue(cityPopulationInADistrict.get(0).district.equals("Zuid-Holland"));
        assertTrue(cityPopulationInADistrict.get(0).population == 593321);

        assertTrue(cityPopulationInADistrict.get(1).name.equals("Haag"));
        assertTrue(cityPopulationInADistrict.get(1).district.equals("Zuid-Holland"));
        assertTrue(cityPopulationInADistrict.get(1).population == 440900);
    }

    /**
     * Tests if the data collected from the database is what it's meant to be
     * for the all cities population descending.
     */
    @Test
    void testCorrectCitiesPopDesc()
    {
        ArrayList<City> CitiesPopDesc = app.GetAllCitiesPopDesc();
        assertEquals(CitiesPopDesc.get(0).name,"Mumbai (Bombay)");
    }

    /**
     * Tests if the data collected from the database is what it's meant to be
     * for the the number of given cities population in desending order.
     */
    @Test
    void testGetGivenNumberOfCapitalCitiesPopDesc()
    {
        ArrayList<City> capitalCitiesPopDesc = app.GetGivenNumberOfCapitalCitiesPopDesc(1);
        assertEquals(capitalCitiesPopDesc.get(0).name,"Seoul");
    }

}
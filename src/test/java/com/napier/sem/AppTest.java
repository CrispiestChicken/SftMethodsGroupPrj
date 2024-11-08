package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    static App app;
    static City city;
    static Capital_City capitalcity;
    @BeforeAll
    static void init() {
        app = new App();
        city = new City();
        capitalcity = new Capital_City();
    }

    @Test
    void testCity()
    {
        city.name = "Edinburgh";
        city.countryCode = "SCO";
        city.district = "NONE";
        city.population = 10000;
        String cityName = city.name;
        app.displayCity(city);
        assertTrue(cityName.equals("Edinburgh"));
    }
    /*
    @Test
    void testCapitalCity()
    {

        capitalcity.name = "Edinburgh";
        capitalcity.countryCode = "SCO";
        capitalcity.population = 10000;
        String cityName = capitalcity.name;
        app.displayCity(city);
        assertTrue(cityName.equals("Edinburgh"));
    }
*/


    /*
    /**
     * Tests if the data collected from the database is what it's meant to be
     * for the all cities population descending.
     */

    /*
    This doesn't work at the moment because I can't connect to the database yet in the tests I will fix this
    for code review 3.
    @Test
    void TestCorrectCitiesPopDesc()
    {
        ArrayList<City> CitiesPopDesc = app.GetAllCitiesPopDesc();

        assertEquals(CitiesPopDesc.get(0).name,"Mumbai (Bombay)");
    }
    */
}
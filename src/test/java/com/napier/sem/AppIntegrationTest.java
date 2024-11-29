package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppIntegrationTest {
    static App app;
    static City city;
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

    /**
     * Tests if the data collected from the database is what it's meant to be
     * for the languages given in descending order.
     */
    @Test
    void testLanguageReport()
    {
        ArrayList<Language> language = app.LanguageReport();
        assertEquals(language.get(0).Name,"Chinese");
        assertEquals(language.get(0).Number,1191843539);
        assertEquals(language.get(0).Percentage,19.0);
    }

    /**
     * This test is for a display function
     * .
     */
    @Test
    void testDisplayTopCityPopulationInDistrict()
    {
        ArrayList<City> yopPopCitieInAdistrict = app.getTopPopulateCitiesInADistrict(2, "Zuid-Holland");
        app.displayTopCityPopulationInDistrict(yopPopCitieInAdistrict);
    }

    /**
     * This test is for a display function
     * .
     */
    @Test
    void testDisplayCity()
    {
        ArrayList<City> testCity = app.getCity();
        app.displayCity(testCity);
    }

    /**
     * This test is for a display function
     * .
     */
    @Test
    void testDisplayLanguage()
    {
        ArrayList<Language> language = app.LanguageReport();
        app.displayLanguage(language);
    }

    /**
     * Tests if the data collected from the database is what it's meant to be
     * for the languages given in descending order.
     */
    @Test
    void testGetAllCapitalCitiesPopDesc()
    {
        ArrayList<City> topcapcities = app.GetAllCapitalCitiesPopDesc();
        assertEquals(topcapcities.get(0).name,"Seoul");
        assertEquals(topcapcities.get(0).population,9981619);
    }

    /**
     * Tests if the data collected from the database is what it's meant to be
     * for the languages given in descending order.
     */
    @Test
    void testCittiesInACountryOrederBypopulation()
    {
        ArrayList<City> test = app.getAllCitiesInCountryOrderedByPopulation("China");
        assertEquals(test.get(0).name,"Shanghai");
        assertEquals(test.get(0).population,9696300);
        assertEquals(test.get(0).country,"China");
    }
}

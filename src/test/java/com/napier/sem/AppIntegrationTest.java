package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppIntegrationTest {
    static App app;
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
        assertTrue(testCity.get(0).name.equals("Mumbai (Bombay)"));
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
     * Tests if the method is giving the correct data in the correct format.
     */
    @Test
    void testGetAllCapitalCitiesInRegionPopDesc()
    {
        // Getting the data then checking if it is correct.
        ArrayList<City> capitalCities = app.GetAllCapitalCitiesInRegionPopDesc("Middle East");
        assertEquals(capitalCities.get(0).name, "Baghdad");
        assertEquals(capitalCities.get(0).population, 4336000);
    }

    /**
     * Tests if the method is giving the correct data in the correct format and only the amount asked for.
     */
    @Test
    void testGetGivenNumberOfCapitalCitiesInRegionPopDesc()
    {
        // Getting the data and checking if it is correct.
        ArrayList<City> capitalCities = app.GetGivenNumOfCapitalCitiesInRegionPopDesc("Middle East", 5);
        assertEquals(capitalCities.get(0).name, "Baghdad");
        assertEquals(capitalCities.get(0).population, 4336000);

        assertEquals(capitalCities.get(4).name, "Damascus");
        assertEquals(capitalCities.get(4).population, 1347000);

    }

    /**
     * Tests if the method is giving the correct data in the correct format.
     */
    @Test
    void testGetAllCapitalCitiesInContinentPopDesc()
    {
        // Getting the data then checking if it is correct.
        ArrayList<City> capitalCities = app.GetAllCapitalCitiesInContinentPopDesc("Europe");
        assertEquals(capitalCities.get(0).name, "Moscow");
        assertEquals(capitalCities.get(0).population, 8389200);
    }

    /**
     * Tests if the method is giving the correct data in the correct format.
     */
    @Test
    void testGetAllCitiesInContinentPopDesc()
    {
        // Getting the data then checking if it is correct.
        ArrayList<City> cities = app.GetAllCitiesInContinentPopDesc("Europe");
        assertEquals(cities.get(0).name, "Moscow");
        assertEquals(cities.get(0).population, 8389200);
    }

    /**
     * Tests if the method is giving the correct data in the correct format.
     */
    @Test
    void testGetPopulationReportOfAllContinentsTotalPopDesc()
    {
        // Getting the data then checking if it is correct.
        ArrayList<Population> populationReports = app.GetPopulationReportOfAllContinentsTotalPopDesc();
        assertEquals(populationReports.get(0).AreaName, "Asia");
        assertEquals(populationReports.get(0).PopulationOfAreaInCitiesPercent, 18.8286);
        assertEquals(populationReports.get(0).PopulationOfAreaNotInCitiesPercent, 81.1714);
    }

    /**
     * Tests if the method is giving the correct data in the correct format.
     */
    @Test
    void testGetPopulationReportOfAllRegionsTotalPopDesc()
    {
        // Getting the data then checking if it is correct.
        ArrayList<Population> populationReports = app.GetPopulationReportOfAllRegionsTotalPopDesc();
        assertEquals(populationReports.get(0).AreaName, "Eastern Asia");
        assertEquals(populationReports.get(0).PopulationOfAreaInCitiesPercent, 21.0622);
        assertEquals(populationReports.get(0).PopulationOfAreaNotInCitiesPercent, 78.9378);
    }

    /**
     * Tests that the method will give the data in the correct format
     */
    @Test
    void testFormatCapitalCityReportsAsString()
    {

        // Making sure that the data is correct
        ArrayList<City> capitalCities = app.GetAllCapitalCitiesPopDesc();
        String capitalCityReportString = app.FormatCapitalCityReportsAsString(capitalCities);
        assertTrue(capitalCityReportString.contains("Seoul, KOR, 9981619"));
    }

    /**
     * Tests that the method will give the data in the correct format
     */
    @Test
    void testFormatCountryReportsAsString()
    {
        ArrayList<Country> countryReports = new ArrayList<>();

        //There is no country reports just yet.

        /*
        // Making sure that the data is correct
        countryReports = Get
        String countryReportsString = "";
        assertTrue(countryReportsString.contains(""));
         */
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

    @Test
    void testGetGivenNumOfTopPopulatedCountriesInRegionPopDesc()
    {
        ArrayList<Country> test = app.getGivenNumOfTopPopulatedCountriesInRegionPopDesc("Caribbean", 5);
        assertEquals(test.get(0).CountryName,"Cuba");
        assertEquals(test.get(0).Population,11201000);
        assertEquals(test.get(0).Region,"Caribbean");
    }

    @Test
    void testGetGivenNumOfTopPopulatedCountriesInTheWorld()
    {
        ArrayList<Country> test = app.getGivenNumOfTopPopulatedCountriesInTheWorld(10);
        assertEquals(test.get(0).CountryName,"China");
        assertEquals(test.get(0).Population,1277558000);
    }

    @Test
    void testgetGivenPopulationOFCountry()
    {
        ArrayList<Country> test = app.getGivenPopulationOFCountry("United States");
        assertEquals(test.get(0).CountryName,"United States");
        assertEquals(test.get(0).Population,278357000);
    }

    /**
     * Tests if the method is giving the correct data in the correct format and only the amount asked for.
     */
    @Test
    void testGetGivenNumberOfCapitalCitiesInGivenContinentPopDesc()
    {
        // Getting the data and checking if it is correct.
        ArrayList<City> capitalCities = app.GetGivenNumberOfCapitalCitiesInGivenContinentPopDesc("Europe", 5);
        assertEquals(capitalCities.get(0).name, "Moscow");
        assertEquals(capitalCities.get(0).population, 8389200);

        assertEquals(capitalCities.get(4).name, "Roma");
        assertEquals(capitalCities.get(4).population, 2643581);
    }

    @Test
    void testGetAllCapitalCities()
    {
        ArrayList<City> test = app.getAllCapitalCities();
        assertEquals(test.get(0).name,"Seoul");
        assertEquals(test.get(0).population,9981619);
    }

    @Test
    void testgetPopulationOfAContinent()
    {
        long test = app.getPopulationOfAContinent("Asia");
        assertEquals(3705025700L, test);
    }

    @Test
    void  testGetAllCountriesOrderedByPop()
    {
        ArrayList<Country> test = app.getAllCountriesOrderedByPop();
        assertEquals(test.get(0).CountryName, "China");
        assertEquals(test.get(0).Population,11201000);
    }

    @Test
    void testGetAllCountriesInContinentOrderedByPop()
    {
        ArrayList<Country> test = app.getAllCountriesInContinentOrderedByPop("Asia");
        assertEquals(test.get(0).CountryName,  "China");
        assertEquals(test.get(0).Population,278357000);
//        assertEquals(test.get(0).Population,1277558000);

    }


}

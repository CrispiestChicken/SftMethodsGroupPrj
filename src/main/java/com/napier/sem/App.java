package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

public class App {
    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect(String location, int delay) {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        boolean shouldWait = false;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                if (shouldWait) {
                    // Wait a bit for db to start
                    Thread.sleep(delay);
                }
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://" + location + "/world?allowPublicKeyRetrieval=TRUE&useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + i);
                System.out.println(sqle.getMessage());

                shouldWait = true;
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect() {
        if (con != null) {
            try {
                // Close connection
                con.close();
            } catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }
    }

    /**
     * Runs a given query and returns the result.
     *
     * @param query The query you want to run.
     * @return The results of the query.
     * @throws Exception Probably from the sql query failing.
     */
    private ResultSet runQuery(String query) throws Exception {

        // Create an SQL statement
        Statement stmt = con.createStatement();

        // Execute SQL statement
        return stmt.executeQuery(query);
    }


    /**
     * @param results A ResultSet that you get from executing a query.
     * @return An ArrayList containing City objects that contains the data for each city gotten from the result set.
     * @throws Exception Makes it so the person using this method has to deal with exceptions.
     */
    private ArrayList<City> getCityDataFromResultSet(ResultSet results) throws Exception {

        ArrayList<City> cities = new ArrayList<>();

        // Declaring city up here so that it isn't declaring it over and over again.
        City city;


        // If there is a row of data it gets the data and stores it in the array list so that it can later be returned.
        while (results.next()) {
            city = new City();
            city.population = results.getLong("Population");
            city.countryCode = results.getString("CountryCode");
            city.district = results.getString("District");
            city.name = results.getString("Name");

            cities.add(city);
        }

        return cities;
    }



    /**
     * @param results A ResultSet that you get from executing a query.
     * @return An ArrayList containing City objects that contains the data for each city gotten from the result set.
     * @throws Exception Makes it so the person using this method has to deal with exceptions.
     */
    private ArrayList<City> getAllCitiesInCountryOrderedByPopulationDataFromResultSet(ResultSet results) throws Exception {

        ArrayList<City> cities = new ArrayList<>();

        // Declaring city up here so that it isn't declaring it over and over again.
        City city;


        // If there is a row of data it gets the data and stores it in the array list so that it can later be returned.
        while (results.next()) {
            city = new City();
            city.population = results.getLong("Population");
            city.name = results.getString("Name");
            city.country = results.getString("CountryName");

            cities.add(city);
        }

        return cities;
    }

    /**
     * @param results A ResultSet that you get from executing a query.
     * @return An ArrayList containing City objects that contains the data for each capital city gotten from the result set.
     * @throws Exception Makes it so the person using this method has to deal with exceptions.
     */
    private ArrayList<City> getCapitalCityDataFromResultSet(ResultSet results) throws Exception {

        ArrayList<City> capitalCities = new ArrayList<>();

        // Declaring city up here so that it isn't declaring it over and over again.
        City capitalCity;

        // If there is a row of data it gets the data and stores it in the array list so that it can later be returned.
        while (results.next()) {
            capitalCity = new City();
            capitalCity.name = results.getString("Name");
            capitalCity.population = results.getLong("Population");
            capitalCity.countryCode = results.getString("CountryCode");

            capitalCities.add(capitalCity);
        }

        return capitalCities;
    }

    /**
     * @param results A ResultSet that you get from executing a query.
     * @return An ArrayList containing Language objects that contains the data for each language gotten from the result set.
     * @throws Exception Makes it so the person using this method has to deal with exceptions.
     */
    private ArrayList<Language> getLanguageDataFromResultSet(ResultSet results) throws Exception {

        ArrayList<Language> languagesReport = new ArrayList<>();

        // Declaring language up here so that it isn't declaring it over and over again.
        Language language;

        // If there is a row of data it gets the data and stores it in the array list so that it can later be returned.
        while (results.next()) {
            language = new Language();
            language.Name = results.getString("Language");
            language.Number = results.getInt("TotalSpeakers");
            language.Percentage = results.getInt("WorldPercentage");

            languagesReport.add(language);

        }

        return languagesReport;
    }

    /**
     * @param results A ResultSet that you get from executing a query.
     * @return An ArrayList containing Country objects that contains the data for each country gotten from the result set.
     * @throws Exception Makes it so the person using this method has to deal with exceptions.
     */
    private ArrayList<Country> getCountryDataFromResultSet(ResultSet results) throws Exception {
        ArrayList<Country> countries = new ArrayList<>();

        // Declaring country up here so that it isn't declaring it over and over again.
        Country country;

        // If there is a row of data it gets the data and stores it in the array list so that it can later be returned.
        while (results.next()) {
            country = new Country();
            country.CountryCode = results.getString("Code");
            country.CountryName = results.getString("Name");
            country.Continent = results.getString("Continent");
            country.Region = results.getString("Region");
            country.Population = results.getLong("Population");
            country.CapitalCity = results.getString("Capital");

            countries.add(country);

        }

        return countries;
    }

    /**
     * @param results A ResultSet that you get from executing a query.
     * @return An ArrayList containing Population objects that contains the data for each population row gotten from the result set.
     * @throws Exception Makes it so the person using this method has to deal with exceptions.
     */
    private ArrayList<Population> getPopulationDataFromResultSet(ResultSet results) throws Exception {
        ArrayList<Population> populationReport = new ArrayList<>();

        // Declaring population up here so that it isn't declaring it over and over again.
        Population population;

        // If there is a row of data it gets the data and stores it in the array list so that it can later be returned.
        while (results.next()) {
            population = new Population();
            population.AreaName = results.getString("AreaName");
            population.PopulationOfArea = results.getLong("AreaPopulation");
            population.PopulationOfAreaInCities = results.getLong("AreaCityPopulation");
            population.PopulationOfAreaInCitiesPercent = results.getDouble("AreaCityPopulationPercent");
            population.PopulationOfAreaNotInCities = results.getLong("AreaNotInCityPopulation");
            population.PopulationOfAreaNotInCitiesPercent = results.getDouble("AreaNotInCityPopulationPercent");

            populationReport.add(population);
        }

        return populationReport;
    }


    /**
     * This is for getting top populated cities in a district
     *
     * @return ArrayList<City>
     */
    public ArrayList<City> getTopPopulateCitiesInADistrict(int topPopulatedCities, String districtName) {
        try {
            // Create string for SQL statement
            String strSelect =
                "SELECT Name, Population, District, CountryCode " +
                    "FROM city " +
                    "WHERE District = '" + districtName + "' " +
                    "ORDER BY Population DESC " +
                    "LIMIT " + topPopulatedCities;

            // Execute SQL statement
            ResultSet rset = runQuery(strSelect);

            // Takes all the data from the result and formats it into an ArrayList of cities.
            return getCityDataFromResultSet(rset);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    /**
     * This gets all the cities
     *
     * @return ArrayList<City>
     */
    public ArrayList<City> getCity() {
        ArrayList<City> cities = new ArrayList<>();
        try {
            // Create string for SQL statement
            String strSelect =
                "SELECT city.name, country.name, District, city.Population "
                    + "FROM city join country ON city.CountryCode = country.Code" +
                    " ORDER BY city.Population DESC ";
            // Execute SQL statement
            ResultSet rset = runQuery(strSelect);

            // Return new employee if valid.
            City city;

            // If there is a row of data it gets the data and stores it in the array list so that it can later be returned.
            while (rset.next()) {
                city = new City();
                city.population = rset.getInt("city.Population");
                city.country = rset.getString("country.name");
                city.district = rset.getString("District");
                city.name = rset.getString("city.name");

                cities.add(city);

            }

            return cities;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }


    /**
     * This displays the top city population in a district
     */
    public void displayTopCityPopulationInDistrict(ArrayList<City> cities) {
        if (cities != null) {
            System.out.printf("%-30s %-30s %-30s %n", "Name", "District", "Population ");
            for (City city : cities) {
                System.out.printf("%-30s %-30s %-30s %n", city.name, city.district, city.population);
            }

        }
    }

    /**
     * This displays city report
     */
    public void displayCity(ArrayList<City> cities) {
        if (cities != null) {
            System.out.printf("%-30s %-30s %-30s %-30s %n", "Name", "Country", "District", "Population ");
            for (City city : cities) {
                System.out.printf("%-30s %-30s %-30s %-30s %n", city.name, city.country, city.district, city.population);
            }

        }
    }

    public void displayCapitalCity(ArrayList<City> cities) {
        if (cities != null) {
            System.out.printf("%-30s %-30s %-30s %-30s %n", "Capital", "Country", "District", "Population ");
            for(City city : cities) {
                System.out.printf("%-30s %-30s %-30s %-30s %n", city.name, city.country, city.district, city.population);
            }

        }
    }

    /**
     * This displays Top N Populated Countries in a Region
     */
    public void displayTopPopulatedCountries(ArrayList<Country> contries) {
        if (contries != null) {
            System.out.printf("%-30s %-30s %-30s %n", "Name", "District", "Population ");
            for (Country country : contries) {
                System.out.printf("%-30s %-30s %-30s %n", country.CountryName, country.Region, country.Population);
            }

        }
    }

    public ArrayList<City> getAllCapitalCities()
    {
        try {
            ArrayList<City> capCitties = new ArrayList<>();
            String selectString =
                    "SELECT city.Name AS Capital, "
                            + "country.Name AS Country, "
                            + "city.District, "
                            + "city.Population "
                            + "FROM country "
                            + "INNER JOIN city "
                            + "ON country.Capital = city.ID "
                            + "ORDER BY city.Population DESC;";


            // Execute SQL statement
            ResultSet resultSet = runQuery(selectString);

            // Takes all the data from the result and formats it into an ArrayList of cities.
            City city;

            // If there is a row of data it gets the data and stores it in the array list so that it can later be returned.
            while (resultSet.next()) {
                city = new City();
                city.population = resultSet.getInt("city.Population");
                city.country = resultSet.getString("city.District");
                city.district = resultSet.getString("District");
                city.name = resultSet.getString("Capital");

                capCitties.add(city);

            }
            return capCitties;

        }
        // If any error happens.
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    /**
     * Gets all the cities ordered from the highest population to smallest.
     *
     * @return An array list containing City objects.
     */
    public ArrayList<City> GetAllCitiesPopDesc() {
        try {
            // String for SQL statement
            String selectString =
                "SELECT Name, CountryCode, District, Population "
                    + "FROM city "
                    + "ORDER BY Population Desc ;";


            // Execute SQL statement
            ResultSet resultSet = runQuery(selectString);

            // Takes all the data from the result and formats it into an ArrayList of cities.
            return getCityDataFromResultSet(resultSet);

        }
        // If any error happens.
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    /**
     * Gets all the cities ordered from the highest population to smallest.
     *
     * @return continent population
     */
    public long getPopulationOfAContinent(String continent)
    {
        long population = 0;
        try {

            // String for SQL statement
            String selectString =
                    "SELECT Continent, "
                            + "SUM(Population) AS TotalPopulation "
                            + "FROM country "
                            + "WHERE Continent =  '" + continent + "' ;";


            // Execute SQL statement
            ResultSet resultSet = runQuery(selectString);

            // Takes all the data from the result and formats it into an ArrayList of cities.
            while (resultSet.next()) {
                population = resultSet.getLong("TotalPopulation");
            }
            return population;
        }
        // If any error happens.
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return -1;
        }
    }

    /**
     * Gets all the capital cities ordered from the highest population to smallest.
     *
     * @return An array list containing City objects.
     */
    public ArrayList<City> GetAllCapitalCitiesPopDesc() {
        try {
            // String for SQL statement
            String selectString =
                "SELECT city.Name AS Name, country.Code AS CountryCode, city.Population AS Population " +
                    "FROM country " +
                    "INNER JOIN city ON country.Capital = city.ID " +
                    "ORDER BY city.Population DESC";

            // Execute SQL statement
            ResultSet resultSet = runQuery(selectString);

            // Takes all the data from the result and formats it into an ArrayList of cities.
            return getCapitalCityDataFromResultSet(resultSet);

        }
        // If any error happens.
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }


    /**
     * Gets a given number capital cities ordered from the highest population to smallest.
     *
     * @return An array list containing City objects.
     */
    public ArrayList<City> GetGivenNumberOfCapitalCitiesPopDesc(int numOfCitiesToGet) {
        try {
            // String for SQL statement
            String selectString =
                "SELECT city.Name AS Name, city.CountryCode AS CountryCode, city.Population AS Population"
                    + " FROM country "
                    + " INNER JOIN city ON country.Capital = city.ID "
                    + " ORDER BY Population Desc "
                    + "LIMIT " + numOfCitiesToGet;

            // Execute SQL statement
            ResultSet resultSet = runQuery(selectString);

            // Takes all the data from the result and formats it into an ArrayList of cities.
            return getCapitalCityDataFromResultSet(resultSet);

        }
        // If any error happens.
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    /**
     * This creates a language report
     *
     * @return ArrayList<Language>
     */
    public ArrayList<Language> LanguageReport() {
        try {
            String selectString =
                "SELECT cl.Language, "          //makes a table with summing up populations from all countries aswell as the number of speakers of each language in each country to work out global percentage from largest to smallest
                    + "       SUM(c.Population * (cl.Percentage / 100)) AS TotalSpeakers, "
                    + "       (SUM(c.Population * (cl.Percentage / 100)) / (SELECT SUM(Population) FROM country)) * 100 AS WorldPercentage "
                    + "FROM countrylanguage cl "
                    + "INNER JOIN country c ON cl.CountryCode = c.Code "
                    + "WHERE cl.Language IN ('Chinese', 'English', 'Hindi', 'Spanish', 'Arabic') "
                    + "GROUP BY cl.Language "
                    + "ORDER BY TotalSpeakers DESC;";

            ResultSet resultSet = runQuery(selectString);

            // Takes all the data from the result and formats it into an ArrayList of languages.
            return getLanguageDataFromResultSet(resultSet);

        }
        // If any error happens.
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get language report");
            return null;
        }
    }

    public long getGlobalPopulation()
    {
        long globalPopulation = 0;
        try{
            String stringSelect = "SELECT SUM(Population) AS GlobalPopulation FROM country";;
            ResultSet resultSet = runQuery(stringSelect);

            while (resultSet.next()) {
                globalPopulation = resultSet.getLong("GlobalPopulation");
            }
            return globalPopulation;
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Failed to get language report");
            return -1;
        }
    }
    /**
     * This displays the language report
     */
    public void displayLanguage(ArrayList<Language> LanguagesReport) {
        if (LanguagesReport != null) {
            System.out.printf("%-30s %-30s %-30s %n", "Language", "Population", "Percentage");
            for (Language language : LanguagesReport) {
                System.out.printf("%-30s %-30s %-30s %n", language.Name, language.Number, language.Percentage);
            }

        }
    }


    /**
     * Gets all the capital cities in a given region ordered from the highest population to smallest.
     *
     * @param region The region you want to get the capital cities from.
     * @return An array list containing City objects.
     */
    public ArrayList<City> GetAllCapitalCitiesInRegionPopDesc(String region) {
        try {
            // String for SQL statement
            String selectString =
                "SELECT city.Name AS Name, city.CountryCode AS CountryCode, city.Population AS Population " +
                    "FROM country " +
                    "INNER JOIN city ON city.ID = country.Capital " +
                    "WHERE country.Region = '" + region + "'" +
                    " ORDER BY city.Population Desc;";


            // Execute SQL statement
            ResultSet resultSet = runQuery(selectString);

            // Takes all the data from the result and formats it into an ArrayList of cities.
            return getCapitalCityDataFromResultSet(resultSet);

        }
        // If any error happens.
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }

    }


    /**
     * Gets given number of capital cities in a given region ordered from the highest population to smallest.
     *
     * @param region         The region you want to get the capital cities from.
     * @param numOfCapCities The number of cities to get.
     * @return An array list containing City objects.
     */
    public ArrayList<City> GetGivenNumOfCapitalCitiesInRegionPopDesc(String region, int numOfCapCities) {
        try {
            // String for SQL statement
            String selectString =
                "SELECT city.Name AS Name, city.CountryCode AS CountryCode, city.Population AS Population " +
                    "FROM country " +
                    "INNER JOIN city ON city.ID = country.Capital " +
                    "WHERE country.Region = '" + region + "'" +
                    "ORDER BY Population Desc " +
                    "LIMIT " + numOfCapCities;

            // Execute SQL statement
            ResultSet resultSet = runQuery(selectString);

            // Takes all the data from the result and formats it into an ArrayList of cities.
            return getCapitalCityDataFromResultSet(resultSet);

        }
        // If any error happens.
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }

    }

    /**
     * Gets given number of capital cities in a given region ordered from the highest population to smallest.
     *
     * @param region                     The region you want to get the capital cities from.
     * @param numOfTopPopulatedCountries The number of countries to get.
     * @return An array list containing City objects.
     */
    public ArrayList<Country> getGivenNumOfTopPopulatedCountriesInRegionPopDesc(String region, int numOfTopPopulatedCountries) {
        try {
            // String for SQL statement
            String selectString =
                "SELECT * "
                    + "FROM country "
                    + "WHERE Region Like '" + region + "' ORDER BY Population DESC "
                    + "LIMIT " + numOfTopPopulatedCountries;

            // Execute SQL statement
            ResultSet resultSet = runQuery(selectString);

            // Takes all the data from the result and formats it into an ArrayList of cities.
            return getCountryDataFromResultSet(resultSet);

        }
        // If any error happens.
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }

    }

    /**
     * Gets given number of capital cities in a given region ordered from the highest population to smallest.
     *
     * @param numOfTopPopulatedCountries The number of countries to get.
     * @return An array list containing City objects.
     */
    public ArrayList<Country> getGivenNumOfTopPopulatedCountriesInTheWorld(int numOfTopPopulatedCountries) {
        try {
            // String for SQL statement
            String selectString =
                "SELECT * "
                    + "FROM country "
                    + " ORDER BY Population DESC "
                    + "LIMIT " + numOfTopPopulatedCountries;

            // Execute SQL statement
            ResultSet resultSet = runQuery(selectString);

            // Takes all the data from the result and formats it into an ArrayList of cities.
            return getCountryDataFromResultSet(resultSet);

        }
        // If any error happens.
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }

    }

    public ArrayList<Country> getGivenPopulationOFCountry(String country) {
        try {
            // String for SQL statement
            String selectString =
                "SELECT * "
                    + "FROM country "
                    + " Where Name Like '" + country + "' ";

            // Execute SQL statement
            ResultSet resultSet = runQuery(selectString);

            // Takes all the data from the result and formats it into an ArrayList of cities.
            return getCountryDataFromResultSet(resultSet);

        }
        // If any error happens.
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }

    }

    /**
     * Gets all the capital cities in a given continent ordered from the highest population to smallest.
     *
     * @param continent The continent you want to get the capital cities from.
     * @return An array list containing City objects.
     */
    public ArrayList<City> GetAllCapitalCitiesInContinentPopDesc(String continent) {
        try {
            // String for SQL statement
            String selectString =
                "SELECT city.Name AS Name, city.CountryCode AS CountryCode, city.Population AS Population " +
                    "FROM country " +
                    "INNER JOIN city ON city.ID = country.Capital " +
                    "WHERE country.Continent = '" + continent + "'" +
                    "ORDER BY Population Desc;";


            // Execute SQL statement
            ResultSet resultSet = runQuery(selectString);

            // Takes all the data from the result and formats it into an ArrayList of cities.
            return getCapitalCityDataFromResultSet(resultSet);

        }
        // If any error happens.
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    public ArrayList<City> getAllCitiesInCountryOrderedByPopulation(String countryName) {
        try {
            // String for SQL statement
            String selectString =
                "SELECT city.Name AS Name, city.Population AS Population, country.Name AS CountryName " +
                    "FROM city " +
                    "INNER JOIN country ON city.CountryCode = country.Code " +
                    "WHERE country.Name = '" + countryName + "' " +  // Replace with the desired country name
                    "ORDER BY city.Population DESC;";


            // Execute SQL statement
            ResultSet resultSet = runQuery(selectString);

            // Takes all the data from the result and formats it into an ArrayList of cities.
            return getAllCitiesInCountryOrderedByPopulationDataFromResultSet(resultSet);

        }
        // If any error happens.
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    /**
     * Gets all the cities in a given continent ordered from the highest population to smallest.
     *
     * @param continent The continent you want to get the cities from.
     * @return An array list containing City objects.
     */
    public ArrayList<City> GetAllCitiesInContinentPopDesc(String continent) {
        try {
            // String for SQL statement
            String selectString =
                "SELECT city.Name AS Name, city.CountryCode AS CountryCode, city.Population AS Population, city.District AS District " +
                    "FROM country " +
                    "INNER JOIN city ON city.CountryCode = country.Code " +
                    "WHERE country.Continent = '" + continent + "'" +
                    "ORDER BY Population Desc;";

            // Execute SQL statement
            ResultSet resultSet = runQuery(selectString);

            // Takes all the data from the result and formats it into an ArrayList of cities.
            return getCityDataFromResultSet(resultSet);

        }
        // If any error happens.
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }

    }


    /**
     * Gets the total population of every continent as well as the population
     * in and out of cities with percentages for each.
     *
     * @return An array list containing Population objects 1 for each continent.
     */
    public ArrayList<Population> GetPopulationReportOfAllContinentsTotalPopDesc() {
        try {
            // String for SQL statement
            String selectString =
                "SELECT co.Continent AS AreaName, " +
                    "    SUM(co.Population) AS AreaPopulation, " +
                    "    SUM(cip.CityPopulation) AS AreaCityPopulation, " +
                    "    SUM(co.Population) - SUM(cip.CityPopulation) AS AreaNotInCityPopulation, " +
                    "    SUM(cip.CityPopulation) / SUM(co.Population) * 100 AS AreaCityPopulationPercent, " +
                    "    (SUM(co.Population) - SUM(cip.CityPopulation)) / SUM(co.Population) * 100 AS AreaNotInCityPopulationPercent " +
                    "FROM country co " +
                    "LEFT JOIN (" +
                    "    SELECT CountryCode, SUM(Population) AS CityPopulation " +
                    "    FROM city " +
                    "    GROUP BY CountryCode) cip " +
                    "    ON co.Code = cip.CountryCode " +
                    "GROUP BY co.Continent " +
                    "ORDER BY AreaPopulation DESC;";


            // Execute SQL statement
            ResultSet resultSet = runQuery(selectString);

            // Takes all the data from the result and formats it into an ArrayList of Population.
            return getPopulationDataFromResultSet(resultSet);

        }
        // If any error happens.
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Population data");
            return null;
        }

    }


    /**
     * Gets the total population of every region as well as the population
     * in and out of cities with percentages for each.
     *
     * @return An array list containing Population objects 1 for each region.
     */
    public ArrayList<Population> GetPopulationReportOfAllRegionsTotalPopDesc() {
        try {
            // String for SQL statement
            String selectString =
                "SELECT co.Region AS AreaName, " +
                    "    SUM(co.Population) AS AreaPopulation, " +
                    "    SUM(cip.CityPopulation) AS AreaCityPopulation, " +
                    "    SUM(co.Population) - SUM(cip.CityPopulation) AS AreaNotInCityPopulation, " +
                    "    SUM(cip.CityPopulation) / SUM(co.Population) * 100 AS AreaCityPopulationPercent, " +
                    "    (SUM(co.Population) - SUM(cip.CityPopulation)) / SUM(co.Population) * 100 AS AreaNotInCityPopulationPercent " +
                    "FROM country co " +
                    "LEFT JOIN (" +
                    "    SELECT CountryCode, SUM(Population) AS CityPopulation " +
                    "    FROM city " +
                    "    GROUP BY CountryCode) cip " +
                    "    ON co.Code = cip.CountryCode " +
                    "GROUP BY co.Region " +
                    "ORDER BY AreaPopulation DESC;";


            // Execute SQL statement
            ResultSet resultSet = runQuery(selectString);

            // Takes all the data from the result and formats it into an ArrayList of Population.
            return getPopulationDataFromResultSet(resultSet);

        }
        // If any error happens.
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Population data");
            return null;
        }

    }


    /**
     * Turns an ArrayList of capital city reports into a single string with 1 report on each line.
     *
     * @param capitalCities An ArrayList of capital city reports.
     * @return A string that formats each capital city report as 1 line.
     */
    public String FormatCapitalCityReportsAsString(ArrayList<City> capitalCities) {
        if (capitalCities != null) {
            StringBuilder finishedString = new StringBuilder();
            for (City capitalCityReport : capitalCities) {
                finishedString.append(capitalCityReport.name).append(", ");
                finishedString.append(capitalCityReport.countryCode).append(", ");
                finishedString.append(capitalCityReport.population).append("\n");
            }

            return finishedString.toString();
        }

        return null;
    }


    /**
     * Turns an ArrayList of country reports into a single string with 1 report on each line.
     *
     * @param countries An ArrayList of country reports.
     * @return A string that formats each country report as 1 line.
     */
    public String FormatCountryReportsAsString(ArrayList<Country> countries) {
        if (countries != null) {
            StringBuilder finishedString = new StringBuilder();
            for (Country country : countries) {
                finishedString.append(country.CountryCode).append(", ");
                finishedString.append(country.CountryName).append(", ");
                finishedString.append(country.Continent).append(", ");
                finishedString.append(country.Region).append(", ");
                finishedString.append(country.Population).append(", ");
                finishedString.append(country.CapitalCity).append("\n");
            }

            return finishedString.toString();
        }

        return null;
    }

    /**
     * Turns an ArrayList of Population reports into a single string with 1 report on each line.
     *
     * @param populationReports An ArrayList of population reports.
     * @return A string that formats each population report as 1 line.
     */
    public String FormatPopulationReportsAsString(ArrayList<Population> populationReports) {
        if (populationReports != null) {
            StringBuilder finishedString = new StringBuilder();
            for (Population populationReport : populationReports) {
                finishedString.append(populationReport.AreaName).append(", ");
                finishedString.append(populationReport.PopulationOfArea).append(", ");
                finishedString.append(populationReport.PopulationOfAreaInCities).append(", ");
                finishedString.append(populationReport.PopulationOfAreaInCitiesPercent).append(", ");
                finishedString.append(populationReport.PopulationOfAreaNotInCities).append(", ");
                finishedString.append(populationReport.PopulationOfAreaNotInCitiesPercent).append("\n");
            }

            return finishedString.toString();
        }

        return null;
    }

    /**
     * Gets a given number capital cities in a given continent ordered from the highest population to smallest.
     *
     * @return An array list containing City objects.
     */
    public ArrayList<City> GetGivenNumberOfCapitalCitiesInGivenContinentPopDesc(String continent, int numOfCitiesToGet) {
        try {
            // String for SQL statement
            String selectString =
                "SELECT city.Name AS Name, city.CountryCode AS CountryCode, city.Population AS Population"
                    + " FROM country "
                    + " INNER JOIN city ON country.Capital = city.ID "
                    + " WHERE country.Continent = '" + continent + "'"
                    + " ORDER BY Population Desc "
                    + "LIMIT " + numOfCitiesToGet;

            // Execute SQL statement
            ResultSet resultSet = runQuery(selectString);

            // Takes all the data from the result and formats it into an ArrayList of cities.
            return getCapitalCityDataFromResultSet(resultSet);

        }
        // If any error happens.
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    /**
     * SEM34 All Countries by Population Size
     *
     * @return An array list containing Country objects.
     */
    public ArrayList<Country> getAllCountriesOrderedByPop() {
        try {
            // string sql
            String selectString =
                    "SELECT Name AS Name, Code AS Code, Continent AS Continent, Region AS Region, Population AS Population, Capital AS Capital " +
                            "FROM country " +
                            "ORDER BY Population DESC;";

            // execute sql statement
            ResultSet resultSet = runQuery(selectString);

            // puts result into ArrayList of cities.
            return getCountryDataFromResultSet(resultSet);

        }
        // in case of error
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    /**
     * SEM35 All Countries in a Continent by Population
     *
     * @return An array list containing Country objects.
     */
    public ArrayList<Country> getAllCountriesInContinentOrderedByPop(String continentName) {
        try {
            // string sql
            String selectString =
                    "SELECT country.Name AS Name, country.Code AS Code, country.Continent AS Continent, country.Region AS Region, country.Population AS Population, country.Capital AS Capital " +
                    "FROM country " +
                    "WHERE country.Continent = '" + continentName + "'" +
                    " ORDER BY country.Population DESC";

            // execute sql statement
            ResultSet resultSet = runQuery(selectString);

            // puts result into ArrayList of cities.
            return getCountryDataFromResultSet(resultSet);

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    /**
     * SEM36 Countries in a Region by Population
     *
     * @return An array list containing Country objects.
     */
    public ArrayList<Country> getAllCountriesInRegionOrderedByPop(String regionName) {
        try {
            // string sql
            String selectString =
                    "SELECT country.Name AS Name, country.Code AS Code, country.Continent AS Continent, country.Region AS Region, country.Population AS Population, country.CapitalCity AS Capital" +
                            "FROM country " +
                            "INNER JOIN region ON country.RegionCode = region.Code " +
                            "WHERE region.Name = '" + regionName + "' " +  // replace region name
                            "ORDER BY country.Population DESC;";

            // execute sql statement
            ResultSet resultSet = runQuery(selectString);

            // puts result into an cities's arrayList
            return getCountryDataFromResultSet(resultSet);

        }
        // in case of error
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get countries details");
            return null;
        }
    }

    /**
     * SEM38 Top N Populated Countries in a Continent
     *
     * @return An array list containing Country objects.
     */
    public ArrayList<Country> getTopPopCountriesInContinent(int topPopulatedCountries, String continentName) {
        try {
            // Create string sql
            String strSelect =
                    "SELECT Name AS Name, Code AS Code, Name AS Name, Continent AS Continent, Region AS Region, Population AS Population, CapitalCity AS Capital" +
                            "FROM country " +
                            "WHERE Continent = '" + continentName + "' " +
                            "ORDER BY Population DESC " +
                            "LIMIT " + topPopulatedCountries;

            // execute sql statement
            ResultSet resultset = runQuery(strSelect);

            // puts result into countries's arrayList
            return getCountryDataFromResultSet(resultset);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    /**
     * SEM20 Cities in a Region by Population
     *
     * @return An array list containing City objects.
     */
    public ArrayList<City> getAllCitiesInRegionOrderedByPop(String regionName) {
        try {
            // string sql
            String selectString =
                    "SELECT city.Name AS Name, city.countryCode AS CountryCode, city.district AS District, city.country AS Country, city.Region AS Region, city.Population AS Population" +
                            "FROM country " +
                            "INNER JOIN region ON city.RegionCode = region.Code " +
                            "WHERE region.Name = '" + regionName + "' " +  // replace region name
                            "ORDER BY city.Population DESC;";

            // execute sql statement
            ResultSet resultSet = runQuery(selectString);

            // puts result into an cities's arrayList
            return getCityDataFromResultSet(resultSet);

        }
        // in case of error
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get countries details");
            return null;
        }
    }

    /**
     * SEM25 Top N Populated Cities in a Region
     *
     * @return An array list containing City objects.
     */
    public ArrayList<City> getTopPopCitiesInRegion(int topPopulatedCities, String regionName) {
        try {
            // Create string sql
            String strSelect =
                    "SELECT city.Name AS Name, city.countryCode AS CountryCode, city.district AS District, city.country AS Country, city.Region AS Region, city.Population AS Population" +
                            "FROM city " +
                            "INNER JOIN region ON city.RegionCode = region.Code " +
                            "WHERE region.Name = '" + regionName + "' " + // replace region name
                            "LIMIT " + topPopulatedCities;

            // execute sql statement
            ResultSet rset = runQuery(strSelect);

            // puts result into countries's arrayList
            return getCityDataFromResultSet(rset);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    /**
     * SEM24 Top N Populated Cities in a Continent
     *
     * @return An array list containing City objects.
     */

    public ArrayList<City> getTopPopCitiesInContinent(int topPopulatedCities, String continentName) {
        try {
            // Create string sql
            String strSelect =
                    "SELECT city.Name AS Name, city.countryCode AS CountryCode, city.district AS District, city.country AS Country, city.Region AS Region, city.Population AS Population" +
                            "FROM city " +
                            "INNER JOIN continent ON city.ContinentCode = continent.Code " +
                            "WHERE region.Name = '" + continentName + "' " + // replace rcontinent name
                            "LIMIT " + topPopulatedCities;

            // execute sql statement
            ResultSet rset = runQuery(strSelect);

            // puts result into countries's arrayList
            return getCityDataFromResultSet(rset);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    /**
     * SEM26 Top N Populated Cities in a Country
     *
     * @return An array list containing City objects.
     */
    public ArrayList<City> getTopPopCitiesInCountry(int topPopulatedCities, String countryName) {
        try {
            // Create string sql
            String strSelect =
                    "SELECT city.Name AS Name, city.countryCode AS CountryCode, city.district AS District, city.country AS Country, city.Region AS Region, city.Population AS Population" +
                            "FROM city " +
                            "INNER JOIN country ON city.CountryCode = country.Code " +
                            "WHERE country.Name = '" + countryName + "' " + // replace country name
                            "LIMIT " + topPopulatedCities;

            // execute sql statement
            ResultSet rset = runQuery(strSelect);

            // puts result into countries's arrayList
            return getCityDataFromResultSet(rset);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    /**
     * SEM22 Cities in a District by Population
     *
     * @return An array list containing City objects.
     */
    public ArrayList<City> getAllCitiesInDistrictOrderedByPop(String districtName) {
        try {
            // string sql
            String selectString =
                    "SELECT city.Name AS Name, city.countryCode AS CountryCode, city.district AS District, city.country AS Country, city.Region AS Region, city.Population AS Population" +
                            "FROM country " +
                            "INNER JOIN district ON city.district = district.Code " +
                            "WHERE district.Name = '" + districtName + "' " +  // replace district name
                            "ORDER BY city.Population DESC;";

            // execute sql statement
            ResultSet resultSet = runQuery(selectString);

            // puts result into an cities's arrayList
            return getCityDataFromResultSet(resultSet);

        }
        // in case of error
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

}
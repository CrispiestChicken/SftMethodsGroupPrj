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
                con = DriverManager.getConnection("jdbc:mysql://"+location+"/world?allowPublicKeyRetrieval=TRUE&useSSL=false", "root", "example");
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
     *
     * @param results A ResultSet that you get from executing a query.
     * @return An ArrayList containing City objects that contains the data for each city gotten from the result set.
     * @throws Exception Makes it so the person using this method has to deal with exceptions.
     */
    private ArrayList<City> getCityDataFromResultSet(ResultSet results) throws Exception
    {

        ArrayList<City> cities = new ArrayList<>();

        // Declaring city up here so that it isn't declaring it over and over again.
        City city;


        // If there is a row of data it gets the data and stores it in the array list so that it can later be returned.
        while (results.next()) {
            city = new City();
            city.population = results.getInt("Population");
            city.countryCode = results.getString("CountryCode");
            city.district = results.getString("District");
            city.name = results.getString("Name");

            cities.add(city);
        }

        return cities;
    }

    /**
     * @param results A ResultSet that you get from executing a query.
     * @return An ArrayList containing City objects that contains the data for each capital city gotten from the result set.
     * @throws Exception Makes it so the person using this method has to deal with exceptions.
     */
    private ArrayList<City> getCapitalCityDataFromResultSet(ResultSet results) throws Exception
    {

        ArrayList<City> capitalCities = new ArrayList<>();

        // Declaring city up here so that it isn't declaring it over and over again.
        City capitalCity;

        // If there is a row of data it gets the data and stores it in the array list so that it can later be returned.
        while (results.next()) {
            capitalCity = new City();
            capitalCity.name = results.getString("Name");
            capitalCity.population = results.getInt("Population");
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
    private ArrayList<Language> getLanguageDataFromResultSet(ResultSet results) throws Exception
    {

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
    private ArrayList<Country> getCountryDataFromResultSet(ResultSet results) throws Exception
    {
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
            country.Population = results.getInt("Population");
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
    private ArrayList<Population> getPopulationDataFromResultSet(ResultSet results) throws Exception
    {
        ArrayList<Population> populationReport = new ArrayList<>();

        // Declaring population up here so that it isn't declaring it over and over again.
        Population population;

        // If there is a row of data it gets the data and stores it in the array list so that it can later be returned.
        while (results.next()) {
            population = new Population();
            population.AreaName = results.getString("AreaName");
            population.PopulationOfArea = results.getInt("AreaPopulation");
            population.PopulationOfAreaInCities = results.getInt("AreaCityPopulation");
            population.PopulationOfAreaInCitiesPercent = results.getDouble("AreaCityPopulationPercent");
            population.PopulationOfAreaNotInCities = results.getInt("AreaNotInCityPopulation");
            population.PopulationOfAreaNotInCitiesPercent = results.getDouble("AreaNotInCityPopulationPercent");

            populationReport.add(population);
        }

        return populationReport;
    }


    /**
     * This is for getting top populated cities in a district
     * @return ArrayList<City>
     */
    public ArrayList<City>  getTopPopulateCitiesInADistrict(int topPopulatedCities, String districtName)
    {
        try
        {
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

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    /**
     * This gets all the cities
     * @return ArrayList<City>
     */
    public ArrayList<City>  getCity() {
        ArrayList<City> cities = new ArrayList<>();
        try {
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.name, country.name, District, city.Population "
                            + "FROM city join country ON city.CountryCode = country.Code ";
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
     *
     */
    public void displayTopCityPopulationInDistrict(ArrayList<City> cities) {
        if (cities != null) {
            System.out.printf("%-30s %-30s %-30s %n", "Name", "District", "Population ");
            for(City city : cities) {
                System.out.printf("%-30s %-30s %-30s %n", city.name, city.district, city.population);
            }

        }
    }

    /**
     * This displays city report
     *
     */
    public void displayCity(ArrayList<City> cities) {
        if (cities != null) {
            System.out.printf("%-30s %-30s %-30s %-30s %n", "Name", "Country", "District", "Population ");
            for(City city : cities) {
                System.out.printf("%-30s %-30s %-30s %-30s %n", city.name, city.country, city.district, city.population);
            }

        }
    }


    /**
     * This gets the capital city
     *
     */
    /*public Capital_City getCapCity(int cityId) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT Name, CountryCode, Population "         // this is not complete will need to get capital city code from each country
                            + "FROM city "
                            + "WHERE ID = " + cityId;
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Return new employee if valid.
            // Check one is returned
            if (rset.next()) {
                Capital_City Ccity = new Capital_City();
                Ccity.population = rset.getInt("Population");
                Ccity.countryCode = rset.getString("CountryCode");
                Ccity.name = rset.getString("Name");
                return Ccity;
            } else
                return null;
        } catch (Exception f) {
            System.out.println(f.getMessage());
            System.out.println("Failed to get capital city details");
            return null;
        }
    }*/


    /**
     * Gets all the cities ordered from the highest population to smallest.
     *
     * @return An array list containing City objects.
     */
    public ArrayList<City> GetAllCitiesPopDesc()
    {
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
     * Gets all the capital cities ordered from the highest population to smallest.
     *
     * @return An array list containing City objects.
     */
    public ArrayList<City> GetAllCapitalCitiesPopDesc()
    {
        try {
            // String for SQL statement
                    String selectString =
                    "SELECT city.Name AS Name, city.CountryCode AS CountryCode, city.Population AS Population " +
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
     * Gets a given number Cities ordered from the highest population to smallest.
     *
     * @return An array list containing City objects.
     */
    public ArrayList<City> GetGivenNumberOfCapitalCitiesPopDesc(int numOfCitiesToGet)
    {
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
    public ArrayList<Language> LanguageReport()
    {
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

    /**
     *This displays the language report
     */
    public void displayLanguage(ArrayList<Language> LanguagesReport) {
        if (LanguagesReport != null) {
            System.out.printf("%-30s %-30s %-30s %n", "Language", "Population", "Percentage");
            for(Language language : LanguagesReport) {
                System.out.printf("%-30s %-30s %-30s %n", language.Name, language.Number, language.Percentage);
            }

        }
    }


    /**
     * Gets all the capital cities in a given region ordered from the highest population to smallest.
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
                            "WHERE country.Region = " + region +
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


    /**
     * Gets given number of capital cities in a given region ordered from the highest population to smallest.
     * @param region The region you want to get the capital cities from.
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
                            "WHERE country.Region = " + region +
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
     * Gets all the capital cities in a given continent ordered from the highest population to smallest.
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
                            "WHERE country.Continent = " + continent +
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


    /**
     * Gets all the cities in a given continent ordered from the highest population to smallest.
     * @param continent The continent you want to get the cities from.
     * @return An array list containing City objects.
     */
    public ArrayList<City> GetAllCitiesInContinentPopDesc(String continent) {
        try {
            // String for SQL statement
            String selectString =
                    "SELECT city.Name AS Name, city.CountryCode AS CountryCode, city.Population AS Population, city.District AS District" +
                    "FROM country " +
                    "INNER JOIN city ON city.CountryCode = country.Code " +
                    "WHERE country.Continent = " + continent +
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
     * @return An array list containing Population objects 1 for each continent.
     */
    public ArrayList<Population> GetPopulationReportOfAllContinentsTotalPopDesc() {
        try {
            // String for SQL statement
            String selectString =
                    "SELECT co.Continent AS AreaName, " +
                    "    SUM(co.Population) AS AreaPopulation, " +
                    "    SUM(cip.CityPopulation) AS AreaCityPopulation, " +
                    "    SUM(co.Population) - SUM(cip.CityPopulation) AS AreaNonInCityPopulation, " +
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
     * @param capitalCities An ArrayList of capital city reports.
     * @return A string that formats each capital city report as 1 line.
     */
    public String FormatCapitalCityReportsAsString(ArrayList<City> capitalCities)
    {
        if (capitalCities != null)
        {
            StringBuilder finishedString = new StringBuilder();
            for (City capitalCityReport : capitalCities)
            {
                finishedString.append(capitalCityReport.name).append(", ");
                finishedString.append(capitalCityReport.country).append(", ");
                finishedString.append(capitalCityReport.population).append("\n");
            }

            return finishedString.toString();
        }

        return null;
    }


}
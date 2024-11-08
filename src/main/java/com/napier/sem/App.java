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
    public void connect() {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                // Wait a bit for db to start
                Thread.sleep(3000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/world?useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
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

    public City getCity(int cityId) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT Name, CountryCode, District, Population "
                            + "FROM city "
                            + "WHERE ID = " + cityId;
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Return new employee if valid.
            // Check one is returned
            if (rset.next()) {
                City city = new City();
                city.population = rset.getInt("Population");
                city.countryCode = rset.getString("CountryCode");           /* this may need changed to just country and not country code */
                city.district = rset.getString("District");
                city.name = rset.getString("Name");
                return city;
            } else
                return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }


    public void displayCity(City city) {
        if (city != null) {
            System.out.printf("%-15s %-15s %-15s %-15s %n", "Name", "Country Code", "District", "Population ");
            System.out.printf("%-15s %-15s %-15s %-15s %n", city.name, city.countryCode, city.district, city.population);
        }
    }


    public Capital_City getCapCity(int cityId) {
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
    }


    /**
     * Gets all the cities ordered from the highest population to smallest.
     *
     * @return An array list containing City objects.
     */
    public ArrayList<City> GetAllCitiesPopDesc() {
        // The arraylist storing all the city information.
        ArrayList<City> CitiesInPopDesc = new ArrayList<>();

        try {
            // Creating SQL statement
            Statement stmt = con.createStatement();


            // String for SQL statement
            String selectString =
                    "SELECT Name, CountryCode, District, Population "
                            + "FROM city "
                            + "ORDER BY Population Desc ;";


            // Execute SQL statement
            ResultSet resultSet = stmt.executeQuery(selectString);

            // Declaring city up here so that it isn't declaring it over and over again.
            City city;

            // If there is a row of data it gets the data and stores it in the array list so that it can later be returned.
            while (resultSet.next()) {
                city = new City();
                city.population = resultSet.getInt("Population");
                city.countryCode = resultSet.getString("CountryCode");
                city.district = resultSet.getString("District");
                city.name = resultSet.getString("Name");

                CitiesInPopDesc.add(city);

            }
        }
        // If any error happens.
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }

        // Returns the city information to be used as needed.
        return CitiesInPopDesc;
    }



    /**
     * Gets all the capital cities ordered from the highest population to smallest.
     *
     * @return An array list containing Capital_City objects.
     */
    public ArrayList<Capital_City> GetAllCapitalCitiesPopDesc() {
        // The arraylist storing all the capital city information.
        ArrayList<Capital_City> capitalCitiesInPopDesc = new ArrayList<>();

        try {
            // Creating SQL statement
            Statement stmt = con.createStatement();


            // String for SQL statement
            String selectString =
                    "SELECT city.Name, city.CountryCode, city.Population"
                            + "FROM country"
                            + "INNER JOIN city ON city.ID = country.Capital"
                            + "ORDER BY Population Desc ;";


            // Execute SQL statement
            ResultSet resultSet = stmt.executeQuery(selectString);

            // Declaring city up here so that it isn't declaring it over and over again.
            Capital_City capitalCity;

            // If there is a row of data it gets the data and stores it in the array list so that it can later be returned.
            while (resultSet.next()) {
                capitalCity = new Capital_City();
                capitalCity.name = resultSet.getString("Name");
                capitalCity.population = resultSet.getInt("Population");
                capitalCity.countryCode = resultSet.getString("CountryCode");

                capitalCitiesInPopDesc.add(capitalCity);

            }
        }
        // If any error happens.
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }

        // Returns the city information to be used as needed.
        return capitalCitiesInPopDesc;
    }


    /**
     * All the countries in the world organised by largest population to smallest
     * @return An array list containing Country objects.
     */
    public ArrayList<Country> GetAllCountriesPopDesc() {
        // The arraylist storing all the country information.
        ArrayList<Country> CountriesInPopDesc = new ArrayList<>();

        try {
            // Creating SQL statement
            Statement stmt = con.createStatement();


            // String for SQL statement
            String selectString =
                    "SELECT Name, Code, Region, Population, Continent, Capital "
                            + "FROM country "
                            + "ORDER BY Population Desc ;";


            // Execute SQL statement
            ResultSet resultSet = stmt.executeQuery(selectString);

            // Declaring city.
            Country country;

            // Stores data in the array list.
            while (resultSet.next()) {
                country = new Country();
                country.population = resultSet.getInt("Population");
                country.code = resultSet.getString("Code");
                country.name = resultSet.getString("Name");
                country.region = resultSet.getString("Region");
                country.continent = resultSet.getString("Continent");
                country.capital = resultSet.getString("Capital");

                CountriesInPopDesc.add(country);

            }
        }
        // If any error happens.
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get county details");
            return null;
        }

        // Returns the city information to be used as needed.
        return CountriesInPopDesc;
    }




}
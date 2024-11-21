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
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
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

    public ArrayList<City>  getTopPopulateCitiesInADistrict(int topPopulatedCities, String districtName) {
        ArrayList<City> cities = new ArrayList<>();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.name, city.Population, city.District " +
                            "FROM city " +
                            "WHERE city.District = '" + districtName + "' " +
                            "ORDER BY city.Population DESC " +
                            "LIMIT " + topPopulatedCities;
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Return new employee if valid.
            City city;

            // If there is a row of data it gets the data and stores it in the array list so that it can later be returned.
            while (rset.next()) {
                city = new City();
                city.population = rset.getInt("city.Population");
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

    public ArrayList<City>  getCity() {
        ArrayList<City> cities = new ArrayList<>();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.name, country.name, District, city.Population "
                            + "FROM city join country ON city.CountryCode = country.Code ";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
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


    public void displayTopCityPopulationInDistrict(ArrayList<City> citties) {
        if (citties != null) {
            System.out.printf("%-30s %-30s %-30s %n", "Name", "District", "Population ");
            for(City city : citties) {
                System.out.printf("%-30s %-30s %-30s %n", city.name, city.district, city.population);
            }

        }
    }

    public void displayCity(ArrayList<City> citties) {
        if (citties != null) {
            System.out.printf("%-30s %-30s %-30s %-30s %n", "Name", "Country", "District", "Population ");
            for(City city : citties) {
                System.out.printf("%-30s %-30s %-30s %-30s %n", city.name, city.country, city.district, city.population);
            }

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
     * Gets a given number Cities ordered from the highest population to smallest.
     *
     * @return An array list containing Capital_City objects.
     */
    public ArrayList<City> GetGivenNumberOfCapitalCitiesPopDesc(int numOfCitiesToGet)
    {

        // The arraylist storing the capital city information.
        ArrayList<City> capitalCitiesInPopDesc = new ArrayList<>();

        try {
            // Creating SQL statement
            Statement stmt = con.createStatement();


            // String for SQL statement
            // Idk if the limit part of this will work through java, but I can't test it right now.
            String selectString =
                    "SELECT city.Name, city.CountryCode, city.Population"
                            + " FROM country "
                            + " INNER JOIN city ON country.Capital = city.ID "
                            + " ORDER BY Population Desc "
                            + "LIMIT " + numOfCitiesToGet;



            // Execute SQL statement
            ResultSet resultSet = stmt.executeQuery(selectString);

            // Declaring city up here so that it isn't declaring it over and over again.
            City capitalCity;

            // If there is a row of data it gets the data and stores it in the array list so that it can later be returned.
            while (resultSet.next()) {
                capitalCity = new City();
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

}
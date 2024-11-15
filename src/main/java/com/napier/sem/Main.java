package com.napier.sem;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();
        System.out.println("You ARE IN MAIN!!");
        // Connect to database
        a.connect();

        ArrayList<City> city = a.getCity();

        a.displayCity(city);
        // Disconnect from database
        a.disconnect();
    }
}

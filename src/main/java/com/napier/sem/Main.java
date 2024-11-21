package com.napier.sem;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        if (args.length < 1) {
            a.connect("localhost:33060", 10000);
        } else {
            a.connect(args[0], Integer.parseInt(args[1]));
        }

        /*ArrayList<City> city = a.getCity();

        a.displayCity(city);*/

        ArrayList<City> cityPopulationInADistrict = a.getTopPopulateCitiesInADistrict(2, "Zuid-Holland");
        ArrayList<City> cappopdesc = a.GetGivenNumberOfCapitalCitiesPopDesc(1);
        a.displayTopCityPopulationInDistrict(cityPopulationInADistrict);
        ArrayList<Language> LanguageReport = a.LanguageReport();
        a.displayLanguage(LanguageReport);
        a.displayCity(cappopdesc);
        // Disconnect from database
        a.disconnect();
    }
}

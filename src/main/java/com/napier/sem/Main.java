package com.napier.sem;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Create new Application
        App a = new App();

        a.connect("localhost:33060", 10000);


        if (args.length < 1) {

        } else {
            a.connect(args[0], Integer.parseInt(args[1]));
        }


        // Add options to the array.
        ArrayList<String> options = new ArrayList<>();
        options.add("test1");
        options.add("test2");
        options.add("test3");
        options.add("test4");


        String menu = "";
        int counter = 1;
        for (String option : options) {
            menu = menu.concat("\n" + counter + ". " + option);
            counter++;
        }
        // Display menu.
        System.out.println(menu);


        // Choosing the option (1/36).
        int chosenOption = 1;

        // If option invalid tells the user to select a valid option and ending the program.
        if (chosenOption < 1 || chosenOption > 36) {
            System.out.println("Please enter a valid number between 1 and 36");
            return;
        }


        switch (chosenOption) {
            case 1:
                ArrayList<City> fg = a.GetAllCapitalCitiesInRegionPopDesc("Middle East");
                System.out.println(fg.get(0).name);

                break;
            case 2:
                System.out.println("test2Result");
                break;
            case 3:
                System.out.println("test3Result");
                break;
            case 4:
                System.out.println("test4Result");
                break;
            case 5:
                System.out.println("test5Result");
                break;
            case 6:
                System.out.println("test6Result");
                break;
            case 7:
                System.out.println("test7Result");
                break;
            case 8:
                System.out.println("test8Result");
                break;
            case 9:
                System.out.println("test9Result");
                break;
            case 10:
                System.out.println("test10Result");
                break;
            case 11:
                System.out.println("test11Result");
                break;
            case 12:
                System.out.println("test12Result");
                break;
            case 13:
                System.out.println("test13Result");
                break;
            case 14:
                System.out.println("test14Result");
                break;
            case 15:
                System.out.println("test15Result");
                break;
            case 16:
                System.out.println("test16Result");
                break;
            case 17:
                System.out.println("test17Result");
                break;
            case 18:
                System.out.println("test18Result");
                break;
            case 19:
                System.out.println("test19Result");
                break;
            case 20:
                System.out.println("test20Result");
                break;
            case 21:
                System.out.println("test21Result");
                break;
            case 22:
                System.out.println("test22Result");
                break;
            case 23:
                System.out.println("test23Result");
                break;
            case 24:
                System.out.println("test24Result");
                break;
            case 25:
                System.out.println("test25Result");
                break;
            case 26:
                System.out.println("test26Result");
                break;
            case 27:
                System.out.println("test27Result");
                break;
            case 28:
                System.out.println("test28Result");
                break;
            case 29:
                System.out.println("test29Result");
                break;
            case 30:
                System.out.println("test30Result");
                break;
            case 31:
                System.out.println("test31Result");
                break;
            case 32:
                System.out.println("test32Result");
                break;
            case 33:
                System.out.println("test33Result");
                break;
            case 34:
                System.out.println("test34Result");
                break;
            case 35:
                System.out.println("test35Result");
                break;
            case 36:
                System.out.println("test36Result");
                break;









        /*
        ArrayList<City> cittiesInACountryOrederBypopulation = a.getAllCitiesInCountryOrderedByPopulation("China");

        a.displayCity(cittiesInACountryOrederBypopulation);

        ArrayList<Country> topPopulAtedCountriesInARegion = a.getGivenNumOfTopPopulatedCountriesInRegionPopDesc("Caribbean", 5);

        a.displayTopPopulatedCountries(topPopulAtedCountriesInARegion);

        ArrayList<Country> topPopulatedCountry = a.getGivenNumOfTopPopulatedCountriesInTheWorld(10);

        a.displayTopPopulatedCountries(topPopulatedCountry);

        ArrayList<Country> country = a.getGivenPopulationOFCountry("United States");

        a.displayTopPopulatedCountries(country);

        ArrayList<City> capCities = a.getAllCapitalCities();

        a.displayCapitalCity(capCities);

        System.out.println("POPULATION OF CONTINET: "+ a.getPopulationOfAContinent("Asia"));
        /*ArrayList<City> city = a.getCity();

        a.displayCity(city);*/

        /*ArrayList<City> cityPopulationInADistrict = a.getTopPopulateCitiesInADistrict(2, "Zuid-Holland");
        ArrayList<City> cappopdesc = a.GetGivenNumberOfCapitalCitiesPopDesc(1);
        a.displayTopCityPopulationInDistrict(cityPopulationInADistrict);
        ArrayList<Language> LanguageReport = a.LanguageReport();
        a.displayLanguage(LanguageReport);
        a.displayCity(cappopdesc);
        ArrayList<City> capPopDestRep = a.GetAllCapitalCitiesPopDesc();
        a.displayCity(capPopDestRep);
        // Disconnect from database
        a.disconnect();*/
        }
    }
}

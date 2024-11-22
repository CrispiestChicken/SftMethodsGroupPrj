package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    static App app;
    static City city;
    static Language language;
    //Initialises before each tests
    @BeforeAll
    static void init() {
        app = new App();
        city = new City();
        language = new Language();
    }

    //Tests city class
    @Test
    void testCity()
    {
        city.name = "Edinburgh";
        city.countryCode = "SCO";
        city.district = "NONE";
        city.population = 10000;
        String cityName = city.name;
        assertTrue(cityName.equals("Edinburgh"));
    }

    //Tests lanuage class
    @Test
    void testLanguage()
    {
        language.Name = "Chinese";
        String languageName = language.Name;
        assertTrue(languageName.equals("Chinese"));
    }


}
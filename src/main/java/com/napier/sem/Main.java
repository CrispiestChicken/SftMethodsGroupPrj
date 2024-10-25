package com.napier.sem;

public class Main {
    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();
        System.out.println("You ARE IN MAIN!!");
        // Connect to database
        a.connect();

        // Disconnect from database
        a.disconnect();
    }
}

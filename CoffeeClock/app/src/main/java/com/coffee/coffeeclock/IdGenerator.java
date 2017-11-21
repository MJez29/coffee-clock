package com.coffee.coffeeclock;

/*
    IdGenerator
    A small class used to generate unique IDs for Alarms to avoid overlap
 */

public class IdGenerator {
    private static int id = 1;

    public static int getid()
    {
        id++;
        return id;
    }

    // Called when data is recovered to avoid ID overlap
    public static void setid(int newid)
    {
        id = newid;
    }
}

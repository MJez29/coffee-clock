package com.coffee.coffeeclock;

/**
 * Created by James on 2017-11-06.
 */

public class IdGenerator {
    public static int id = 1;
    public static int getid(){
        id++;
        return id;
    }
    public static void setid(int newid){
        id = newid;
    }
}

package com.group8.controllers;

/**
 * Created by AnkanX on 15-10-22.
 */
public class queryResult {

    private final static queryResult instance = new queryResult();

    private String result;

// returns this instance
public static queryResult getInstance()
    {
    return instance;
    }
    // Return the SQL query result
    public String getResult()
    {
     return result;
    }
    // Reset this
    public void setResult()
    {
      this.result="";
    }
    // Set the value of this to the query fetch data
    public void setResult(String data)
    {
        this.result=data;
    }
}

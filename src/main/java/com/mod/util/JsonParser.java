package com.mod.util;


import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class JsonParser
{

    public static Map<String, String> readJsonFileInsideJar(String path)
    {
        try
        {
            InputStream inputStream = JsonParser.class.getResourceAsStream(path);
            if (inputStream == null)
            {
                System.err.println("File not found: " + path);
                return null;
            }
            System.out.println("read json ye hear");

            // Create a reader from the input stream
            java.util.Scanner scanner = new java.util.Scanner(inputStream).useDelimiter("\\A");
            String json = scanner.hasNext() ? scanner.next() : "";
            scanner.close();

            // Parse JSON to a Java Map
            Map<String, String> map = new Gson().fromJson(json, HashMap.class);

            // Close the input stream
            inputStream.close();

            return map;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    //turns a json in string form into a map
    public static Map stringToMap(String content)
    {
        Map<String, String> map = new Gson().fromJson(content, Map.class);
        return map;
    }



}
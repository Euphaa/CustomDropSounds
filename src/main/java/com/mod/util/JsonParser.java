package com.mod.util;


import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class JsonParser
{
    //only usefull for json that definitley only has string keys and values
    public static Map<String, String> readJsonFileInsideJar(String path)
    {
        try
        {
            InputStream inputStream = JsonParser.class.getResourceAsStream(path);
            if (inputStream == null)
            {
                return null;
            }

            java.util.Scanner scanner = new java.util.Scanner(inputStream).useDelimiter("\\A");
            String json = scanner.hasNext() ? scanner.next() : "";
            scanner.close();

            Map<String, String> map = new Gson().fromJson(json, HashMap.class);

            inputStream.close();

            return map;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, String> readJsonFileOutsideJar(String path) {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
            {
                stringBuilder.append(line);
            }
            reader.close();
            String json = stringBuilder.toString();
            return new Gson().fromJson(json, HashMap.class);
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
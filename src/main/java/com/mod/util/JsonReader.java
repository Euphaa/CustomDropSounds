package com.mod.util;


import com.google.gson.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JsonReader
{
    //only usefull for json that definitley only has string keys and values
    public static Map<String, Object> readJsonFileInsideJar(String path)
    {
        try
        {
            InputStream inputStream = JsonReader.class.getResourceAsStream(path);
            if (inputStream == null)
            {
                return null;
            }

            java.util.Scanner scanner = new java.util.Scanner(inputStream).useDelimiter("\\A");
            String json = scanner.hasNext() ? scanner.next() : "";
            scanner.close();

            Map<String, Object> map = new Gson().fromJson(json, HashMap.class);

            inputStream.close();

            return map;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, Object> readJsonFileOutsideJar(String path)
    {
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
            Gson gson = new Gson();
            JsonElement jsonElement = new JsonParser().parse(json); // Corrected here
            return parseJsonElement(jsonElement);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private static Map<String, Object> parseJsonElement(JsonElement jsonElement)
    {
        Map<String, Object> resultMap = new HashMap<>();
        if (!jsonElement.isJsonObject()) return resultMap;

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
        for (Map.Entry<String, JsonElement> entry : entrySet)
        {
            String key = entry.getKey();
            JsonElement value = entry.getValue();
            if (value.isJsonPrimitive())
            {
                resultMap.put(key, parseJsonPrimitive(value.getAsJsonPrimitive()));
            }
            else if (value.isJsonObject() || value.isJsonArray())
            {
                resultMap.put(key, parseJsonElement(value));
            }
        }
        return resultMap;
    }

    private static Object parseJsonPrimitive(JsonPrimitive primitive)
    {
        if (primitive.isNumber())
        {
            if (primitive.getAsString().contains("."))
            {
                return primitive.getAsFloat();
            }
            else
            {
                return primitive.getAsInt();
            }
        }
        else if (primitive.isString())
        {
            return primitive.getAsString();
        }
        else if (primitive.isBoolean())
        {
            return primitive.getAsBoolean();
        }
        return null;
    }

    //turns a json in string form into a map
//    public static Map stringToMap(String content)
//    {
//        Map<String, String> map = new Gson().fromJson(content, Map.class);
//        return map;
//    }

    public static void writeToFile(String path, String txt)
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path)))
        {
            writer.write(txt);
        }
        catch (IOException e)
        {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

}
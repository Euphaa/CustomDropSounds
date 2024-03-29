package com.mod;

import com.google.gson.Gson;
import com.mod.util.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustomDropSounds
{
    public static Map<String, String> dropsAndSounds = new HashMap<>();
    public static Map<String, String> defaultDropsounds = new HashMap<>();
    private static final String settingsFileName = "./CustomDropSounds/userSounds.json";
    public static Map<String, Object> settings;

    //    adds a sound to a particular string detected in chat, given a few conditions
    public static void addSound(String dropName, String soundName)
    {
//        dropsAndSounds.add(new DropSound(dropName, soundName, false));
        dropsAndSounds.put(dropName, soundName);
    }

    public static void addDefaultSound(String dropName, String soundName)
    {
//        defaultDropsounds.add(new DropSound(dropName, soundName, true));
        defaultDropsounds.put(dropName, soundName);
    }

    //finds a sound to play with a name that is mentioned in the message
    public static void PlayFromSounds(String msg)
    {
        boolean gotoDefaults = true;
        for (String dropName : dropsAndSounds.keySet())
        {
            if (msg.contains(dropName))
            {
                if (defaultDropsounds.containsValue(dropsAndSounds.get(dropName)))
                {
                    //the name of the file is the same as one in default sounds, so it plays the one from the defaults
                    playDefaultSound(dropsAndSounds.get(dropName));
                }
                else
                {
                    playCustomSoundFromName(dropsAndSounds.get(dropName));
                }
                gotoDefaults = false;
            }
        }
        //incase something was already played, dont play the default mod sounds
        if (!gotoDefaults) return;
        //now play the default ones
        for (String dropName : defaultDropsounds.keySet())
        {
            if (msg.contains(dropName))
            {
                playDefaultSound(defaultDropsounds.get(dropName));
            }
        }
    }

//    private static void playCustomSoundFromDrop(String dropName) {
//        String path = "./CustomDropSounds/" + dropsAndSounds.get(dropName);
//        SoundFilePlayer.playCustomSound(path, .2f);
//    }

    private static void playCustomSoundFromName(String name)
    {
        String path = "./CustomDropSounds/wavs/" + name;
        SoundFilePlayer.playCustomSound(path, getGlobalVolume());
    }

    //plays a sound from either the user defined or default sounds
    public static void playSoundFromName(String soundName)
    {
        if (defaultDropsounds.containsValue(soundName))
        {
            //the name of the file is the same as one in default sounds, so it plays the one from the defaults
            playDefaultSound(soundName);
        }
        else
        {
            playCustomSoundFromName(soundName);
        }
    }

    private static void playDefaultSound(String sound)
    {
        //runs in a separate thread
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable()
        {
            @Override
            public void run()
            {
                String path = "/assets/CustomDropSounds/wavs/" + sound;
                SoundFilePlayer.playSound(path, getGlobalVolume());
            }
        });
        executor.shutdown();
    }


    //saves custom dropsounds
    public static void saveCustomSounds()
    {
        String json = new Gson().toJson(dropsAndSounds);
        JsonReader.writeToFile("./CustomDropSounds/userSounds.json", json);
    }

    public static float getGlobalVolume()
    {
        if (settings.get("globalVolume") instanceof Float)
        {
            return (float) settings.get("globalVolume");
        }
        return 0;
    }

    public static void setGlobalVolume(float x)
    {
        settings.put("globalVolume", x);
    }

//    public static String getSoundsToString()
//    {
//        return "User defined: " + dropsAndSounds.toString() + "\nDefault: " + defaultDropsounds.toString();
//    }

    public static void saveSettings()
    {
        String json = new Gson().toJson(settings);
        Init.sendMsgToPlayer(json);
        JsonReader.writeToFile("./CustomDropSounds/settings.json", json);
    }


    public static boolean removeSound(String drop) {

        if (!dropsAndSounds.containsKey(drop))
        {
            return false;
        }
        dropsAndSounds.remove(drop);
        return true;
    }
}
package com.mod;

import com.google.gson.Gson;
import com.mod.util.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustomDropSounds
{
    public static Map<String, String> dropsAndSounds = new HashMap<>();
    public static Map<String, String> defaultDropsounds = new HashMap<>();
    private static final String settingsFileName = "./CustomDropSounds/userSounds.json";
    public static float globalVolume = .5f;

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
                Init.sendMsgToPlayer("match detected: " + dropName);
                if (defaultDropsounds.containsValue(dropsAndSounds.get(dropName)))
                {
                    System.out.println("playing default");
                    //the name of the file is the same as one in default sounds, so it plays the one from the defaults
                    playDefaultSound(dropsAndSounds.get(dropName));
                }
                else
                {
                    System.out.println("playing custom");
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

    private static void playCustomSoundFromName(String name) {
        String path = "./CustomDropSounds/wavs/" + name;
        SoundFilePlayer.playCustomSound(path, globalVolume);
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

    private static void playDefaultSound(String sound) {
        //runs in a separate thread
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                String path = "/assets/CustomDropSounds/wavs/" + sound;
                SoundFilePlayer.playSound(path, globalVolume);
            }
        });
        executor.shutdown();
    }


    //saves custom dropsounds
    public static void writeDropsoundsToJson()
    {
        String json = new Gson().toJson(dropsAndSounds);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./CustomDropSounds/userSounds.json")))
        {
            writer.write(json);
        }
        catch (IOException e)
        {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static String getSoundsToString()
    {
        return "User defined: " + dropsAndSounds.toString() + "\nDefault: " + defaultDropsounds.toString();
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
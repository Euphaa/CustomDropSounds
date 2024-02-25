package com.mod;

import com.google.gson.Gson;
import com.mod.commands.*;
import com.mod.util.JsonReader;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@SideOnly(Side.CLIENT)
@Mod(modid = "CustomDropSounds", useMetadata=true)
public class Init {

    public static String resourcesPath;
    public static final String MODID = "CustomDropSounds";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        /* make necessary dirs */
        resourcesPath = generateResourcesPath();
        checkForAndMakeDir("./CustomDropSounds");
        checkForAndMakeDir("./CustomDropSounds/wavs");
        CreateFile("./CustomDropSounds/userSounds.json");
        if (CreateFile("./CustomDropSounds/settings.json"))
        {
            //copy default settings to the one inside the folder
            Map<String, Object> defaultSettings = JsonReader.readJsonFileInsideJar("/assets/CustomDropSounds/settings.json");
            String defaultSettingsJson = new Gson().toJson(defaultSettings);
            JsonReader.writeToFile("./CustomDropSounds/settings.json", defaultSettingsJson);
        }

        /* import dropsounds */

        importCustomSounds();
        importDefaultSounds();

        /* import settings */

        CustomDropSounds.settings = JsonReader.readJsonFileOutsideJar("./CustomDropSounds/settings.json");
        if (!CustomDropSounds.settings.containsKey("globalVolume") || CustomDropSounds.settings.get("globalVolume") instanceof Float)
        {
            //create setting and make it a float
            CustomDropSounds.settings.put("globalVolume", .5f);
        }


        /* event handlers */
        MinecraftForge.EVENT_BUS.register(new ChatListener());

        /* commands */
        ClientCommandHandler.instance.registerCommand(new HelpCommand());
        ClientCommandHandler.instance.registerCommand(new PlaySoundCommand());
        ClientCommandHandler.instance.registerCommand(new PrintSoundsCommand());
        ClientCommandHandler.instance.registerCommand(new SaveSoundsCommand());
        ClientCommandHandler.instance.registerCommand(new SetSoundCommand());
        ClientCommandHandler.instance.registerCommand(new SetVolumeCommand());
        ClientCommandHandler.instance.registerCommand(new RemoveSoundCommand());


    }

    private static void importCustomSounds() {
        Map<String, Object> customSounds = JsonReader.readJsonFileOutsideJar("./CustomDropSounds/userSounds.json");
        if (customSounds != null)
        {
            for (String key : customSounds.keySet())
            {
                if (customSounds.get(key) instanceof String)
                {
                    CustomDropSounds.dropsAndSounds.put(key, (String) customSounds.get(key));
                }
            }
        }
    }

    private static void importDefaultSounds() {
        Map<String, Object> defaultSounds = JsonReader.readJsonFileInsideJar("/assets/CustomDropSounds/defaultDropsounds.json");
        if (defaultSounds != null)
        {
            for (String key : defaultSounds.keySet())
            {
                if (defaultSounds.get(key) instanceof String)
                {
                    CustomDropSounds.defaultDropsounds.put(key, (String) defaultSounds.get(key));
                }
            }
        }
    }

    private static boolean CreateFile(String path) {
        File f = new File(path);
        if (!f.exists())
        {
            try
            {
                f.createNewFile();
                return true;
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }

    //makes a directory only if it doesn't exist already
    public static void checkForAndMakeDir(String path)
    {
        File resourcesDir = new File(path);
        if (!resourcesDir.isDirectory()) {
            //dir doens't exist, time to make one
            resourcesDir.mkdirs();
        }
    }

    //gets the minecraft folder path and appends the mod id
    private String generateResourcesPath()
    {
        String mcFolder = Minecraft.getMinecraft().mcDataDir.getAbsolutePath();
        return mcFolder.substring(0, mcFolder.length() - 1) + MODID + "\\";
    }

    public static void sendMsgToPlayer(String msg) {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        player.addChatComponentMessage(new ChatComponentText(msg));
    }
}

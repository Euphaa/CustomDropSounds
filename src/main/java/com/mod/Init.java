package com.mod;

import com.mod.commands.PlaySoundCommand;
import com.mod.commands.PrintSoundsCommand;
import com.mod.commands.SaveSoundsCommand;
import com.mod.commands.SetSoundCommand;
import com.mod.util.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;
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
        checkForAndMakeDir(resourcesPath);

        /* import dropsounds */
//        ResourceLocation id = new ResourceLocation(Init.MODID, "victory.wav");



        Map<String, String> defaultSounds = JsonParser.readJsonFileInsideJar("/assets/CustomDropSounds/defaultDropsounds.json");
        CustomDropSounds.defaultDropsounds.putAll(defaultSounds);


        /* event handlers */
        MinecraftForge.EVENT_BUS.register(new ChatListener());

        /* commands */
        ClientCommandHandler.instance.registerCommand(new SetSoundCommand());
        ClientCommandHandler.instance.registerCommand(new PrintSoundsCommand());
        ClientCommandHandler.instance.registerCommand(new SaveSoundsCommand());
        ClientCommandHandler.instance.registerCommand(new PlaySoundCommand());

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

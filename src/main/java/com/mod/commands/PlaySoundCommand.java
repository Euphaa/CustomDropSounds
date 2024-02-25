package com.mod.commands;

import com.mod.CustomDropSounds;
import com.mod.Init;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.Sys;

@SideOnly(Side.CLIENT)
public class PlaySoundCommand extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "playsound";
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "/playsound <sound_name.wav>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length < 1) return;
        CustomDropSounds.playSoundFromName(args[0]);
        Init.sendMsgToPlayer(CustomDropSounds.getSoundsToString());
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }
}
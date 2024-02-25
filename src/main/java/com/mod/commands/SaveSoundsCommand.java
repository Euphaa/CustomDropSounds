package com.mod.commands;

import com.mod.CustomDropSounds;
import com.mod.Init;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SaveSoundsCommand extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "savesounds";
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "/savesounds";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        CustomDropSounds.writeDropsoundsToJson();
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }
}

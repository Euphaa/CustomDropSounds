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
public class PrintSoundsCommand extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "printsounds";
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "/printsounds";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        Init.sendMsgToPlayer(CustomDropSounds.getSoundsToString());
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }
}
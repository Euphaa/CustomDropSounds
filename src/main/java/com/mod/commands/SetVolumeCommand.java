package com.mod.commands;

import com.mod.CustomDropSounds;
import com.mod.Init;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;

@SideOnly(Side.CLIENT)
public class SetVolumeCommand extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "volumecds";
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "/volumecds <float between 1 and 0>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length < 1)
        {
            //not enough info given by user D:
            return;
        }

        try
        {
            float newVolume = Float.parseFloat(args[0]) % 1;
            if (newVolume < 0)
            {
                Init.sendMsgToPlayer("§care you stupid?");
                newVolume = 0;
            }
            else if (newVolume > 1)
            {
                Init.sendMsgToPlayer("§cchoose a decimal between 0 and 1");
                newVolume = 1;
            }
            CustomDropSounds.globalVolume = newVolume;
            Init.sendMsgToPlayer("§7Volume set to §6" + newVolume * 100 + "%");
        }
        catch (NumberFormatException e)
        {
            Init.sendMsgToPlayer("§7Thats not a number silly!");
        }
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }
}
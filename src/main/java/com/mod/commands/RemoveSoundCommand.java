package com.mod.commands;

import com.mod.CustomDropSounds;
import com.mod.Init;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RemoveSoundCommand extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "rmcds";
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "/rmcds <drop>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length < 1)
        {
            Init.sendMsgToPlayer("§cnot enough args given");
            return;
        }
        if (!CustomDropSounds.removeSound(args[0]))
        {
            Init.sendMsgToPlayer("§cdoes not exist. check ur spelling.");
            return;
        }
        CustomDropSounds.saveCustomSounds();
        Init.sendMsgToPlayer("§7removed successfully");
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }
}
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
        return "savecds";
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "/savecds";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        CustomDropSounds.saveCustomSounds();
        Init.sendMsgToPlayer("§7Sounds saved to §8./CustomDropSounds/userSounds.json");
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }
}

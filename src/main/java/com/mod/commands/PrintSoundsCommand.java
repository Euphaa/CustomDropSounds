package com.mod.commands;

import com.mod.CustomDropSounds;
import com.mod.Init;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.Sys;
import scala.Int;

import java.util.Map;

@SideOnly(Side.CLIENT)
public class PrintSoundsCommand extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "printcds";
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "/printcds";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
//        Init.sendMsgToPlayer(CustomDropSounds.getSoundsToString());
        StringBuilder printable = new StringBuilder("§7User defined:");
        Map<String, String> customs = CustomDropSounds.dropsAndSounds;
        for (String key : customs.keySet())
        {
            printable.append("\n§6").append(key).append("§7 -> §3").append(customs.get(key));
        }
        printable.append("\n§7Default:");
        Map<String, String> defaults = CustomDropSounds.defaultDropsounds;
        for (String key : defaults.keySet())
        {
            printable.append("\n§6").append(key).append("§7 -> §3").append(defaults.get(key));
        }
        Init.sendMsgToPlayer(printable.toString());
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }
}
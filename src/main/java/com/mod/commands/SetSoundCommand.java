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
public class SetSoundCommand extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "setcds";
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "/setcds <sound_name.wav> <drop name>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length < 2)
        {
            //not enough info given by user D:
            Init.sendMsgToPlayer("§cneed 2 args dumbass, can't bind a sound to nothing");
        }
        String dropName = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        CustomDropSounds.addSound(dropName, args[0]);
        CustomDropSounds.writeDropsoundsToJson();
        Init.sendMsgToPlayer("§7Saved: §3" + args[0] + "§7 plays on §6" + dropName);
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }
}

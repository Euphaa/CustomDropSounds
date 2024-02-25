package com.mod.commands;

import com.mod.CustomDropSounds;
import com.mod.Init;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SetSoundCommand extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "setsound";
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "/setsound <drop name> <sound_name.wav>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length < 2)
        {
            //not enough info given by user D:
            Init.sendMsgToPlayer("need 2 args dumbass, can't bind a sound to nothing");
        }
        CustomDropSounds.addSound(args[0], args[1]);
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }
}

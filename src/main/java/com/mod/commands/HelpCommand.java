package com.mod.commands;

import com.mod.Init;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class HelpCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "helpcds";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/helpcds";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        Init.sendMsgToPlayer(
            "§f||| §6/volume§ecds §6<float> §7- sets the volume of (almost) all sounds played for values 0 - 1. can be run without any args to display the current volume." +
            "\n§f||| §6/play§ecds §6<soundName.wav> §7- plays a sound with a given name (note that sounds have to be in .wav format and be in the ./CustomDropSounds/wavs/ folder)" +
            "\n§f||| §6/print§ecds §7- prints all sound bindings to the chat" +
            "\n§f||| §6/save§ecds §7- saves all sound binds to the disk. done automatically most of the time." +
            "\n§f||| §6/set§ecds §6<SoundName.wav> <drop> §7- sets a sound file to be played when a drop is detected in chat; filename must not have spaces, but the drop can." +
            "\n§f||| §6/rm§ecds §7- removes a sound binding (user provided only) based on the drop string" +
            "\n§f||| §6/help§ecds §7- get a list of commands"
        );
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
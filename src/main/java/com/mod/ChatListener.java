package com.mod;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatListener
{
    @SubscribeEvent
    public void onChatMsgRecieved(ClientChatReceivedEvent event)
    {
        String msg = event.message.getUnformattedText();
        if (!msg.contains(":") || msg.contains("playsoundpls"))
        {
            //the msg doesn't contain a colon; filters out all player messages, which all have a colon.
            CustomDropSounds.PlayFromSounds(msg);
        }
    }
}

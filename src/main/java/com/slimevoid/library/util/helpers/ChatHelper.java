package com.slimevoid.library.util.helpers;

import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class ChatHelper {

    public static IChatComponent getMessage(EnumChatFormatting color, String string, Object... args) {
        ChatComponentTranslation ret = new ChatComponentTranslation(string, args);
        ret.getChatStyle().setColor(color);
        return ret;
    }

}

package net.slimevoid.library.util.helpers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class ChatHelper {

    private static IChatComponent getColouredMessage(EnumChatFormatting color, String message, Object... args) {
        IChatComponent ret = getMessage(message,
                                        args);
        ret.getChatStyle().setColor(color);
        return ret;
    }

    private static IChatComponent getMessage(String message, Object... args) {
        ChatComponentTranslation ret = new ChatComponentTranslation(message, args);
        return ret;
    }

    public static void addMessageToPlayer(EntityPlayer entityplayer, String message, Object... args) {
        entityplayer.addChatMessage(getMessage(message,
                                               args));
    }

    public static void addColouredMessageToPlayer(EntityPlayer entityplayer, EnumChatFormatting color, String message, Object... args) {
        entityplayer.addChatMessage(getColouredMessage(color,
                                                       message,
                                                       args));
    }

    public static void sendChatMessageToAllNear(World world, int x, int y, int z, int range, String message, Object... args) {
        if (world.isRemote) return;
        MinecraftServer.getServer().getConfigurationManager().sendToAllNear(x,
                                                                            y,
                                                                            z,
                                                                            range,
                                                                            world.provider.dimensionId,
                                                                            new S02PacketChat(getMessage(message,
                                                                                                         args)));
    }

}

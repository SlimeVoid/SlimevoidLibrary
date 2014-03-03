package com.slimevoid.library.util.helpers;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayerMP;

import com.slimevoid.library.IPacketHandler;
import com.slimevoid.library.network.PacketUpdate;

import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.NetworkRegistry;

/**
 * Client and Server channels must be registered independently
 */
public class PacketHelper {

    public static HashMap<String, FMLEventChannel> listener = new HashMap<String, FMLEventChannel>();

    public static void registerClientPacketHandler(String modChannel, IPacketHandler handler) {
        /*
         * Register Listeners
         */
        // ClientPacketHandler.listener =
        // NetworkRegistry.INSTANCE.newEventDrivenChannel(modChannel);
        if (!listener.containsKey(modChannel)) {
            listener.put(modChannel,
                         NetworkRegistry.INSTANCE.newEventDrivenChannel(modChannel));
        }
        listener.get(modChannel).register(handler);
    }

    public static void registerPacketHandler(String modChannel, IPacketHandler handler) {
        /*
         * Register Listeners
         */
        if (!listener.containsKey(modChannel)) {
            listener.put(modChannel,
                         NetworkRegistry.INSTANCE.newEventDrivenChannel(modChannel));
        }
        listener.get(modChannel).register(handler);
    }

    public static void sendToPlayer(PacketUpdate packet, EntityPlayerMP entityplayer) {
        listener.get(packet.getChannel()).sendTo(packet.getPacket(),
                                                 entityplayer);
    }

    public static void sendToServer(PacketUpdate packet) {
        listener.get(packet.getChannel()).sendToServer(packet.getPacket());
    }

}

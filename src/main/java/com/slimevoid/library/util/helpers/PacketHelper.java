package com.slimevoid.library.util.helpers;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayerMP;

import com.slimevoid.library.network.PacketUpdate;
import com.slimevoid.library.network.handlers.ClientPacketHandler;
import com.slimevoid.library.network.handlers.ServerPacketHandler;

import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Client and Server channels must be registered independently
 */
public class PacketHelper {

    private static HashMap<String, FMLEventChannel> listener = new HashMap<String, FMLEventChannel>();

    /**
     * Register Listener for mod Channel
     * 
     * Should be called in the Main class of the mod 
     * @param modChannel
     */
    public static void registerListener(String modChannel) {
        listener.put(modChannel,
                     NetworkRegistry.INSTANCE.newEventDrivenChannel(modChannel));
    }

    @SideOnly(Side.CLIENT)
    public static void registerClientHandler(String modChannel, ClientPacketHandler handler) {
        /*
         * Register Handler for Listener
         */
        if (listener.containsKey(modChannel)) {
            listener.get(modChannel).register(handler);
        }
    }

    public static void registerServerHandler(String modChannel, ServerPacketHandler handler) {
        /*
         * Register Handler for Listener
         */
        if (listener.containsKey(modChannel)) {
            listener.get(modChannel).register(handler);
        }
    }

    public static void sendToPlayer(PacketUpdate packet, EntityPlayerMP entityplayer) {
        listener.get(packet.getChannel()).sendTo(packet.getPacket(),
                                                 entityplayer);
    }

    public static void sendToServer(PacketUpdate packet) {
        listener.get(packet.getChannel()).sendToServer(packet.getPacket());
    }

    public static void broadcastPacket(PacketUpdate packet) {
        listener.get(packet.getChannel()).sendToAll(packet.getPacket());
    }

    public static void sendToAllAround(PacketUpdate packet, int x, int y, int z, int range, int dimension) {
        listener.get(packet.getChannel()).sendToAllAround(packet.getPacket(),
                                                          new TargetPoint(dimension, x, y, z, range));
    }

}

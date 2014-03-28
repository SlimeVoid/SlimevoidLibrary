package net.slimevoid.library.util.helpers;

import java.util.Map;

import net.minecraft.entity.player.EntityPlayerMP;
import net.slimevoid.library.network.PacketUpdate;
import net.slimevoid.library.network.handlers.PacketPipeline;

import com.google.common.collect.Maps;

import cpw.mods.fml.relauncher.Side;

/**
 * Client and Server channels must be registered independently
 */
public class PacketHelper {

    private static Map<String, PacketPipeline> channels = Maps.<String, PacketPipeline> newConcurrentMap();

    /**
     * Register Listener for mod Channel
     * 
     * Should be called in the Main class of the mod 
     * @param modChannel
     */
    public static void registerHandler(String modChannel, PacketPipeline handler) {
        if (channels.containsKey(modChannel)) {
            throw new RuntimeException("That channel is already registered");
        }

        channels.put(modChannel,
                     handler);
        channels.get(modChannel).initialize(modChannel);
    }

    public static void sendToPlayer(PacketUpdate packet, EntityPlayerMP entityplayer) {
        channels.get(packet.getChannel()).sendToPlayer(packet,
                                                       entityplayer);
    }

    public static void sendToServer(PacketUpdate packet) {
        channels.get(packet.getChannel()).sendToServer(packet);
    }

    public static void broadcastPacket(PacketUpdate packet) {
        channels.get(packet.getChannel()).broadcastPacket(packet);
    }

    public static void sendToAllAround(PacketUpdate packet, int x, int y, int z, int range, int dimension) {
        channels.get(packet.getChannel()).sendToAllAround(packet,
                                                          x,
                                                          y,
                                                          z,
                                                          range,
                                                          dimension);
    }

}

package com.slimevoid.library.util.helpers;

import com.slimevoid.library.IPacketHandler;
import com.slimevoid.library.network.handlers.ClientPacketHandler;
import com.slimevoid.library.network.handlers.ServerPacketHandler;

import cpw.mods.fml.common.network.NetworkRegistry;

/**
 * Client and Server channels must be registered independently
 */
public class PacketHelper {

    public static void registerClientPacketHandler(String modChannel, IPacketHandler handler) {
        /*
         * Register Listeners
         */
        ClientPacketHandler.listener = NetworkRegistry.INSTANCE.newEventDrivenChannel(modChannel
                                                                                      + "-Client");
        ClientPacketHandler.listener.register(handler);
    }

    public static void registerPacketHandler(String modChannel, IPacketHandler handler) {
        /*
         * Register Listeners
         */
        ServerPacketHandler.listener = NetworkRegistry.INSTANCE.newEventDrivenChannel(modChannel);
        ServerPacketHandler.listener.register(handler);
    }

}

package com.slimevoid.library.network.handlers;

import io.netty.buffer.ByteBufInputStream;

import java.io.DataInputStream;
import java.util.HashMap;
import java.util.Map;

import com.slimevoid.library.IPacketHandler;
import com.slimevoid.library.data.Logger;
import com.slimevoid.library.data.LoggerSlimevoidLib;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent;

public class ClientPacketHandler implements IPacketHandler {

    private Map<Integer, SubPacketHandler> handlers;

    /**
     * Initializes the commonHandler Map
     */
    public ClientPacketHandler() {
        handlers = new HashMap<Integer, SubPacketHandler>();
    }

    /**
     * Register a sub-handler with the server-side packet handler.
     * 
     * @param packetID
     *            Packet ID for the sub-handler to handle.
     * @param handler
     *            The sub-handler.
     */
    public void registerPacketHandler(int packetID, SubPacketHandler handler) {
        if (handlers.containsKey(packetID)) {
            LoggerSlimevoidLib.getInstance(Logger.filterClassName(ClientPacketHandler.class.toString())).write(false,
                                                                                                               "PacketID ["
                                                                                                                       + packetID
                                                                                                                       + "] already registered.",
                                                                                                               Logger.LogLevel.ERROR);
            throw new RuntimeException("PacketID [" + packetID
                                       + "] already registered.");
        }
        handlers.put(packetID,
                     handler);
    }

    /**
     * Retrieves the registered sub-handler from the server side list
     * 
     * @param packetID
     * @return the sub-handler
     */
    public SubPacketHandler getPacketHandler(int packetID) {
        if (!handlers.containsKey(packetID)) {
            LoggerSlimevoidLib.getInstance(Logger.filterClassName(ClientPacketHandler.class.toString())).write(false,
                                                                                                               "Tried to get a Packet Handler for ID: "
                                                                                                                       + packetID
                                                                                                                       + " that has not been registered.",
                                                                                                               Logger.LogLevel.WARNING);
            throw new RuntimeException("Tried to get a Packet Handler for ID: "
                                       + packetID
                                       + " that has not been registered.");
        }
        return handlers.get(packetID);
    }

    @SubscribeEvent
    public void onClientPacket(ClientCustomPacketEvent event) {
        DataInputStream data = new DataInputStream(new ByteBufInputStream(event.packet.payload()));
        try {
            int packetID = data.read();
            this.getPacketHandler(packetID).onPacketData(event);
            data.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

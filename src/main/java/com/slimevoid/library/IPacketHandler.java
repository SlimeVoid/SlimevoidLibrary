package com.slimevoid.library;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ServerCustomPacketEvent;

public interface IPacketHandler {

    @SubscribeEvent
    /**
     * When a server packet is received
     * 
     * you must implement @SubscribeEvent for the implemented method
     * @param event
     */
    public void onServerPacket(ServerCustomPacketEvent event);

    @SubscribeEvent
    /**
     * When a client packet is received
     * 
     * you must implement @SubscribeEvent for the implemented method
     * @param event
     */
    public void onClientPacket(ClientCustomPacketEvent event);

}

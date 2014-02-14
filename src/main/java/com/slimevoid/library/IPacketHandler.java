package com.slimevoid.library;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ServerCustomPacketEvent;

public interface IPacketHandler {

    @SubscribeEvent
    public void onServerPacket(ServerCustomPacketEvent event);

    @SubscribeEvent
    public void onClientPacket(ClientCustomPacketEvent event);

}

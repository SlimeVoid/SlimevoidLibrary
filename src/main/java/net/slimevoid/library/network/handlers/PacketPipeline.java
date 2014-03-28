package net.slimevoid.library.network.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayerMP;
import net.slimevoid.library.data.Logger;
import net.slimevoid.library.data.LoggerSlimevoidLib;
import net.slimevoid.library.network.EurysPacket;
import net.slimevoid.library.network.PacketUpdate;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.relauncher.Side;

@ChannelHandler.Sharable
public class PacketPipeline extends
        MessageToMessageCodec<FMLProxyPacket, EurysPacket> {

    private EnumMap<Side, FMLEmbeddedChannel> channels;

    private Map<Integer, SubPacketHandler>    handlers;

    /**
     * Constructs the PacketPipeline and initialises SubHandler Map
     */
    public PacketPipeline() {
        handlers = new HashMap<Integer, SubPacketHandler>();
    }

    public void initialize(String modChannel) {
        this.channels = NetworkRegistry.INSTANCE.newChannel(modChannel,
                                                            this);
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
            LoggerSlimevoidLib.getInstance(Logger.filterClassName(PacketPipeline.class.toString())).write(false,
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
            LoggerSlimevoidLib.getInstance(Logger.filterClassName(PacketPipeline.class.toString())).write(false,
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

    @Override
    protected void encode(ChannelHandlerContext ctx, EurysPacket msg, List<Object> out) throws Exception {
        ByteBuf buffer = Unpooled.buffer();
        if (!this.handlers.containsKey(msg.getPacketId())) {
            throw new NullPointerException("No Packet Registered for: "
                                           + msg.getClass().getCanonicalName());
        }
        msg.writeData(ctx,
                      buffer);
        FMLProxyPacket proxyPacket = new FMLProxyPacket(buffer.copy(), ctx.channel().attr(NetworkRegistry.FML_CHANNEL).get());
        out.add(proxyPacket);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, FMLProxyPacket msg, List<Object> out) throws Exception {
        ByteBuf payload = msg.payload();
        byte discriminator = payload.readByte();
        this.getPacketHandler(discriminator).onPacketData(ctx,
                                                          payload.slice(),
                                                          out);

    }

    public void sendToPlayer(PacketUpdate packet, EntityPlayerMP entityplayer) {
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(entityplayer);
        this.channels.get(Side.SERVER).writeAndFlush(packet);
    }

    public void sendToServer(PacketUpdate packet) {
        this.channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
        this.channels.get(Side.CLIENT).writeAndFlush(packet);
    }

    public void broadcastPacket(PacketUpdate packet) {
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
        this.channels.get(Side.SERVER).writeAndFlush(packet);
    }

    public void sendToAllAround(PacketUpdate packet, int x, int y, int z, int range, int dimension) {
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(new TargetPoint(dimension, x, y, z, range));
        this.channels.get(Side.SERVER).writeAndFlush(packet);
    }
}

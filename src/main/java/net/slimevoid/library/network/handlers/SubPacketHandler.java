package net.slimevoid.library.network.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.world.World;
import net.slimevoid.library.IPacketExecutor;
import net.slimevoid.library.data.Logger;
import net.slimevoid.library.data.LoggerSlimevoidLib;
import net.slimevoid.library.network.PacketUpdate;

import com.google.common.collect.Maps;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class SubPacketHandler {

    private EnumMap<Side, Map<String, IPacketExecutor>> executors = Maps.newEnumMap(Side.class);

    {
        executors.put(Side.CLIENT,
                      Maps.<String, IPacketExecutor> newConcurrentMap());
        executors.put(Side.SERVER,
                      Maps.<String, IPacketExecutor> newConcurrentMap());
    }

    /**
     * Register an executor with the server-side packet sub-handler.
     * 
     * @param commandID
     *            Command ID for the executor to handle.
     * @param executor
     *            The executor
     */
    public void registerClientExecutor(String commandString, IPacketExecutor executor) {
        if (executors.get(Side.CLIENT).containsKey(commandString)) {
            LoggerSlimevoidLib.getInstance(Logger.filterClassName(this.toString())).write(false,
                                                                                          "Command String ["
                                                                                                  + commandString
                                                                                                  + "] already registered.",
                                                                                          Logger.LogLevel.ERROR);
            throw new RuntimeException("Command String [" + commandString
                                       + "] already registered.");
        }
        executors.get(Side.CLIENT).put(commandString,
                                       executor);
    }

    /**
     * Register an executor with the server-side packet sub-handler.
     * 
     * @param commandID
     *            Command ID for the executor to handle.
     * @param executor
     *            The executor
     */
    public void registerServerExecutor(String commandString, IPacketExecutor executor) {
        if (executors.get(Side.SERVER).containsKey(commandString)) {
            LoggerSlimevoidLib.getInstance(Logger.filterClassName(this.toString())).write(false,
                                                                                          "Command String ["
                                                                                                  + commandString
                                                                                                  + "] already registered.",
                                                                                          Logger.LogLevel.ERROR);
            throw new RuntimeException("Command String [" + commandString
                                       + "] already registered.");
        }
        executors.get(Side.SERVER).put(commandString,
                                       executor);
    }

    /**
     * Abstract method for returning a new instance of PacketMining
     * 
     * @return new Packet
     */
    protected abstract PacketUpdate createNewPacket();

    /**
     * Handles a received packet.
     * 
     * @param packet
     *            The received packet
     * @param world
     *            The world object
     * @param entityplayer
     *            The sending player.
     */
    protected void handleClientPacket(PacketUpdate packet, World world, EntityPlayer entityplayer) {
        LoggerSlimevoidLib.getInstance(Logger.filterClassName(this.getClass().toString())).write(world.isRemote,
                                                                                                 "handlePacket("
                                                                                                         + packet.toString()
                                                                                                         + ", world,"
                                                                                                         + entityplayer.getGameProfile().getName()
                                                                                                         + ")",
                                                                                                 Logger.LogLevel.DEBUG);
        // Fetch the command.
        String command = packet.getCommand();

        // Execute the command.
        if (executors.get(Side.CLIENT).containsKey(command)) {
            executors.get(Side.CLIENT).get(command).execute(packet,
                                                            world,
                                                            entityplayer);
        } else {
            LoggerSlimevoidLib.getInstance(Logger.filterClassName(this.getClass().toString())).write(world.isRemote,
                                                                                                     "handlePacket("
                                                                                                             + packet.toString()
                                                                                                             + ", world,"
                                                                                                             + entityplayer.getGameProfile().getName()
                                                                                                             + ") - UNKNOWN COMMAND",
                                                                                                     LoggerSlimevoidLib.LogLevel.WARNING);
            throw new RuntimeException("Tried to get a Packet Executor for command: "
                                       + command
                                       + " that has not been registered.");
        }
    }

    /**
     * Handles a received packet.
     * 
     * @param packet
     *            The received packet
     * @param world
     *            The world object
     * @param entityplayer
     *            The sending player.
     */
    protected void handleServerPacket(PacketUpdate packet, World world, EntityPlayer entityplayer) {
        LoggerSlimevoidLib.getInstance(Logger.filterClassName(this.getClass().toString())).write(world.isRemote,
                                                                                                 "handlePacket("
                                                                                                         + packet.toString()
                                                                                                         + ", world,"
                                                                                                         + entityplayer.getGameProfile().getName()
                                                                                                         + ")",
                                                                                                 Logger.LogLevel.DEBUG);
        // Fetch the command.
        String command = packet.getCommand();

        // Execute the command.
        if (executors.get(Side.SERVER).containsKey(command)) {
            executors.get(Side.SERVER).get(command).execute(packet,
                                                            world,
                                                            entityplayer);
        } else {
            LoggerSlimevoidLib.getInstance(Logger.filterClassName(this.getClass().toString())).write(world.isRemote,
                                                                                                     "handlePacket("
                                                                                                             + packet.toString()
                                                                                                             + ", world,"
                                                                                                             + entityplayer.getGameProfile().getName()
                                                                                                             + ") - UNKNOWN COMMAND",
                                                                                                     LoggerSlimevoidLib.LogLevel.WARNING);
            throw new RuntimeException("Tried to get a Packet Executor for command: "
                                       + command
                                       + " that has not been registered.");
        }
    }

    public void onPacketData(ChannelHandlerContext ctx, ByteBuf data, List<Object> out) {
        EntityPlayer entityplayer = null;
        PacketUpdate packet = this.createNewPacket();
        packet.readData(ctx,
                        data);
        switch (FMLCommonHandler.instance().getEffectiveSide()) {
        case CLIENT:
            entityplayer = this.getClientPlayer();
            this.handleClientPacket(packet,
                                    entityplayer.worldObj,
                                    entityplayer);
            break;

        case SERVER:
            INetHandler netHandler = ctx.channel().attr(NetworkRegistry.NET_HANDLER).get();
            entityplayer = ((NetHandlerPlayServer) netHandler).playerEntity;
            this.handleServerPacket(packet,
                                    entityplayer.worldObj,
                                    entityplayer);
            break;

        default:
        }

        out.add(packet);
    }

    @SideOnly(Side.CLIENT)
    private EntityPlayer getClientPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }
}

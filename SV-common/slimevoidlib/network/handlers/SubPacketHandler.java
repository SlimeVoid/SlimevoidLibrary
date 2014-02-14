package slimevoidlib.network.handlers;

import io.netty.buffer.ByteBufInputStream;

import java.io.DataInputStream;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.world.World;
import slimevoidlib.IPacketExecutor;
import slimevoidlib.data.Logger;
import slimevoidlib.data.LoggerSlimevoidLib;
import slimevoidlib.network.PacketUpdate;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ServerCustomPacketEvent;

public abstract class SubPacketHandler {

    private Map<String, IPacketExecutor> executors = new HashMap<String, IPacketExecutor>();

    /**
     * Register an executor with the server-side packet sub-handler.
     * 
     * @param commandID
     *            Command ID for the executor to handle.
     * @param executor
     *            The executor
     */
    public void registerPacketHandler(String commandString, IPacketExecutor executor) {
        if (executors.containsKey(commandString)) {
            LoggerSlimevoidLib.getInstance(Logger.filterClassName(this.toString())).write(false,
                                                                                          "Command String ["
                                                                                                  + commandString
                                                                                                  + "] already registered.",
                                                                                          Logger.LogLevel.ERROR);
            throw new RuntimeException("Command String [" + commandString
                                       + "] already registered.");
        }
        executors.put(commandString,
                      executor);
    }

    @SubscribeEvent
    /**
     * Receive a Server packet from the handler.<br>
     * Assembles the packet into an wireless packet and routes to
     * handlePacket().
     */
    public void onServerPacket(ServerCustomPacketEvent event) {
        EntityPlayerMP entityplayer = ((NetHandlerPlayServer) event.handler).playerEntity;
        ByteBufInputStream bbis = new ByteBufInputStream(event.packet.payload());
        DataInputStream data = new DataInputStream(bbis);
        this.assemblePacket(entityplayer.getEntityWorld(),
                            entityplayer,
                            data);
    }

    @SubscribeEvent
    /**
     * Receive a Client packet from the handler.<br>
     * Assembles the packet into an wireless packet and routes to
     * handlePacket().
     */
    public void onClientPacket(ClientCustomPacketEvent event) {
        EntityPlayer entityplayer = Minecraft.getMinecraft().thePlayer;
        ByteBufInputStream bbis = new ByteBufInputStream(event.packet.payload());
        DataInputStream data = new DataInputStream(bbis);
        this.assemblePacket(entityplayer.getEntityWorld(),
                            entityplayer,
                            data);
    }

    protected void assemblePacket(World world, EntityPlayer entityplayer, DataInputStream data) {
        try {
            // Assemble packet
            int packetID = data.read();
            PacketUpdate pU = this.createNewPacket();
            pU.readData(data);
            // Route to handlePacket()
            handlePacket(pU,
                         world,
                         entityplayer);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
    protected void handlePacket(PacketUpdate packet, World world, EntityPlayer entityplayer) {
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
        if (executors.containsKey(command)) {
            executors.get(command).execute(packet,
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

}

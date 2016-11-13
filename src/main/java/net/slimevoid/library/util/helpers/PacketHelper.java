package net.slimevoid.library.util.helpers;

import com.google.common.collect.Maps;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.slimevoid.library.network.PacketUpdate;

import java.util.Map;

/**
 * Client and Server channels must be registered independently
 */
public class PacketHelper {

    private static Map<String, SimpleNetworkWrapper> channels = Maps.<String, SimpleNetworkWrapper>newConcurrentMap();

    /**
     * Register Listener for mod Channel
     * <p/>
     * Should be called in the Main class of the mod
     *
     * @param modChannel
     */
    public static void registerHandler() {
        String modChannel = Loader.instance().activeModContainer().getModId();
        if (channels.containsKey(modChannel)) {
            throw new RuntimeException("That channel is already registered");
        }

        channels.put(modChannel,
                NetworkRegistry.INSTANCE.newSimpleChannel(modChannel));
    }

    @SideOnly(Side.CLIENT)
    public static void registerClientExecutor(Class executor, Class packet, int packetIndex) {
        String modChannel = Loader.instance().activeModContainer().getModId();
        if (channels.containsKey(modChannel)) {
            channels.get(modChannel).registerMessage(executor, packet, packetIndex, Side.CLIENT);
        }
    }

    public static void registerServerExecutor(Class executor, Class packet, int packetIndex) {
        String modChannel = Loader.instance().activeModContainer().getModId();
        if (channels.containsKey(modChannel)) {
            channels.get(modChannel).registerMessage(executor, packet, packetIndex, Side.SERVER);
        }
    }

    public static void sendToPlayer(PacketUpdate packet, EntityPlayerMP entityplayer) {
        channels.get(packet.getChannel()).sendTo(packet,
                entityplayer);
    }

    public static void sendToServer(PacketUpdate packet) {
        channels.get(packet.getChannel()).sendToServer(packet);
    }

    public static void broadcastPacket(PacketUpdate packet) {
        channels.get(packet.getChannel()).sendToAll(packet);
    }

    public static void sendToAllAround(PacketUpdate packet, int x, int y, int z, int range, int dimension) {
        channels.get(packet.getChannel()).sendToAllAround(packet,
                new TargetPoint(x,
                        y,
                        z,
                        range,
                        dimension));
    }
}

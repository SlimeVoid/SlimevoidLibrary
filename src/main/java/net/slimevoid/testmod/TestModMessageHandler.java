package net.slimevoid.testmod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.slimevoid.library.network.PacketUpdate;
import net.slimevoid.library.network.executor.PacketExecutor;

public class TestModMessageHandler extends PacketExecutor<TestModMessage, IMessage> {

    @Override
    public PacketUpdate execute(PacketUpdate packet, World world, EntityPlayer entityplayer) {
        System.out.println(packet.getCommand() + " ¬ " + entityplayer);
        return null;
    }

}
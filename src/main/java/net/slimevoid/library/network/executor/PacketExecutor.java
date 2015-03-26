package net.slimevoid.library.network.executor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.slimevoid.library.network.PacketUpdate;

public abstract class PacketExecutor <REQ extends PacketUpdate, REPLY extends IMessage> implements IPacketExecutor, IMessageHandler<REQ, REPLY> {

	@SuppressWarnings("unchecked")
	@Override
    public REPLY onMessage(REQ message, MessageContext ctx) {
        EntityPlayer entityplayer = null;
        World world = null;
    	switch (ctx.side) {
        case CLIENT:
            entityplayer = this.getClientPlayer();
            world = this.getClientWorld();
            break;

        case SERVER:
            entityplayer = ((NetHandlerPlayServer) ctx.netHandler).playerEntity;
            world = entityplayer.worldObj;
            break;
    	}
		return (REPLY) this.execute(message, world, entityplayer);
    }

    @SideOnly(Side.CLIENT)
    private EntityPlayer getClientPlayer() {
        return FMLClientHandler.instance().getClientPlayerEntity();
    }
    
    @SideOnly(Side.CLIENT)
    private World getClientWorld() {
        return FMLClientHandler.instance().getWorldClient();
    }
}

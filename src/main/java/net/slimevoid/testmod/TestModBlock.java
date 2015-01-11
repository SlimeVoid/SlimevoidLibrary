package net.slimevoid.testmod;

import net.minecraft.block.BlockDirt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.slimevoid.library.util.helpers.PacketHelper;

public class TestModBlock extends BlockDirt {
	
	@Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		TestModMessage message = new TestModMessage();
		message.setCommand("Testing");
		if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
			PacketHelper.sendToServer(message);
		}
		return true;
	}

}

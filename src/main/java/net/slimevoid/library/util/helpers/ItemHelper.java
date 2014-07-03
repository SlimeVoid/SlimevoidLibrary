package net.slimevoid.library.util.helpers;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

public class ItemHelper {

    public static boolean isBlockStack(ItemStack itemstack) {
        return itemstack != null
               && itemstack.getItem() != null
               && itemstack.getItem() instanceof ItemBlock
               && !Block.getBlockFromItem(itemstack.getItem()).hasTileEntity(itemstack.getItemDamage());
    }

    public static boolean isSolidBlockStack(ItemStack itemstack, World world, int x, int y, int z) {
        return isBlockStack(itemstack)
               && Block.getBlockFromItem(itemstack.getItem()).renderAsNormalBlock();
    }

    public static void dropItem(World world, int x, int y, int z, ItemStack itemstack) {
        if (world.isRemote) {
            return;
        } else {
            double d = 0.69999999999999996D;
            double xx = (double) world.rand.nextFloat() * d + (1.0D - d) * 0.5D;
            double yy = (double) world.rand.nextFloat() * d + (1.0D - d) * 0.5D;
            double zz = (double) world.rand.nextFloat() * d + (1.0D - d) * 0.5D;
            EntityItem item = new EntityItem(world, (double) x + xx, (double) y
                                                                     + yy, (double) z
                                                                           + zz, itemstack);
            item.age = 10;
            world.spawnEntityInWorld(item);
            return;
        }
    }
    
    public static void dropItemAtPlayer(EntityPlayer entityplayer, ItemStack stack) {
        EntityItem entityitem = new EntityItem(entityplayer.worldObj, entityplayer.posX + 0.5D, entityplayer.posY + 0.5D, entityplayer.posZ + 0.5D, stack);
        entityplayer.worldObj.spawnEntityInWorld(entityitem);
        if (!(entityplayer instanceof FakePlayer)) {
            entityitem.onCollideWithPlayer(entityplayer);
        }
    }

    public static String correctName(String name) {
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }

    public static String itemstackArrayToIntegers(Object[] input) {
        String concat = "";
        for (int i = 0; i < input.length; i++) {
            String itemstack = null;
            if (input[i] instanceof ItemStack) {
                itemstack = itemstackToName((ItemStack) input[i]);
            }
            if (itemstack != null) {
                concat += itemstack;
                concat += i < input.length - 1 ? " : " : "";
            }
        }
        return concat;
    }

    public static String itemstackToName(ItemStack itemstack) {
        return itemstack != null ? itemstack.getDisplayName() + " | "
                                   + itemstack.stackSize : "null";
    }

    public static String itemstackArrayToStrings(Object[] input) {
        String concat = "";
        for (int i = 0; i < input.length; i++) {
            concat += i > 0 ? " + " : "";
            if (input[i] instanceof ItemStack) {
                concat += itemstackToString((ItemStack) input[i]);
            }
        }
        return concat;
    }

    public static String itemstackToString(ItemStack itemstack) {
        return itemstack != null ? itemstack.getDisplayName() + " | "
                                   + itemstack.stackSize : "null";
    }
}

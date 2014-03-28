/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version. This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details. You should have received a copy of the GNU
 * Lesser General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>
 */
package net.slimevoid.library.nbt;

import io.netty.buffer.ByteBuf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.ByteBufUtils;

public class NBTHelper {

    /**
     * Writes an ItemStack to a, Output Stream
     * 
     * @param data
     * @param itemstack
     */
    public static void writeItemStack(ByteBuf data, ItemStack itemstack) {
        ByteBufUtils.writeItemStack(data,
                                    itemstack);
    }

    /**
     * Reads an ItemStack from an Input Stream
     * 
     * @param data
     * @return
     * @throws IOException
     */
    public static ItemStack readItemStack(ByteBuf data) {
        return ByteBufUtils.readItemStack(data);
    }

    /**
     * Writes a String to the DataOutputStream
     */
    public static void writeString(String stringToWrite, DataOutputStream data) throws IOException {
        if (stringToWrite.length() > 32767) {
            throw new IOException("String too big");
        } else {
            data.writeShort(stringToWrite.length());
            data.writeChars(stringToWrite);
        }
    }

    /**
     * Reads a string from a DataInputStream
     */
    public static String readString(DataInputStream data, int allowedLength) throws IOException {
        short stringLength = data.readShort();

        if (stringLength > allowedLength) {
            throw new IOException("Received string length longer than maximum allowed ("
                                  + stringLength + " > " + allowedLength + ")");
        } else if (stringLength < 0) {
            throw new IOException("Received string length is less than zero! Weird string!");
        } else {
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < stringLength; ++i) {
                stringBuilder.append(data.readChar());
            }

            return stringBuilder.toString();
        }
    }

    /**
     * Writes a compressed NBTTagCompound to the OutputStream
     */
    public static void writeNBTTagCompound(NBTTagCompound nbttagcompound, ByteBuf data) {
        ByteBufUtils.writeTag(data,
                              nbttagcompound);
    }

    /**
     * Reads a compressed NBTTagCompound from the InputStream
     */
    public static NBTTagCompound readNBTTagCompound(ByteBuf data) {
        return ByteBufUtils.readTag(data);
    }

    public static int getTagInteger(ItemStack itemstack, String key, int defaultValue) {
        NBTTagCompound tag = itemstack.getTagCompound();
        if (tag == null) {
            tag = new NBTTagCompound();
        }
        if (tag.hasKey(key)) {
            return tag.getInteger(key);
        } else {
            tag.setInteger(key,
                           defaultValue);
        }
        itemstack.setTagCompound(tag);
        return tag.getInteger(key);
    }
}

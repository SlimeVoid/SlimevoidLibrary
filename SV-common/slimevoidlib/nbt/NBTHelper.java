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
package slimevoidlib.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

public class NBTHelper {

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
									+ stringLength
									+ " > "
									+ allowedLength
									+ ")");
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
	public static void writeNBTTagCompound(NBTTagCompound nbttagcompound, DataOutputStream data) throws IOException {
		if (nbttagcompound == null) {
			data.writeShort(-1);
		} else {
			byte[] bytes = CompressedStreamTools.compress(nbttagcompound);
			data.writeShort((short) bytes.length);
			data.write(bytes);
		}
	}

	/**
	 * Reads a compressed NBTTagCompound from the InputStream
	 */
	public static NBTTagCompound readNBTTagCompound(DataInputStream data) throws IOException {
		short nbtSize = data.readShort();

		if (nbtSize < 0) {
			return null;
		} else {
			byte[] bytes = new byte[nbtSize];
			data.readFully(bytes);
			return CompressedStreamTools.decompress(bytes);
		}
	}
}

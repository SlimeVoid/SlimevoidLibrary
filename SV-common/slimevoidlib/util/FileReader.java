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
package slimevoidlib.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class FileReader {
	public static String readFile(File file) {
		FileInputStream stream = null;
		try {
			stream = new FileInputStream(file);
			FileChannel fc = stream.getChannel();
			MappedByteBuffer bb = fc.map(	FileChannel.MapMode.READ_ONLY,
											0,
											fc.size());
			return Charset.defaultCharset().decode(bb).toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stream != null) stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
}

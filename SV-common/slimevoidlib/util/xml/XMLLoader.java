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
package slimevoidlib.util.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class XMLLoader {
	/**
	 * Variable mapping.
	 */
	protected static Map<String, Integer>	xmlVariables	= new HashMap<String, Integer>();

	/**
	 * Filename filter. Used for filtering out other files than XML files.
	 */
	protected static FilenameFilter			filter			= new FilenameFilter() {
																@Override
																public boolean accept(File dir, String name) {
																	return name.substring(	name.length() - 4,
																							name.length()).equals(".xml");
																}
															};

	/**
	 * Add a XML variable mapping. Variable name can be used in XML files
	 * instead of IDs.
	 * 
	 * @param var
	 *            Variable name.
	 * @param val
	 *            Variable value.
	 */
	public static void addXmlVariable(String var, int val) {
		xmlVariables.put(	var,
							val);
	}

	/**
	 * Checks if a file exists in a directory.
	 * 
	 * @param filename
	 *            The filename.
	 * @param dir
	 *            The directory.
	 * @return True if file exists in directory, false otherwise.
	 */
	protected static boolean checkIfExists(String filename, File dir) {
		return (new File(dir.getPath() + File.separator + filename)).exists();
	}

	/**
	 * Copies a file to a directory.
	 * 
	 * @param from
	 *            Source file.
	 * @param toDir
	 *            Destination directory.
	 * 
	 * @throws IOException
	 */
	protected static void copyDefaultTo(File from, File toDir) throws IOException {
		sendMessage("Copying from default: " + from.getName() + "->"
					+ toDir.getAbsolutePath());

		// Initialize destination file.
		File to = new File(toDir.getPath() + File.separator + from.getName());
		if (!to.exists()) {
			to.createNewFile();
		}

		// File channels.
		FileChannel source = null;
		FileChannel destination = null;

		try {
			source = new FileInputStream(from).getChannel();
			destination = new FileOutputStream(to).getChannel();
			// Copy over entire content from source channel to destination
			// channel.
			destination.transferFrom(	source,
										0,
										source.size());
		} finally {
			// Close the channels when finished.
			if (source != null) source.close();
			if (destination != null) destination.close();
		}
	}

	/**
	 * Fetches a value with set tag from a element node.
	 * 
	 * @param tag
	 *            Tag name.
	 * @param element
	 *            Element node.
	 * @return The tag's value.
	 */
	protected static String getValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = nodes.item(0);
		return node.getNodeValue();
	}

	/**
	 * Send a info message, logger or console.
	 * 
	 * @param error
	 *            The message.
	 */
	protected static void sendMessage(String message) {
		System.out.println(message);
	}

	/**
	 * Send a error message, logger or console.
	 * 
	 * @param error
	 *            The message.
	 */
	protected static void endWithError(String error) {
		System.err.println(error);
	}
}

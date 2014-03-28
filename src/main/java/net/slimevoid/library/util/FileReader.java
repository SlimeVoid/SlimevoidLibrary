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
package net.slimevoid.library.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;

import net.slimevoid.library.core.SlimevoidCore;
import net.slimevoid.library.core.lib.CoreLib;

public class FileReader {

    public static String readFile(String file) {
        InputStream is = FileReader.class.getClassLoader().getResourceAsStream(file);
        return getStringFromInputStream(is);
    }

    public static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        String ls = System.getProperty("line.separator");
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(ls);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

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
    public static boolean checkIfExists(String filename, File dir) {
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
    public static void copyDefaultTo(File from, File toDir) throws IOException {
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
            destination.transferFrom(source,
                                     0,
                                     source.size());
        } finally {
            // Close the channels when finished.
            if (source != null) source.close();
            if (destination != null) destination.close();
        }
    }

    /**
     * Send a info message, logger or console.
     * 
     * @param error
     *            The message.
     */
    public static void sendMessage(String message) {
        SlimevoidCore.console(CoreLib.MOD_ID,
                              message);
    }

    /**
     * Send a error message, logger or console.
     * 
     * @param error
     *            The message.
     */
    public static void endWithError(String error) {
        SlimevoidCore.console(CoreLib.MOD_ID,
                              error);
    }
}

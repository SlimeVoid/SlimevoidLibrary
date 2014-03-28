package net.slimevoid.library.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import net.slimevoid.library.core.SlimevoidCore;
import net.slimevoid.library.core.lib.CoreLib;

public class FileUtils {
    /**
     * List directory contents for a resource folder. Not recursive. This is
     * basically a brute-force implementation. Works for regular files and also
     * JARs.
     * 
     * @author Greg Briggs
     * @editor Eurymachus
     * @param clazz
     *            Any java class that lives in the same place as the resources
     *            you want.
     * @param path
     *            Should end with "/", but not start with one.
     * @return Just the name of each member item, not the full paths.
     * @throws URISyntaxException
     * @throws IOException
     */
    public static String[] getResourceListing(Class clazz, String path) throws URISyntaxException, IOException {
        SlimevoidCore.console(CoreLib.MOD_ID,
                              "Attempting resource load from [" + path
                                      + "] using Class Parent ["
                                      + clazz.getSimpleName() + "]");
        /* attempts to get a valid URL based on the class file and given path */
        URL dirURL = clazz.getClassLoader().getResource(path);

        if (dirURL != null && dirURL.getProtocol().equals("file")) {
            /* A file path: easy enough */
            SlimevoidCore.console(CoreLib.MOD_ID,
                                  "Found resource in file path!");
            return new File(dirURL.toURI()).list();
        }

        if (dirURL == null) {
            /*
             * In case of a jar file, we can't actually find a directory. Have
             * to assume the same jar as clazz.
             */
            String me = clazz.getName().replace(".",
                                                "/") + ".class";
            dirURL = clazz.getClassLoader().getResource(me);
        }

        if (dirURL != null) {
            /* A ZIP/JAR path */
            if (dirURL.getProtocol().equals("jar")
                || dirURL.getProtocol().equals("zip")) {
                /* strip out only the file */
                String filePath = dirURL.getPath().substring(5,
                                                             dirURL.getPath().indexOf("!"));
                Enumeration<? extends ZipEntry> entries = null;
                SlimevoidCore.console(CoreLib.MOD_ID,
                                      "Jar protocol loaded!");
                JarFile jar = new JarFile(URLDecoder.decode(filePath,
                                                            "UTF-8"));
                entries = jar.entries();
                SlimevoidCore.console(CoreLib.MOD_ID,
                                      "JarFile initialized with the following [Path: "
                                              + filePath + ", Name: "
                                              + jar.getName()
                                              + ", Number of entries: "
                                              + entries);
                Set<String> result = new HashSet<String>(); // avoid duplicates
                                                            // in
                // case it is a
                // subdirectory
                if (entries != null) {
                    while (entries.hasMoreElements()) {
                        String name = entries.nextElement().getName();
                        if (name.startsWith(path)) {
                            /* filter according to the path */
                            String entry = name.substring(path.length());
                            int checkSubdir = entry.indexOf("/");
                            if (checkSubdir >= 0) {
                                /*
                                 * if it is a subdirectory, we just return the
                                 * directory name
                                 */
                                entry = entry.substring(0,
                                                        checkSubdir);
                            }
                            /* Strip out empty names */
                            if (entry != "") {
                                result.add(entry);
                            }
                        }
                    }
                    if (result.size() > 0) {
                        SlimevoidCore.console(CoreLib.MOD_ID,
                                              "Resource folder loaded ["
                                                      + path
                                                      + "], Number of resource files ["
                                                      + result.size() + "]");
                        return result.toArray(new String[result.size()]);
                    }
                }
            } else {
                SlimevoidCore.console(CoreLib.MOD_ID,
                                      "Caution: Failed to read URL ["
                                              + dirURL.getPath()
                                              + "], unknown protocol ["
                                              + dirURL.getProtocol()
                                              + " | Pathed to [" + path + "]",
                                      1);

            }
        } else {
            SlimevoidCore.console(CoreLib.MOD_ID,
                                  "Caution: Resource folder entries [" + path
                                          + "] could not be located!",
                                  1);
        }
        UnsupportedOperationException uOE = new UnsupportedOperationException("Cannot list files for URL "
                                                                              + dirURL);
        SlimevoidCore.console(CoreLib.MOD_ID,
                              uOE.getLocalizedMessage(),
                              1);
        throw uOE;
    }

    public static boolean copyFile(final File toCopy, final File destFile) {
        try {
            return FileUtils.copyStream(new FileInputStream(toCopy),
                                        new FileOutputStream(destFile));
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean copyFilesRecusively(final File toCopy, final File destDir) {
        assert destDir.isDirectory();

        if (!toCopy.isDirectory()) {
            return FileUtils.copyFile(toCopy,
                                      new File(destDir, toCopy.getName()));
        } else {
            final File newDestDir = new File(destDir, toCopy.getName());
            if (!newDestDir.exists() && !newDestDir.mkdir()) {
                return false;
            }
            for (final File child : toCopy.listFiles()) {
                if (!FileUtils.copyFilesRecusively(child,
                                                   newDestDir)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static String removeStart(String str, String remove) {
        if (isEmpty(str) || isEmpty(remove)) {
            return str;
        }
        if (str.startsWith(remove)) {
            return str.substring(remove.length());
        }
        return str;
    }

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean copyJarResourcesRecursively(final File destDir, final JarURLConnection jarConnection) throws IOException {

        final JarFile jarFile = jarConnection.getJarFile();

        for (final Enumeration<JarEntry> e = jarFile.entries(); e.hasMoreElements();) {
            final JarEntry entry = e.nextElement();
            if (entry.getName().startsWith(jarConnection.getEntryName())) {
                final String filename = removeStart(entry.getName(), //
                                                    jarConnection.getEntryName());

                final File f = new File(destDir, filename);
                if (!entry.isDirectory()) {
                    final InputStream entryInputStream = jarFile.getInputStream(entry);
                    if (!FileUtils.copyStream(entryInputStream,
                                              f)) {
                        return false;
                    }
                    entryInputStream.close();
                } else {
                    if (!FileUtils.ensureDirectoryExists(f)) {
                        throw new IOException("Could not create directory: "
                                              + f.getAbsolutePath());
                    }
                }
            }
        }
        return true;
    }

    public static boolean copyResourcesRecursively( //
    final URL originUrl, final File destination) {
        try {
            final URLConnection urlConnection = originUrl.openConnection();
            if (urlConnection instanceof JarURLConnection) {
                return FileUtils.copyJarResourcesRecursively(destination,
                                                             (JarURLConnection) urlConnection);
            } else {
                return FileUtils.copyFilesRecusively(new File(originUrl.getPath()),
                                                     destination);
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean copyStream(final InputStream is, final File f) {
        try {
            return FileUtils.copyStream(is,
                                        new FileOutputStream(f));
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean copyStream(final InputStream is, final OutputStream os) {
        try {
            final byte[] buf = new byte[1024];

            int len = 0;
            while ((len = is.read(buf)) > 0) {
                os.write(buf,
                         0,
                         len);
            }
            is.close();
            os.close();
            return true;
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean ensureDirectoryExists(final File f) {
        return f.exists() || f.mkdir();
    }
}

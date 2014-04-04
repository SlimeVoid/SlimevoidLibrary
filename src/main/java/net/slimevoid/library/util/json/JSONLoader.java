package net.slimevoid.library.util.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.HashMap;

import net.slimevoid.library.core.SlimevoidCore;
import net.slimevoid.library.core.lib.CoreLib;
import net.slimevoid.library.util.FileUtils;
import argo.saj.InvalidSyntaxException;

public abstract class JSONLoader {

    private static HashMap<String, JSONLoader> jsonLoaders = new HashMap<String, JSONLoader>();

    public static JSONLoader getJSONLoader(String filename) {
        if (jsonLoaders.containsKey(filename)) {
            return jsonLoaders.get(filename);
        }
        return null;
    }

    public static void registerJSONLoader(JSONLoader loader) {
        if (!jsonLoaders.containsKey(loader.filename)) {
            jsonLoaders.put(loader.filename,
                            loader);
        }
    }

    public static void loadJSON() {
        for (JSONLoader loader : jsonLoaders.values()) {
            loader.loadFile();
        }
    }

    protected Class  _class;
    protected String location;
    protected String filename;

    public JSONLoader(Class clazz, String location, String filename) {
        this._class = clazz;
        this.location = location;
        this.filename = filename;
    }

    public void loadFile() {
        try {
            this.parseJSON(this.readFile());
        } catch (InvalidSyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String readFile() throws IOException {
        InputStream in = this.getInputStream();
        if (in == null) {
            SlimevoidCore.console(this.getModID(),
                                  " Failed to get resource list from ["
                                          + this._class.getSimpleName() + "]["
                                          + this.location + this.filename + "]",
                                  1);
            return "";
        }
        InputStreamReader isr = new InputStreamReader(in);
        BufferedReader reader = new BufferedReader(isr);
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");

        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(ls);
        }

        return stringBuilder.toString();
    }

    /**
     * Loads default XML Recipe files from a directory.
     * 
     * @param dir
     *            Default XML directory.
     */
    public InputStream getInputStream() {
        try {
            // Retrieves the resource listing based on the path and class
            // given
            String[] resourceList = FileUtils.getResourceListing(this._class,
                                                                 this.location);
            // If we retrieved results continue
            if (resourceList.length > 0) {
                // Creates a hashmap of each resource in the list
                for (String file : resourceList) {
                    if (file.equals(this.filename)) {
                        // Returns the file as an InputStream
                        InputStream instr = this._class.getClassLoader().getResourceAsStream(this.location
                                                                                             + this.filename);
                        SlimevoidCore.console(CoreLib.MOD_ID,
                                              "Resource loaded from ["
                                                      + this._class.getSimpleName()
                                                      + "][" + this.location
                                                      + this.filename + "]");
                        return instr;
                    }
                }
            } else {
                SlimevoidCore.console(CoreLib.MOD_ID,
                                      "Caution: Failed to get resource list from ["
                                              + this._class.getSimpleName()
                                              + "][" + location + filename
                                              + "]",
                                      1);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected abstract void parseJSON(String string) throws InvalidSyntaxException;

    protected abstract String getModID();

}

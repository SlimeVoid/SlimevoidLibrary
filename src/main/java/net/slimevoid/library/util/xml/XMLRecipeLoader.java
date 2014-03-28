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
package net.slimevoid.library.util.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.slimevoid.library.core.SlimevoidCore;
import net.slimevoid.library.core.lib.CoreLib;
import net.slimevoid.library.util.FileReader;
import net.slimevoid.library.util.FileUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cpw.mods.fml.common.registry.GameRegistry;

public class XMLRecipeLoader extends XMLLoader {
    /**
     * Default XML files. Are copied over when not already exists.
     */
    private static Map<String, Map<String, InputStream>> defaultLocations = new HashMap<String, Map<String, InputStream>>();

    /**
     * Loads default XML Recipe files from a directory.
     * 
     * @param dir
     *            Default XML directory.
     */
    public static void registerDefaultsFromLocation(Class clazz, String location) {
        // Checks that our location list does not contain a reference already
        if (!defaultLocations.containsKey(location)) {
            try {
                // Retrieves the resource listing based on the path and class
                // given
                String[] resourceList = FileUtils.getResourceListing(clazz,
                                                                     location);
                // If we retrieved results continue
                if (resourceList.length > 0) {
                    // Creates a hashmap of each resource in the list
                    Map<String, InputStream> defaultStreams = new HashMap<String, InputStream>();
                    for (String file : resourceList) {
                        // Returns the file as an InputStream
                        InputStream instr = clazz.getClassLoader().getResourceAsStream(location
                                                                                       + file);
                        // Places that InputStream against its reference name
                        // for use later
                        defaultStreams.put(file,
                                           instr);
                    }
                    // Adds the InputStream HashMap to our resourceLocations Map
                    defaultLocations.put(location,
                                         defaultStreams);
                    SlimevoidCore.console(CoreLib.MOD_ID,
                                          "Resource list loaded from ["
                                                  + clazz.getSimpleName()
                                                  + "][" + location + "]");
                } else {
                    SlimevoidCore.console(CoreLib.MOD_ID,
                                          "Caution: Failed to get resource list from ["
                                                  + clazz.getSimpleName()
                                                  + "][" + location + "]",
                                          1);
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Loads XML Recipe files from a directory.
     * 
     * @param dir
     *            Source directory.
     */
    public static void loadFolder(String locationKey, File dir) {
        // Create the directory if it does not already exist.
        if (!dir.isDirectory()) dir.mkdir();
        if (defaultLocations.containsKey(locationKey)) {
            Map<String, InputStream> defaultStreams = defaultLocations.get(locationKey);

            // Iterate through the default files.
            for (String filename : defaultStreams.keySet()) {
                // If it does not exist in the source directory; copy defaults
                // over.
                if (!FileReader.checkIfExists(filename,
                                              dir)) {
                    File newFile = new File(dir.getPath() + File.separator
                                            + filename);
                    if (FileUtils.copyStream(defaultStreams.get(filename),
                                             newFile)) {
                        SlimevoidCore.console(CoreLib.MOD_ID,
                                              "Default was file loaded ["
                                                      + newFile.getName() + "]");
                    } else {
                        SlimevoidCore.console(CoreLib.MOD_ID,
                                              "Failed to load default file ["
                                                      + newFile.getName() + "]");
                    }
                } else {
                    SlimevoidCore.console(CoreLib.MOD_ID,
                                          "File ["
                                                  + filename
                                                  + "] already exists! Skipping...");
                }
            }

            // Iterate through XML files in the source directory.
            for (File xml : dir.listFiles(filter)) {
                // Load the XML file.
                loadXML(xml);
            }
            defaultLocations.remove(locationKey);
        } else {
            SlimevoidCore.console(CoreLib.MOD_ID,
                                  "Caution: Could not load default settings from ["
                                          + locationKey + "]");
        }
    }

    /**
     * Load a specific XML Recipe file.
     * 
     * @param file
     *            Source file.
     */
    public static void loadXML(File file) {
        try {
            // Set up the XML document.
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            // Fetch and iterate through all recipe nodes.
            NodeList nodes = doc.getElementsByTagName("recipe");
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);

                // If the node is an element node; assemble the recipe.
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    assemble(element,
                             file);
                }
            }

        } catch (ParserConfigurationException e) {
            // This happens when the parser settings are fooked up.
            // Serious business! Very rare.
            FileReader.endWithError("Could not parse XML: " + file.getName());
            e.printStackTrace();
        } catch (SAXException e) {
            // This happens when the XML markup is fooked up.
            // The syntax must be correct XML. One root node, closed nodes, etc
            // etc.
            FileReader.endWithError("Could not parse XML markup: "
                                    + file.getName());
            e.printStackTrace();
        } catch (IOException e) {
            // File I/O error.
            // Did not exist? No read/write permissions?
            FileReader.endWithError("Could not read XML: " + file.getName());
            e.printStackTrace();
        }
    }

    /**
     * Assemble a recipe Element node.
     * 
     * @param element
     *            Element node.
     * @param xmlFile
     *            Source XML File.
     */
    private static void assemble(Element element, File xmlFile) {
        // The layout
        String[] recipeLayout = null;

        // Output stack size. Defaults to one.
        int recipeStackSize = 1;

        // Output ID. Must be set or it will skip the recipe.
        Item outItem = null;
        // Output meta data. Defaults to 0.
        int outMeta = 0;

        // The input recipe map.
        // Maps up itemstacks to layout characters.
        Map<String, ItemStack> recipeMap = new HashMap<String, ItemStack>();

        /************************************
         * \ Fetch stack size, meta and outId * \
         ************************************/
        NamedNodeMap recAttrs = element.getAttributes(); // recipe attributes.
        for (int j = 0; j < recAttrs.getLength(); j++) {
            // StackSize attribute was found.
            if (recAttrs.item(j).getNodeName().equals("stackSize")) {
                try {
                    // Attempt to parse attribute integer.
                    recipeStackSize = Integer.parseInt(recAttrs.item(j).getNodeValue());
                } catch (NumberFormatException e) {
                } // Ignore it completely if failed.
            }
            // Metadata attribute was found.
            if (recAttrs.item(j).getNodeName().equals("meta")) {
                String outMetaStr = recAttrs.item(j).getNodeValue();
                outMeta = xmlValueToInteger(outMetaStr);
            }
            // Out ID attribute was found.
            if (recAttrs.item(j).getNodeName().equals("outId")) {
                String outIdStr = recAttrs.item(j).getNodeValue();
                outItem = xmlValueToItem(outIdStr);
            }
        }

        // Do not continue without ID.
        if (outItem == null) {
            FileReader.endWithError("recipe.outID not set! ("
                                    + xmlFile.getName() + ")");
            return;
        }

        /****************
         * \ Fetch layout * \
         ****************/
        // Split it up by newline
        recipeLayout = getValue("layout",
                                element).split("\n");
        int nextI = 0;
        for (int i = 0; i < recipeLayout.length; i++) {
            // Trim the line. Removing all leading and trailing whitespaces.
            recipeLayout[i] = recipeLayout[i].trim();
            // If its not empty, add it to next index and set current to null.
            if (!recipeLayout[i].equals("")) {
                recipeLayout[nextI] = recipeLayout[i];
                recipeLayout[i] = null;
                nextI++;
                // If it is empty, set it to null.
            } else {
                recipeLayout[i] = null;
            }
        }
        // When layout finishes, the array will be n-length, where n is number
        // of newlines.
        // But the actual layout stuff is moved to the top of the array, where
        // rest is null.

        /*******************
         * \ Fetch mappings * \
         *******************/
        NodeList blockNodes = element.getElementsByTagName("mapping");
        // Iterate through each mapping.
        for (int i = 0; i < blockNodes.getLength(); i++) {
            Node node = blockNodes.item(i);

            // Only care about the node if it is an element node.
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                NamedNodeMap attrs = node.getAttributes();
                // The input object's ID.
                Item item = null;
                // The input object's metadata.
                int meta = 0;

                // Iterate through mapping attributes.
                for (int j = 0; j < attrs.getLength(); j++) {
                    // ID attribute was found.
                    if (attrs.item(j).getNodeName().equals("id")) {
                        String idStr = attrs.item(j).getNodeValue();
                        item = xmlValueToItem(idStr);
                    }
                    // Metadata attribute was found.
                    if (attrs.item(j).getNodeName().equals("meta")) {
                        String metaStr = attrs.item(j).getNodeValue();
                        meta = xmlValueToInteger(metaStr);
                    }
                }

                // Do not continue without ID.
                if (item == null) {
                    FileReader.endWithError("mapping.id not set! ("
                                            + xmlFile.getName() + ")");
                    return;
                }

                // Add the input mapping to the recipe map.
                // The node's value is the variable.
                recipeMap.put(node.getChildNodes().item(0).getNodeValue(),
                              new ItemStack(item, 1, meta));
            }
        }

        /*******************
         * \ Assemble recipe * \
         *******************/
        // The recipe input array.
        List<Object> recipe = new ArrayList<Object>();
        // Iterate through layout.
        for (int i = 0; i < recipeLayout.length; i++) {
            // Add layout string if it is not null.
            if (recipeLayout[i] != null) {
                recipe.add(recipeLayout[i]);
            }
        }
        // Iterate through mappings.
        for (String key : recipeMap.keySet()) {
            // First add the variable...
            recipe.add(Character.valueOf(key.toCharArray()[0]));
            // ...then the item stack.
            recipe.add(recipeMap.get(key));
        }

        // Register recipe.
        // Output itemstack and convert the list to object array.
        registerRecipe(new ItemStack(outItem, recipeStackSize, outMeta),
                       recipe.toArray());
    }

    private static int xmlValueToInteger(String xmlString) {
        int value = 0;
        try {
            // Try to parse attribute integer
            value = Integer.parseInt(xmlString);
        } catch (NumberFormatException e) {
            // Integer parsin failed, try checking if it is a
            // variable string
            if (xmlVariables.containsKey(xmlString)) {
                // If it was a variable string, use the variable
                // mapping instead
                value = xmlVariables.get(xmlString);
            }
        }
        return value;
    }

    private static Item xmlValueToItem(String xmlString) {
        int value = 0;
        try {
            // Try to parse attribute integer
            value = Integer.parseInt(xmlString);
        } catch (NumberFormatException e) {
            // Integer parsin failed, try checking if it is a
            // variable string
            if (xmlVariables.containsKey(xmlString)) {
                // If it was a variable string, use the variable
                // mapping instead
                value = xmlVariables.get(xmlString);
            }
        }
        return Item.getItemById(value);
    }

    /**
     * Register a recipe.<br>
     * Uses Minecraft API (Forge/Modloader) specific method of registration.
     * 
     * @param output
     *            Recipe output.
     * @param input
     *            Recipe input.
     */
    private static void registerRecipe(ItemStack output, Object[] input) {
        GameRegistry.addRecipe(output,
                               input);
        FileReader.sendMessage("Adding recipe for: "
                               + output.getUnlocalizedName());
    }
}

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
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;

import net.slimevoid.library.core.SlimevoidCore;
import net.slimevoid.library.core.lib.CoreLib;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class XMLLoader {
    /**
     * Variable mapping.
     */
    protected static Map<String, Integer> xmlVariables = new HashMap<String, Integer>();

    /**
     * Filename filter. Used for filtering out other files than XML files.
     */
    protected static FilenameFilter       filter       = new FilenameFilter() {
                                                           @Override
                                                           public boolean accept(File dir, String name) {
                                                               return name.substring(name.length() - 4,
                                                                                     name.length()).equals(".xml");
                                                           }
                                                       };

    private static int getInteger(String value) {
        int returns;
        try {
            returns = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1;
        }
        return returns;
    }

    /**
     * Add a XML variable mapping. Variable name can be used in XML files
     * instead of IDs.
     * 
     * @param var
     *            Variable name.
     * @param val
     *            Variable value.
     * @return
     */
    public static void addXmlVariable(String var, int val) {
        if (xmlVariables.containsKey(var)) {
            for (int i = 1; i < 4096; i++) {
                String tempVar = var + i;
                if (!xmlVariables.containsKey(tempVar)) {
                    var += i;
                    break;
                }
            }
        }
        Integer flag = xmlVariables.put(var,
                                        val);

        if (flag != null) {
            SlimevoidCore.console(CoreLib.MOD_ID,
                                  "XML Variable replaced ID [" + flag
                                          + "] with ID [" + val
                                          + "] and mapped to " + var);
        } else {
            SlimevoidCore.console(CoreLib.MOD_ID,
                                  "XML Variable loaded for [" + var + "] @ID ["
                                          + val + "]");
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
}

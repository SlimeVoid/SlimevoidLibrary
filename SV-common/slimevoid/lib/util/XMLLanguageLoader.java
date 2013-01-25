package slimevoid.lib.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class XMLLanguageLoader extends XMLLoader {
	/**
	 * Default XML files.
	 * Are copied over when not already exists.
	 */
	private static Map<String,File> defaults = new HashMap<String, File>();
	
	/**
	 * Item mapping.
	 */
	protected static Map<Integer,Item> items = new HashMap<Integer, Item>();
	

	public static void addItemMapping(Item item) {
		addItemMapping(item.itemID+256, item);
	}
	/**
	 * Add a item mapping.
	 * Items must be registered to be used with XML language mappings.
	 * 
	 * @param id Item id
	 * @param item The item
	 */
	public static void addItemMapping(int id, Item item) {
		items.put(id,item);
	}
	
	/**
	 * Loads default XML Recipe files from a directory.
	 * 
	 * @param dir Default XML directory.
	 */
	public static void loadDefaults( File dir ) {
		// Ignore if the dir is not a directory.
		if ( dir == null || !dir.exists() || !dir.isDirectory() )
			return;
		
		// Iterates through all XML files in the directory and adds to default.
		for ( File xml: dir.listFiles(filter) ) {
			defaults.put(xml.getName(), xml);
		}
	}
	
	/**
	 * Loads XML Recipe files from a directory.
	 * 
	 * @param dir Source directory.
	 */
	public static void loadFolder( File dir ) {
		// Create the directory if it does not already exist.
		if ( !dir.isDirectory() )
			dir.mkdir();
		
		// Iterate through the default files.
		for ( String filename: defaults.keySet() ) {
			// If it does not exist in the source directory; copy defaults over.
			if ( !checkIfExists(filename,dir) ) {
				try {
					copyDefaultTo(defaults.get(filename),dir);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		// Iterate through XML files in the source directory.
		for ( File xml: dir.listFiles(filter) ) {
			// Load the XML file.
			loadXML(xml);
		}
	}
	/**
	 * Load a specific XML Recipe file.
	 * 
	 * @param file Source file.
	 */
	public static void loadXML(File file) {
		try {
			// Set up the XML document.
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
			
			// Fetch and iterate through all recipe nodes.
			NodeList nodes = doc.getElementsByTagName("name");
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
	
				// If the node is an element node; assemble the recipe.
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					assemble(element, file);
				}
			}

		} catch (ParserConfigurationException e) {
			// This happens when the parser settings are fooked up.
			// Serious business! Very rare.
			endWithError("Could not parse XML: "+file.getName());
			e.printStackTrace();
		} catch (SAXException e) {
			// This happens when the XML markup is fooked up.
			// The syntax must be correct XML. One root node, closed nodes, etc etc.
			endWithError("Could not parse XML markup: "+file.getName());
			e.printStackTrace();
		} catch (IOException e) {
			// File I/O error.
			// Did not exist? No read/write permissions?
			endWithError("Could not read XML: "+file.getName());
			e.printStackTrace();
		}
	}
	
	/**
	 * Assemble a name Element node.
	 * 
	 * @param element Element node.
	 * @param xmlFile Source XML File.
	 */
	private static void assemble(Element element, File xmlFile) {
		int objId = 0;

		/***************\
		 * Fetch objId *
		\***************/
		NamedNodeMap recAttrs = element.getAttributes(); // name attributes.
		for ( int j = 0; j < recAttrs.getLength(); j++ ) {
			// Object ID attribute was found.
			if ( recAttrs.item(j).getNodeName().equals("objId") ) {
				String objIdStr = recAttrs.item(j).getNodeValue();
				try {
					// Try to parse attribute integer.
					objId = Integer.parseInt(objIdStr);
				} catch( NumberFormatException e ) {
					// Integer parsing failed, try checking if it is a variable strings.
					if (xmlVariables.containsKey(objIdStr)) {
						// If it was a variable string, use the variable mapping instead.
						objId = xmlVariables.get(objIdStr);
					}
				}
			}
		}
		
		// Do not continue without ID.
		if ( objId == 0 ) {
			endWithError("name.objId not set! ("+xmlFile.getName()+")");
			return;
		}

		/********************\
		 * Fetch  languages *
		\********************/
		NodeList languageNodes = element.getElementsByTagName("language");
		// Iterate through each mapping.
		for (int i = 0; i < languageNodes.getLength(); i++) {
			Node node = languageNodes.item(i);

			
			// Only care about the node if it is an element node.
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				NamedNodeMap attrs = node.getAttributes();

				// The lang.
				String lang = "en_US";
				
				// Iterate through language attributes.
				for ( int j = 0; j < attrs.getLength(); j++ ) {
					// lang attribute was found.
					if ( attrs.item(j).getNodeName().equals("lang") ) {
						lang = attrs.item(j).getNodeValue();
					}
				}

				String name = node.getChildNodes().item(0).getNodeValue();
				
				registerName(
						objId,
						lang,
						name
				);
			}
		}
	}
	
	/**
	 * Register a name.<br>
	 * Uses Minecraft API (Forge/Modloader) specific method of registration.
	 *
	 * @param item Item to register
	 * @param lang Language to register for
	 * @param name Name to register
	 */
	private static void registerName(int objId, String lang, String name) {
		Object obj = null;
		if ( Block.blocksList.length > objId && Block.blocksList[objId] != null )
			obj = Block.blocksList[objId];
		else if ( Item.itemsList.length > objId && Item.itemsList[objId] != null )
			obj = Item.itemsList[objId];
		else if (items.containsKey(objId) ){
			obj = items.get(objId);
		} else {
			endWithError("Could not find object with ID! "+objId);
			return;
		}
		
		LanguageRegistry.instance().addNameForObject(
				obj, 
				lang, 
				name
		);
		sendMessage("Adding name: "+objId+":"+lang+":"+name);
	}
}

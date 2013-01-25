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
import net.minecraft.item.ItemStack;

public class XMLRecipeLoader extends XMLLoader {
	/**
	 * Default XML files.
	 * Are copied over when not already exists.
	 */
	private static Map<String,File> defaults = new HashMap<String, File>();
	
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
			NodeList nodes = doc.getElementsByTagName("recipe");
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
	 * Assemble a recipe Element node.
	 * 
	 * @param element Element node.
	 * @param xmlFile Source XML File.
	 */
	private static void assemble(Element element, File xmlFile) {
		// The layout
		String[] recipeLayout = null;
		
		// Output stack size. Defaults to one.
		int recipeStackSize = 1;
		// Output ID. Must be set or it will skip the recipe.
		int outId = 0;
		// Output meta data. Defaults to 0.
		int outMeta = 0;
		
		// The input recipe map.
		// Maps up itemstacks to layout characters.
		Map<String,ItemStack> recipeMap = new HashMap<String,ItemStack>();
		
		
		/************************************\
		 * Fetch stack size, meta and outId *
		\************************************/
		NamedNodeMap recAttrs = element.getAttributes(); // recipe attributes.
		for ( int j = 0; j < recAttrs.getLength(); j++ ) {
			// StackSize attribute was found.
			if ( recAttrs.item(j).getNodeName().equals("stackSize") ) {
				try {
					// Attempt to parse attribute integer.
					recipeStackSize = Integer.parseInt(recAttrs.item(j).getNodeValue());
				} catch ( NumberFormatException e) {} //Ignore it completely if failed.
			}
			// Metadata attribute was found.
			if ( recAttrs.item(j).getNodeName().equals("meta") ) {
				try {
					// Attempt to parse attribute integer.
					outMeta = Integer.parseInt(recAttrs.item(j).getNodeValue());
				} catch ( NumberFormatException e) {} //Ignore it completely if failed.
			}
			// Out ID attribute was found.
			if ( recAttrs.item(j).getNodeName().equals("outId") ) {
				String outIdStr = recAttrs.item(j).getNodeValue();
				try {
					// Try to parse attribute integer.
					outId = Integer.parseInt(outIdStr);
				} catch( NumberFormatException e ) {
					// Integer parsing failed, try checking if it is a variable strings.
					if (xmlVariables.containsKey(outIdStr)) {
						// If it was a variable string, use the variable mapping instead.
						outId = xmlVariables.get(outIdStr);
					}
				}
			}
		}
		
		// Do not continue without ID.
		if ( outId == 0 ) {
			endWithError("recipe.outID not set! ("+xmlFile.getName()+")");
			return;
		}
		
		
		/****************\
		 * Fetch layout *
		\****************/
		// Split it up by newline
		recipeLayout = getValue("layout",element).split("\n");
		int nextI = 0;
		for ( int i = 0; i < recipeLayout.length; i++ ) {
			// Trim the line. Removing all leading and trailing whitespaces.
			recipeLayout[i] = recipeLayout[i].trim();
			// If its not empty, add it to next index and set current to null.
			if ( !recipeLayout[i].equals("") ) {
				recipeLayout[nextI] = recipeLayout[i];
				recipeLayout[i] = null;
				nextI++;
			// If it is empty, set it to null.
			} else {
				recipeLayout[i] = null;
			}
		}
		// When layout finishes, the array will be n-length, where n is number of newlines.
		// But the actual layout stuff is moved to the top of the array, where rest is null.

		
		/*******************\
		 * Fetch  mappings *
		\*******************/
		NodeList blockNodes = element.getElementsByTagName("mapping");
		// Iterate through each mapping.
		for (int i = 0; i < blockNodes.getLength(); i++) {
			Node node = blockNodes.item(i);
			
			// Only care about the node if it is an element node.
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				NamedNodeMap attrs = node.getAttributes();
				// The input object's ID.
				int id = 0;
				// The input object's metadata.
				int meta = 0;
				
				// Iterate through mapping attributes.
				for ( int j = 0; j < attrs.getLength(); j++ ) {
					// ID attribute was found.
					if ( attrs.item(j).getNodeName().equals("id") ) {
						String idStr = attrs.item(j).getNodeValue();
						try {
							// Attempt to parse attribute integer.
							id = Integer.parseInt(idStr);
						} catch( NumberFormatException e ) {
							// Integer parsing failed, try checking if it is a variable strings.
							if (xmlVariables.containsKey(idStr)) {
								// If it was a variable string, use the variable mapping instead.
								id = xmlVariables.get(idStr);
							}
						}
					}
					// Metadata attribute was found.
					if ( attrs.item(j).getNodeName().equals("meta") ) {
						String metaStr = attrs.item(j).getNodeValue();
						try {
							// Attempt to parse attribute integer.
							meta = Integer.parseInt(metaStr);
						} catch( NumberFormatException e ) {} //Ignore it completely if failed.
					}
				}

				// Do not continue without ID.
				if ( id == 0 ) {
					endWithError("mapping.id not set! ("+xmlFile.getName()+")");
					return;
				}
				
				// Add the input mapping to the recipe map.
				// The node's value is the variable.
				recipeMap.put(
						node.getChildNodes().item(0).getNodeValue(), 
						new ItemStack(id,1,meta)
				);
			}
		}
		
		/*******************\
		 * Assemble recipe *
		\*******************/
		// The recipe input array.
		List<Object> recipe = new ArrayList<Object>();
		// Iterate through layout.
		for ( int i = 0; i < recipeLayout.length; i++ ) {
			// Add layout string if it is not null.
			if ( recipeLayout[i] != null ) {
				recipe.add(recipeLayout[i]);
			}
		}
		// Iterate through mappings.
		for ( String key: recipeMap.keySet() ) {
			// First add the variable...
			recipe.add(Character.valueOf(key.toCharArray()[0]));
			// ...then the item stack.
			recipe.add(recipeMap.get(key));
		}


		// Register recipe.
		// Output itemstack and convert the list to object array.
		registerRecipe(
				new ItemStack(outId,recipeStackSize,outMeta),
				recipe.toArray()
		);
	}
	
	/**
	 * Register a recipe.<br>
	 * Uses Minecraft API (Forge/Modloader) specific method of registration.
	 * 
	 * @param output Recipe output.
	 * @param input Recipe input.
	 */
	private static void registerRecipe(ItemStack output, Object[] input) {
		GameRegistry.addRecipe(
				output,
				input
		);
		sendMessage("Adding recipe: "+output.itemID);
	}
}

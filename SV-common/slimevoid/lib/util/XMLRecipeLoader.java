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

public class XMLRecipeLoader {
	private static Map<String,Integer> xmlVariables = new HashMap<String, Integer>();
	private static Map<String,File> defaults = new HashMap<String, File>();
	
	private static FilenameFilter filter = new FilenameFilter() {
		@Override
		public boolean accept(File dir, String name) {
			return name.substring(name.length()-4,name.length()).equals(".xml");
		}
	};
	
	public static void addXmlVariable(String var, int val) {
		xmlVariables.put(var,val);
	}
	
	public static void loadDefaults( File dir ) {
		for ( File xml: dir.listFiles(filter) ) {
			defaults.put(xml.getName(), xml);
		}
	}

	private static boolean checkIfExists(String filename, File dir) {
		return(new File(dir.getPath()+File.separator+filename)).exists();
	}
	private static void copyDefaultTo(File from, File toDir) throws IOException {
		sendMessage("Copying recipe from default: "+from.getName()+"->"+toDir.getAbsolutePath());
		
		File to = new File(toDir.getPath()+File.separator+from.getName());
		if ( !to.exists() ) {
			to.createNewFile();
		}
		
		FileChannel source = null;
		FileChannel destination = null;
		
		try {
			source = new FileInputStream(from).getChannel();
			destination = new FileOutputStream(to).getChannel();
			destination.transferFrom(source, 0, source.size());
		} finally {
			if ( source != null )
				source.close();
			if ( destination != null )
				destination.close();
		}
	}
	
	public static void loadFolder( File dir ) {
		if ( !dir.isDirectory() )
			dir.mkdir();
		
		for ( String filename: defaults.keySet() ) {
			if ( !checkIfExists(filename,dir) ) {
				try {
					copyDefaultTo(defaults.get(filename),dir);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		for ( File xml: dir.listFiles(filter) ) {
			loadXML(xml);
		}
	}
	public static void loadXML(File file) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
			
			NodeList nodes = doc.getElementsByTagName("recipe");
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
	
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					assemble(element, file);
				}
			}

		} catch (ParserConfigurationException e) {
			endWithError("Could not parse XML: "+file.getName());
			e.printStackTrace();
		} catch (SAXException e) {
			endWithError("Could not parse XML markup: "+file.getName());
			e.printStackTrace();
		} catch (IOException e) {
			endWithError("Could not read XML: "+file.getName());
			e.printStackTrace();
		}
	}
	
	
	private static void assemble(Element element, File xmlFile) {
		String[] recipeLayout = null;
		int recipeStackSize = 1;
		int outId = 0;
		int outMeta = 0;
		Map<String,ItemStack> recipeItemMap = new HashMap<String,ItemStack>();
		Map<String,ItemStack> recipeBlockMap = new HashMap<String,ItemStack>();
		
		// Fetch stack size and outId
		NamedNodeMap recAttrs = element.getAttributes();
		for ( int j = 0; j < recAttrs.getLength(); j++ ) {
			if ( recAttrs.item(j).getNodeName().equals("stackSize") ) {
				try {
					recipeStackSize = Integer.parseInt(recAttrs.item(j).getNodeValue());
				} catch ( NumberFormatException e) {} //Ignore if not set
			}
			if ( recAttrs.item(j).getNodeName().equals("meta") ) {
				try {
					outMeta = Integer.parseInt(recAttrs.item(j).getNodeValue());
				} catch ( NumberFormatException e) {} //Ignore if not set
			}
			if ( recAttrs.item(j).getNodeName().equals("outId") ) {
				String outIdStr = recAttrs.item(j).getNodeValue();
				try {
					// Try integer
					outId = Integer.parseInt(outIdStr);
				} catch( NumberFormatException e ) {
					// Integer failed, try variable strings.
					if (xmlVariables.containsKey(outIdStr)) {
						outId = xmlVariables.get(outIdStr);
					}
				}
			}
		}
		
		// Do not continue if out ID is not set.
		if ( outId == 0 ) {
			endWithError("recipe.outID not set! ("+xmlFile.getName()+")");
			return;
		}
		
		// Fetch layout
		recipeLayout = getValue("layout",element).split("\n");
		int nextI = 0;
		for ( int i = 0; i < recipeLayout.length; i++ ) {
			recipeLayout[i] = recipeLayout[i].trim();
			if ( !recipeLayout[i].equals("") ) {
				recipeLayout[nextI] = recipeLayout[i];
				recipeLayout[i] = null;
				nextI++;
			} else {
				recipeLayout[i] = null;
			}
		}

		// Fetch  mappings
		NodeList blockNodes = element.getElementsByTagName("mapping");
		for (int i = 0; i < blockNodes.getLength(); i++) {
			Node node = blockNodes.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				NamedNodeMap attrs = node.getAttributes();
				int id = 0;
				int meta = 0;
				for ( int j = 0; j < attrs.getLength(); j++ ) {
					if ( attrs.item(j).getNodeName().equals("id") ) {
						String idStr = attrs.item(j).getNodeValue();
						try {
							// Try integer
							id = Integer.parseInt(idStr);
						} catch( NumberFormatException e ) {
							// Integer failed, try variable strings.
							if (xmlVariables.containsKey(idStr)) {
								id = xmlVariables.get(idStr);
							}
						}
					}
					if ( attrs.item(j).getNodeName().equals("meta") ) {
						String metaStr = attrs.item(j).getNodeValue();
						try {
							// Try integer
							meta = Integer.parseInt(metaStr);
						} catch( NumberFormatException e ) {} //Ignore if not set
					}
				}
				if ( id == 0 ) {
					endWithError("mapping.id not set! ("+xmlFile.getName()+")");
					return;
				}
				
				recipeBlockMap.put(
						node.getChildNodes().item(0).getNodeValue(), 
						new ItemStack(id,1,meta)
				);
			}
		}
		
		// Assemble recipe
		List<Object> recipe = new ArrayList<Object>();
		for ( int i = 0; i < recipeLayout.length; i++ ) {
			if ( recipeLayout[i] != null ) {
				recipe.add(recipeLayout[i]);
			}
		}
		for ( String key: recipeItemMap.keySet() ) {
			recipe.add(Character.valueOf(key.toCharArray()[0]));
			recipe.add(recipeItemMap.get(key));
		}
		for ( String key: recipeBlockMap.keySet() ) {
			recipe.add(Character.valueOf(key.toCharArray()[0]));
			recipe.add(recipeBlockMap.get(key));
		}


		// Register recipe
		registerRecipe(
				new ItemStack(outId,recipeStackSize,outMeta),
				recipe.toArray()
		);
	}

	
	private static String getValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = nodes.item(0);
		return node.getNodeValue();
	}
	
	private static void registerRecipe(ItemStack output, Object[] input) {
		GameRegistry.addRecipe(
				output,
				input
		);
		sendMessage("Adding recipe: "+output.itemID);
	}

	private static void sendMessage(String error) {
		System.out.println(error);
	}
	private static void endWithError(String error) {
		System.err.println(error);
	}
}

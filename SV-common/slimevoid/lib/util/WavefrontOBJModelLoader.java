package slimevoid.lib.util;

import java.io.File;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

import slimevoid.lib.data.ModelSimevoidObject;

public class WavefrontOBJModelLoader {
	private static DecimalFormat df = new DecimalFormat("#.####");
	private static Map<File,ModelSimevoidObject> cache = new HashMap<File,ModelSimevoidObject>();
	
	
	public ModelSimevoidObject loadObjFile(ModelBase baseModel, int texW, int texH, File file) throws NumberFormatException, ArithmeticException, FaceMissingTextureException {
		if ( cache.containsKey(file) )
			return cache.get(file);
		
		ModelSimevoidObject out = new ModelSimevoidObject((new ModelRenderer(baseModel, 0, 0)).setTextureSize(texW, texH));
		
		String objStr = FileReader.readFile(file);
		
		parse(objStr, out);
		
		cache.put(file, out);
		
		return out;
	}
	
	private void parse(String objStr, ModelSimevoidObject objModel) throws NumberFormatException, ArithmeticException, FaceMissingTextureException {
		String[] objLines = objStr.split("\n");
		
		for ( int i = 0; i < objLines.length; i++ ) {
			parseLine(objLines[i], objModel);
		}
	}
	
	private void parseLine(String line, ModelSimevoidObject objModel) throws NumberFormatException,ArithmeticException, FaceMissingTextureException {
		String[] lineSegments = line.split(" ");
		
		if ( lineSegments.length > 0 ) {
			if ( lineSegments[0].equals("v") && lineSegments.length >= 4 ) {
				float x = Float.parseFloat(df.format(Float.parseFloat(lineSegments[1].trim())).replaceAll(",", "."));
				float y = Float.parseFloat(df.format(Float.parseFloat(lineSegments[2].trim())).replaceAll(",", "."));
				float z = Float.parseFloat(df.format(Float.parseFloat(lineSegments[3].trim())).replaceAll(",", "."));
				
				objModel.addVertex(x, y, z, 0, 0);
			} else if ( lineSegments[0].equals("vt") && lineSegments.length >= 3 ) {
				float x = Float.parseFloat(lineSegments[1].trim());
				float y = Float.parseFloat(lineSegments[2].trim());
				
				objModel.addVertexTexture(x, y);
			} else if ( lineSegments[0].equals("f") ) {
				if ( lineSegments.length >= 5 ) {
					String[] aS = lineSegments[1].split("/");
					String[] bS = lineSegments[2].split("/");
					String[] cS = lineSegments[3].split("/");
					String[] dS = lineSegments[4].split("/");
	
					if ( aS.length != 2 || bS.length != 2 || cS.length != 2 || dS.length != 2 )
						throw new FaceMissingTextureException("Face missing texture indexes: "+line);
					
					int a = Integer.parseInt(aS[0])-1;
					int at = Integer.parseInt(aS[1])-1;
					
					int b = Integer.parseInt(bS[0])-1;
					int bt = Integer.parseInt(bS[1])-1;
					
					int c = Integer.parseInt(cS[0])-1;
					int ct = Integer.parseInt(cS[1])-1;
					
					int d = Integer.parseInt(dS[0])-1;
					int dt = Integer.parseInt(dS[1])-1;
					
					objModel.addQuad(
							a, b, c, d,
							at, bt, ct, dt, 
							false
					);
				} else if ( lineSegments.length >= 4 ) {
					String[] aS = lineSegments[1].split("/");
					String[] bS = lineSegments[2].split("/");
					String[] cS = lineSegments[3].split("/");
	
					if ( aS.length != 2 || bS.length != 2 || cS.length != 2 )
						throw new FaceMissingTextureException("Face missing texture indexes: "+line);
					
					int a = Integer.parseInt(aS[0])-1;
					int at = Integer.parseInt(aS[1])-1;
					
					int b = Integer.parseInt(bS[0])-1;
					int bt = Integer.parseInt(bS[1])-1;
					
					int c = Integer.parseInt(cS[0])-1;
					int ct = Integer.parseInt(cS[1])-1;
					
					objModel.addTriangle(
							a, b, c,
							at, bt, ct,  
							false
					);
				}
			}	
		}
	}
	
	public class FaceMissingTextureException extends Exception {
		public FaceMissingTextureException() { super(); }
		public FaceMissingTextureException(String message) { super(message); }
		public FaceMissingTextureException(String message, Throwable cause) { super(message, cause); }
		public FaceMissingTextureException(Throwable cause) { super(cause); }
	}
}


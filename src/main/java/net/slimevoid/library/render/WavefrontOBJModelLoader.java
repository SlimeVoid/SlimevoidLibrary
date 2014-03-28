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
package net.slimevoid.library.render;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.slimevoid.library.util.FileReader;

public class WavefrontOBJModelLoader {
    private static DecimalFormat                     df    = new DecimalFormat("#.####");
    private static Map<String, ModelSlimevoidObject> cache = new HashMap<String, ModelSlimevoidObject>();

    public ModelSlimevoidObject loadObjFile(ModelBase baseModel, int texW, int texH, String string) throws NumberFormatException, ArithmeticException, FaceMissingTextureException {
        if (cache.containsKey(string)) return cache.get(string);

        ModelSlimevoidObject out = new ModelSlimevoidObject((new ModelRenderer(baseModel, 0, 0)).setTextureSize(texW,
                                                                                                                texH));

        String objStr = FileReader.readFile(string);

        parse(objStr,
              out);

        cache.put(string,
                  out);

        sendMessage("Loaded model: " + out.vertexCount() + " vertices, "
                    + out.polyCount() + " polys [" + string/* .getName() */
                    + "]");

        return out;
    }

    private void parse(String objStr, ModelSlimevoidObject objModel) throws NumberFormatException, ArithmeticException, FaceMissingTextureException {
        String[] objLines = objStr.split("\n");

        for (int i = 0; i < objLines.length; i++) {
            parseLine(objLines[i],
                      objModel);
        }
    }

    private void parseLine(String line, ModelSlimevoidObject objModel) throws NumberFormatException, ArithmeticException, FaceMissingTextureException {
        String[] lineSegments = line.split(" ");

        if (lineSegments.length > 0) {
            if (lineSegments[0].equals("v") && lineSegments.length >= 4) {
                float x = Float.parseFloat(df.format(Float.parseFloat(lineSegments[1].trim())).replaceAll(",",
                                                                                                          "."));
                float y = Float.parseFloat(df.format(Float.parseFloat(lineSegments[2].trim())).replaceAll(",",
                                                                                                          "."));
                float z = Float.parseFloat(df.format(Float.parseFloat(lineSegments[3].trim())).replaceAll(",",
                                                                                                          "."));

                objModel.addVertex(x,
                                   y,
                                   z,
                                   0,
                                   0);
            } else if (lineSegments[0].equals("vt") && lineSegments.length >= 3) {
                float x = Float.parseFloat(lineSegments[1].trim());
                float y = Float.parseFloat(lineSegments[2].trim());

                objModel.addVertexTexture(x,
                                          y);
            } else if (lineSegments[0].equals("f")) {
                if (lineSegments.length >= 5) {
                    String[] aS = lineSegments[1].split("/");
                    String[] bS = lineSegments[2].split("/");
                    String[] cS = lineSegments[3].split("/");
                    String[] dS = lineSegments[4].split("/");

                    if (aS.length != 2 || bS.length != 2 || cS.length != 2
                        || dS.length != 2) throw new FaceMissingTextureException("Face missing texture indexes: "
                                                                                 + line);

                    int a = Integer.parseInt(aS[0].trim()) - 1;
                    int at = Integer.parseInt(aS[1].trim()) - 1;

                    int b = Integer.parseInt(bS[0].trim()) - 1;
                    int bt = Integer.parseInt(bS[1].trim()) - 1;

                    int c = Integer.parseInt(cS[0].trim()) - 1;
                    int ct = Integer.parseInt(cS[1].trim()) - 1;

                    int d = Integer.parseInt(dS[0].trim()) - 1;
                    int dt = Integer.parseInt(dS[1].trim()) - 1;

                    objModel.addQuad(a,
                                     b,
                                     c,
                                     d,
                                     at,
                                     bt,
                                     ct,
                                     dt,
                                     false);
                } else if (lineSegments.length >= 4) {
                    String[] aS = lineSegments[1].split("/");
                    String[] bS = lineSegments[2].split("/");
                    String[] cS = lineSegments[3].split("/");

                    if (aS.length != 2 || bS.length != 2 || cS.length != 2) throw new FaceMissingTextureException("Face missing texture indexes: "
                                                                                                                  + line);

                    int a = Integer.parseInt(aS[0].trim()) - 1;
                    int at = Integer.parseInt(aS[1].trim()) - 1;

                    int b = Integer.parseInt(bS[0].trim()) - 1;
                    int bt = Integer.parseInt(bS[1].trim()) - 1;

                    int c = Integer.parseInt(cS[0].trim()) - 1;
                    int ct = Integer.parseInt(cS[1].trim()) - 1;

                    objModel.addTriangle(a,
                                         b,
                                         c,
                                         at,
                                         bt,
                                         ct,
                                         false);
                }
            }
        }
    }

    public class FaceMissingTextureException extends Exception {
        public FaceMissingTextureException() {
            super();
        }

        public FaceMissingTextureException(String message) {
            super(message);
        }

        public FaceMissingTextureException(String message, Throwable cause) {
            super(message, cause);
        }

        public FaceMissingTextureException(Throwable cause) {
            super(cause);
        }
    }

    protected static void sendMessage(String message) {
        System.out.println(message);
    }

    protected static void endWithError(String error) {
        System.err.println(error);
    }
}

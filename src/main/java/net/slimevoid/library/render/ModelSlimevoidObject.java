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

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.model.TexturedQuad;
import net.minecraft.client.renderer.Tessellator;

public class ModelSlimevoidObject {
    private List<TexturedQuad>          faceList;
    private List<PositionTextureVertex> vertexList;
    private List<Point2D.Float>         vertexTexList;
    private ModelRenderer               modelRenderer;

    private ModelSlimevoidObjectBounds  boundsCache;

    private int                         polyCount = 0;

    public ModelSlimevoidObject(ModelRenderer modelRenderer) {
        faceList = new ArrayList<TexturedQuad>();
        vertexList = new ArrayList<PositionTextureVertex>();
        vertexTexList = new ArrayList<Point2D.Float>();
        this.modelRenderer = modelRenderer;
    }

    public ModelRenderer getModelRenderer() {
        return this.modelRenderer;
    }

    public int addVertex(float x, float y, float z, float u, float v) {
        int id = vertexList.size();
        vertexList.add(new PositionTextureVertex(x, y, z, u, v));
        return id;
    }

    public int addVertexTexture(float x, float y) {
        int id = vertexTexList.size();
        vertexTexList.add(new Point2D.Float(x, 1 - y));
        return id;
    }

    public void addQuad(int a, int b, int c, int d, int at, int bt, int ct, int dt, boolean flip) {
        PositionTextureVertex ap = new PositionTextureVertex(vertexList.get(a).vector3D, vertexTexList.get(at).x, vertexTexList.get(at).y);
        PositionTextureVertex bp = new PositionTextureVertex(vertexList.get(b).vector3D, vertexTexList.get(bt).x, vertexTexList.get(bt).y);
        PositionTextureVertex cp = new PositionTextureVertex(vertexList.get(c).vector3D, vertexTexList.get(ct).x, vertexTexList.get(ct).y);
        PositionTextureVertex dp = new PositionTextureVertex(vertexList.get(d).vector3D, vertexTexList.get(dt).x, vertexTexList.get(dt).y);

        TexturedQuad quad = new TexturedQuad(new PositionTextureVertex[] {
                ap,
                bp,
                cp,
                dp });

        if (flip) quad.flipFace();
        faceList.add(quad);

        polyCount += 2;
    }

    public void addTriangle(int a, int b, int c, int at, int bt, int ct, boolean flip) {
        PositionTextureVertex ap = new PositionTextureVertex(vertexList.get(a).vector3D, vertexTexList.get(at).x, vertexTexList.get(at).y);
        PositionTextureVertex bp = new PositionTextureVertex(vertexList.get(b).vector3D, vertexTexList.get(bt).x, vertexTexList.get(bt).y);
        PositionTextureVertex cp = new PositionTextureVertex(vertexList.get(c).vector3D, vertexTexList.get(ct).x, vertexTexList.get(ct).y);

        TexturedTriangle triangle = new TexturedTriangle(new PositionTextureVertex[] {
                ap,
                bp,
                cp });

        if (flip) triangle.flipFace();
        faceList.add(triangle);

        polyCount += 1;
    }

    public ModelSlimevoidObjectBounds getBounds() {
        if (boundsCache != null) return boundsCache;

        int minX = 0;
        int minY = 0;
        int minZ = 0;
        int maxX = 0;
        int maxY = 0;
        int maxZ = 0;

        for (PositionTextureVertex v : vertexList) {
            if (v.vector3D.xCoord < minX
                || (minX == 0 && v.vector3D.xCoord != 0)) minX = (int) v.vector3D.xCoord;
            if (v.vector3D.yCoord < minY) minY = (int) v.vector3D.yCoord;
            if (v.vector3D.zCoord < minZ
                || (minZ == 0 && v.vector3D.zCoord != 0)) minZ = (int) v.vector3D.zCoord;

            if (v.vector3D.xCoord > maxX) maxX = (int) v.vector3D.xCoord;
            if (v.vector3D.yCoord > maxY) maxY = (int) v.vector3D.yCoord;
            if (v.vector3D.zCoord > maxZ) maxZ = (int) v.vector3D.zCoord;
        }

        boundsCache = new ModelSlimevoidObjectBounds(minX, minY, minZ, maxX, maxY, maxZ);
        return boundsCache;
    }

    public void render(float par2) {
        for (int i = 0; i < faceList.size(); i++) {
            faceList.get(i).draw(Tessellator.instance,
                                 par2);
        }
    }

    public int vertexCount() {
        return vertexList.size();
    }

    public int faceCount() {
        return faceList.size();
    }

    public int polyCount() {
        return polyCount;
    }

    public class ModelSlimevoidObjectBounds {
        public int minX = 0;
        public int minY = 0;
        public int minZ = 0;
        public int maxX = 0;
        public int maxY = 0;
        public int maxZ = 0;

        public ModelSlimevoidObjectBounds(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
            this.minX = minX;
            this.minY = minY;
            this.minZ = minZ;
            this.maxX = maxX;
            this.maxY = maxY;
            this.maxZ = maxZ;
        }
    }
}

package slimevoid.lib.data;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.model.TexturedQuad;
import net.minecraft.client.renderer.Tessellator;

public class ModelSimevoidObject {
	private List<TexturedQuad> faceList;
	private List<TexturedQuad> qist;
	private List<PositionTextureVertex> vertexList;
	private List<Point2D.Float> vertexTexList;
	private ModelRenderer modelRenderer;
	
	public ModelSimevoidObject(ModelRenderer modelRenderer) {
		faceList = new ArrayList<TexturedQuad>();
		vertexList = new ArrayList<PositionTextureVertex>();
		vertexTexList = new ArrayList<Point2D.Float>();
		this.modelRenderer = modelRenderer;
	}
	
	public int addVertex(float x, float y, float z, float u, float v) {
		int id = vertexList.size();
		vertexList.add(new PositionTextureVertex(x,y,z,u,v));
		return id;
	}

	public int addVertexTexture(float x, float y) {
		int id = vertexTexList.size();
		vertexTexList.add(
				new Point2D.Float(
						x,
						1-y
				)
		);
		return id;
	}
	
	public void addQuad(int a, int b, int c, int d, int at, int bt, int ct, int dt, boolean flip) {
		int u1 = (int)(vertexTexList.get(bt).x*modelRenderer.textureWidth);
		int v1 = (int)(vertexTexList.get(bt).y*modelRenderer.textureHeight);
		int u2 = (int)(vertexTexList.get(dt).x*modelRenderer.textureWidth);
		int v2 = (int)(vertexTexList.get(dt).y*modelRenderer.textureHeight);

		TexturedQuad quad = new TexturedQuad(
				new PositionTextureVertex[] {
						vertexList.get(a),
						vertexList.get(b),
						vertexList.get(c),
						vertexList.get(d)
				},
				u1,
				v1,
				u2,
				v2,
				modelRenderer.textureWidth,
				modelRenderer.textureHeight
		);
		
		if ( flip )
			quad.flipFace();
		faceList.add(quad);
	}
	public void addTriangle(int a, int b, int c, int at, int bt, int ct, boolean flip) {
		PositionTextureVertex ap = new PositionTextureVertex(
				vertexList.get(a).vector3D,
				vertexTexList.get(at).x,vertexTexList.get(at).y
		);
		PositionTextureVertex bp = new PositionTextureVertex(
				vertexList.get(b).vector3D,
				vertexTexList.get(bt).x,vertexTexList.get(bt).y
		);
		PositionTextureVertex cp = new PositionTextureVertex(
				vertexList.get(c).vector3D,
				vertexTexList.get(ct).x,vertexTexList.get(ct).y
		);
		
		TexturedTriangle triangle = new TexturedTriangle(
				new PositionTextureVertex[] {
						ap,bp,cp
				}
		);
		
		if ( flip )
			triangle.flipFace();
		faceList.add(triangle);
	}

	public void render(float par2) {
		for (int i = 0; i < faceList.size(); i++) {
			faceList.get(i).draw(Tessellator.instance, par2);
		}
	}
}

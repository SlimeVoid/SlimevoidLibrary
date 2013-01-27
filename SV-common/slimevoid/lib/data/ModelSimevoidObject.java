package slimevoid.lib.data;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.model.TexturedQuad;
import net.minecraft.client.renderer.Tessellator;

public class ModelSimevoidObject {
	private List<TexturedQuad> quadList;
	private List<PositionTextureVertex> vertexList;
	private List<Point2D.Float> vertexTexList;
	private ModelRenderer modelRenderer;
	
	public ModelSimevoidObject(ModelRenderer modelRenderer) {
		quadList = new ArrayList<TexturedQuad>();
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
		return addVertexTexture(
				(int)(x*modelRenderer.textureWidth),
				(int)((1-y)*modelRenderer.textureHeight)
		);
	}

	public int addVertexTexture(int x, int y) {
		int id = vertexTexList.size();
		vertexTexList.add(
				new Point2D.Float(
						x,
						y
				)
		);
		return id;
	}
	
	public void addQuad(int a, int b, int c, int d, int at, int bt, int ct, int dt, boolean flip) {
		int u1 = (int) Math.min(Math.min(Math.min(
				vertexTexList.get(at).x,
				vertexTexList.get(bt).x),
				vertexTexList.get(ct).x),
				vertexTexList.get(dt).x
		);
		int v1 = (int) Math.min(Math.min(Math.min(
				vertexTexList.get(at).y,
				vertexTexList.get(bt).y),
				vertexTexList.get(ct).y),
				vertexTexList.get(dt).y
		);
		int u2 = (int) Math.max(Math.max(Math.max(
				vertexTexList.get(at).x,
				vertexTexList.get(bt).x),
				vertexTexList.get(ct).x),
				vertexTexList.get(dt).x
		);
		int v2 = (int) Math.max(Math.max(Math.max(
				vertexTexList.get(at).y,
				vertexTexList.get(bt).y),
				vertexTexList.get(ct).y),
				vertexTexList.get(dt).y
		);
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
		quadList.add(quad);
	}

	public void render(float par2) {
		for (int i = 0; i < quadList.size(); i++) {
			quadList.get(i).draw(Tessellator.instance, par2);
		}
	}
}

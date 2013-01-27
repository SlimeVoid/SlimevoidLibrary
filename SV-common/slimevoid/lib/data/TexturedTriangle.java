package slimevoid.lib.data;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.model.TexturedQuad;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Vec3;

public class TexturedTriangle extends TexturedQuad {

	public TexturedTriangle(PositionTextureVertex[] vertices) {
		super(vertices);
	}
	
	public TexturedTriangle(PositionTextureVertex[] vertices, int u1, int v1, int u2, int v2, float texW, float texH) {
		this(vertices);
        float var8 = 0.0F / texW;
        float var9 = 0.0F / texH;
        vertexPositions[0] = vertexPositions[0].setTexturePosition((float)u2 / texW - var8, (float)v1 / texH + var9);
        vertexPositions[1] = vertexPositions[1].setTexturePosition((float)u1 / texW + var8, (float)v1 / texH + var9);
        vertexPositions[2] = vertexPositions[2].setTexturePosition((float)u1 / texW + var8, (float)u2 / texH - var9);
	}
	
	public void flipFace() {
		PositionTextureVertex[] newVertices = new PositionTextureVertex[vertexPositions.length];
		
		for (int i = 0; i < vertexPositions.length; i++) {
			newVertices[i] = this.vertexPositions[vertexPositions.length - i - 1];
		}
		
		vertexPositions = newVertices;
	}
	public void draw(Tessellator par1Tessellator, float par2) {
		Vec3 var3 = this.vertexPositions[1].vector3D.subtract(this.vertexPositions[0].vector3D);
		Vec3 var4 = this.vertexPositions[1].vector3D.subtract(this.vertexPositions[2].vector3D);
		Vec3 var5 = var4.crossProduct(var3).normalize();
		par1Tessellator.startDrawing(GL11.GL_TRIANGLES);
		
		par1Tessellator.setNormal((float)var5.xCoord, (float)var5.yCoord, (float)var5.zCoord);
		
			
		for (int var6 = 0; var6 < 3; ++var6) {
			PositionTextureVertex var7 = this.vertexPositions[var6];
			par1Tessellator.addVertexWithUV((double)((float)var7.vector3D.xCoord * par2), (double)((float)var7.vector3D.yCoord * par2), (double)((float)var7.vector3D.zCoord * par2), (double)var7.texturePositionX, (double)var7.texturePositionY);
		}
		
		par1Tessellator.draw();
	}
}

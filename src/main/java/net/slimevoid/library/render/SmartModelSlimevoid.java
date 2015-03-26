package net.slimevoid.library.render;

import com.google.common.primitives.Ints;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.model.ISmartBlockModel;
import net.minecraftforge.client.model.ISmartItemModel;
import net.minecraftforge.fml.client.FMLClientHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Used as a base to create and register custom Smart Models
 *
 * Currently used for Camo
 */
public abstract class SmartModelSlimevoid implements ISmartBlockModel, ISmartItemModel {

    @Override
    public List getFaceQuads(EnumFacing facing) {
        return getSidedQuads(facing);
    }

    private int[] vertexToInts(float x, float y, float z, int color, int u, int v)
    {
        return new int[]{
                Float.floatToRawIntBits(x),
                Float.floatToRawIntBits(y),
                Float.floatToRawIntBits(z),
                color,
                u,
                v,
                0
        };
    }

    protected IBakedModel getDefaultModel() {
        return FMLClientHandler.instance().getClient().getBlockRendererDispatcher()
                .getModelFromBlockState(Blocks.command_block.getDefaultState(),
                        FMLClientHandler.instance().getClient().theWorld,
                        new BlockPos(0, 0, 0));
    }

    /**
     * Example @link {getDefaultModel()}
     *
     * @return an IBakedModel
     */
    protected abstract IBakedModel getModel();

    protected List getSidedQuads(EnumFacing face) {

        IBakedModel defaultModel = this.getModel();

        if (defaultModel == null) return new ArrayList();
        List listQuadsIn = defaultModel.getFaceQuads(face);
        Integer[] UVs = new Integer[8];
        for (Iterator iterator = listQuadsIn.iterator(); iterator.hasNext();) {
            BakedQuad bakedquad = (BakedQuad) iterator.next();


            for (int i = 0; i < 4; ++i) {
                UVs[(i*2)] = bakedquad.getVertexData()[(i * 7) + 4];
                UVs[(i*2) + 1] = bakedquad.getVertexData()[(i * 7) + 5];
            }
        }



        List<BakedQuad> ret = this.getBakedQuads(UVs, face);
        return ret;
    }

    protected List<BakedQuad> getDefaultBakedQuads(Integer[] UVs, EnumFacing face) {
        List<BakedQuad> ret = new ArrayList<BakedQuad>();
        Vec3 v1 = rotate(new Vec3( -.5,  .5, - .5), face).addVector(.5, .5, .5);
        Vec3 v2 = rotate(new Vec3(- .5,  .5,  .5), face).addVector(.5, .5, .5);
        Vec3 v3 = rotate(new Vec3( .5, .5,  .5), face).addVector(.5, .5, .5);
        Vec3 v4 = rotate(new Vec3( .5,  .5,  - .5), face).addVector(.5, .5, .5);
        ret.add( new BakedQuad(Ints.concat(
                vertexToInts((float) v1.xCoord, (float) v1.yCoord, (float) v1.zCoord, -1, UVs[0], UVs[1]),
                vertexToInts((float) v2.xCoord, (float) v2.yCoord, (float) v2.zCoord, -1, UVs[2], UVs[3]),
                vertexToInts((float) v3.xCoord, (float) v3.yCoord, (float) v3.zCoord, -1, UVs[4], UVs[5]),
                vertexToInts((float) v4.xCoord, (float) v4.yCoord, (float) v4.zCoord, -1, UVs[6], UVs[7])
        ), -1, face));
        return ret;
    }

    /**
     * Example @link {getDefaultBakedQuads()}
     *
     * @param uVs
     * @param face
     *
     * @return a list of baked quads
     */
    protected abstract List<BakedQuad> getBakedQuads(Integer[] uVs, EnumFacing face);

    @Override
    public List getGeneralQuads() {
        return Collections.emptyList();
    }

    @Override
    public boolean isGui3d() {
        return true;
    }

    @Override
    public boolean isAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getTexture() {
        return null;
    }

    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return ItemCameraTransforms.DEFAULT;
    }


    private static Vec3 rotate(Vec3 vec, EnumFacing side)
    {
        switch(side)
        {
            case DOWN:  return new Vec3( vec.xCoord, -vec.yCoord, -vec.zCoord);
            case UP:    return new Vec3( vec.xCoord,  vec.yCoord,  vec.zCoord);
            case NORTH: return new Vec3( vec.xCoord,  vec.zCoord, -vec.yCoord);
            case SOUTH: return new Vec3( vec.xCoord, -vec.zCoord,  vec.yCoord);
            case WEST:  return new Vec3(-vec.yCoord,  vec.xCoord,  vec.zCoord);
            case EAST:  return new Vec3( vec.yCoord, -vec.xCoord,  vec.zCoord);
        }
        return null;
    }
}

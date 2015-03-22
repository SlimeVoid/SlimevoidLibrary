package net.slimevoid.library.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.slimevoid.library.IEnumBlockType;
import net.slimevoid.library.core.SlimevoidCore;
import net.slimevoid.library.core.lib.CoreLib;
import net.slimevoid.library.items.ItemBlockBase;
import net.slimevoid.library.tileentity.TileEntityBase;
import net.slimevoid.library.util.helpers.BlockHelper;
import net.slimevoid.library.util.helpers.ResourceHelper;

/**
 * Created by Greg on 22/03/15.
 */
public abstract class BlockSimpleBase extends BlockBase {

    public BlockSimpleBase(Material material) {
        super(material);
    }

    public void addMapping(int metadata, String unlocalizedName) {
        this.setItemName(metadata,
                unlocalizedName);
    }

    public void setItemName(int metadata, String name) {
        Item item = Item.getItemFromBlock(this);
        if (item != null) {
            ItemBlockBase itembase = ((ItemBlockBase) item).setMetaName(metadata,
                    (new StringBuilder()).append("tile.").append(name).toString());
            if (FMLCommonHandler.instance().getSide().isClient()) {
                this.registerVariant(itembase, metadata, name);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    protected void registerVariant(ItemBlockBase item, int meta, String name) {
        String domain = Loader.instance().activeModContainer().getModId();
        String fullName = domain + ":" + name;
        ResourceHelper.registerVariant(item, meta, fullName);
    }

    @Override
    protected void setActualDefaultState() {
        this.setDefaultState(this.getInitialState(this.blockState.getBaseState().withProperty(BlockStates.FACING, EnumFacing.NORTH).withProperty(this.getBlockTypeProperty(), this.getDefaultBlockType())));
    }

    /**
     * state.withProperty
     *
     * @return state or withProperty
     */

    protected abstract IBlockState getInitialState(IBlockState state);

    protected abstract PropertyEnum getBlockTypeProperty();

    protected abstract IProperty[] getPropertyList();

    protected abstract Comparable<? extends IEnumBlockType> getDefaultBlockType();

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, this.getPropertyList());
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(this.getBlockTypeProperty(), this.getBlockType(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return ((IEnumBlockType) state.getValue(this.getBlockTypeProperty())).getMeta();
    }

    protected abstract Comparable<? extends IEnumBlockType> getBlockType(int meta);

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntityBase tileentity = (TileEntityBase) BlockHelper.getTileEntity(world, pos, this.getTileEntityClass(state));
        if (tileentity != null) {
            return tileentity.getActualState(state, this);
        } else {
            return state;
        }
    }

    public Class<? extends TileEntity> getTileEntityClass(IBlockState state) {
        return ((IEnumBlockType) state.getValue(this.getBlockTypeProperty())).getTileEntityClass();
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return this.createTileEntity(world, this.getStateFromMeta(meta));
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        try {
            return (TileEntity) ((IEnumBlockType) state.getValue(this.getBlockTypeProperty())).createTileEntity();
        } catch (Exception e) {
            SlimevoidCore.console(CoreLib.MOD_NAME, e.getLocalizedMessage());
            return null;
        }
    }
}

package net.slimevoid.library.util.helpers;

import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.slimevoid.library.items.ItemBlockBase;

import java.util.HashMap;

/**
 * Created by Greg on 01/03/15.
 */
public class ResourceHelper {
    private static HashMap<ItemStack, String> variants = new HashMap<ItemStack, String>();

    public static void registerVariant(ItemBlockBase item, int meta, String name) {
        ModelBakery.addVariantName(item, name);
        ItemStack stack = new ItemStack(item, meta);
        variants.put(stack, name);
    }

    public static void processVariants() {
        ItemModelMesher mesher = FMLClientHandler.instance().getClient().getRenderItem().getItemModelMesher();
        for (ItemStack stack : variants.keySet()) {
            Item item = stack.getItem();
            int meta = stack.getMetadata();
            String name = variants.get(stack);
            mesher.register(item, meta, new ModelResourceLocation(name, "inventory"));
        }
    }
}

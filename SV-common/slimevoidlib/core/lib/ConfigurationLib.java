package slimevoidlib.core.lib;

import java.io.File;

import net.minecraft.block.Block;
import net.minecraftforge.common.Configuration;
import slimevoidlib.blocks.BlockTransientLight;

public class ConfigurationLib {

    private static Configuration configuration;

    private static String        BLOCK_TRANSIENT_LIGHT = "sv.transientlight";
    public static int            blockLightID          = 3840;
    public static Block          blockLight;

    public static void preInit(File configFile) {
        configuration = new Configuration(configFile);

        configuration.load();

        blockLightID = configuration.get(Configuration.CATEGORY_BLOCK,
                                         BLOCK_TRANSIENT_LIGHT,
                                         blockLightID).getInt();

        configuration.save();
    }

    public static void init() {
        blockLight = new BlockTransientLight(blockLightID).setLightValue(1.0F).setUnlocalizedName(BLOCK_TRANSIENT_LIGHT);
    }

}

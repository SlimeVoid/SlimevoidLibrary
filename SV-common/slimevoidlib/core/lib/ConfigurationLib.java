package slimevoidlib.core.lib;

import java.io.File;

import net.minecraftforge.common.Configuration;

public class ConfigurationLib {

    private static Configuration configuration;

    public static void preInit(File configFile) {
        configuration = new Configuration(configFile);

        configuration.load();

        configuration.save();
    }

    public static void init() {
    }

}

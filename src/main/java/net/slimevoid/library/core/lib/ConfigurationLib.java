package net.slimevoid.library.core.lib;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

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
